package com.icat.quest.auth.login.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWTSigner;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.Constants;
import com.icat.quest.common.utils.SystemConfigResolver;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.common.vo.TestConductorVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.TestConductorDao;
import com.icat.quest.dao.UserHasPermissionDao;
import com.icat.quest.dao.UserLoginDao;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.Permission;
import com.icat.quest.model.TestConductor;
import com.icat.quest.model.UserHasPermission;
import com.icat.quest.model.UserLogin;

@Service
public class AuthServiceImpl {

	@Autowired
	private TestConductorDao testConductorDao;
	@Autowired
	private CandidateDao userDao;
	@Autowired
	private UserLoginDao userLoginDao;
	@Autowired
	private UserHasPermissionDao userHasPermissionDao;

	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	public Object getAuthTokenForLogin(Map<String, String> authParams) throws Exception {

		String userName = authParams.get(Constants.REQUEST_USERNAME);
		String password = authParams.get(Constants.REQUEST_PASSWORD);
		String userType = authParams.get(Constants.REQUEST_USER_TYPE);
		if (UserType.valueOf(userType).equals(UserType.CANDIDATE)) {
			return loginCandidate(userName, password);
		} else {
			return loginAdmin(userName, password);
		}

	}

	private TestConductorVo loginAdmin(String userName, String password) throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_testConductorName", userName);
		List<TestConductor> testConductorList = testConductorDao
				.listEntityByParameter(TestConductorDao.findTestConductorLogin, paramsKayAndValues, null, null);
		if (testConductorList == null || testConductorList.isEmpty()) {
			logger.warn("User does not exist");
			throw new Exception("User does not exist");
		}
		TestConductor testConductor = testConductorList.get(0);

		if (testConductor == null || !testConductor.getPassword().equals(password)) {
			logger.warn("User & Password does not match");
			throw new Exception("User & Password does not match");

		}
		Integer userId = testConductor.getTestConductorId();
		TestConductorVo testConductorVo = Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductor);

		testConductorVo
				.setPermissionVos(getUserPermissions(UserType.valueOf(testConductor.getAdminType().name()), userId));
		testConductorVo.setToken(createToken(userId, testConductor.getAdminType(),
				testConductor.getCollege() != null ? testConductor.getCollege().getCollegeId() : null));
		return testConductorVo;

	}

	private CandidateVo loginCandidate(String userName, String password) throws Exception {

		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_userName", userName);
		List<Candidate> userList = userDao.listEntityByParameter(CandidateDao.findUserLogin, paramsKayAndValues, null,
				null);

		if (userList == null || userList.isEmpty()) {
			logger.warn("User does not exist");
			throw new Exception("User does not exist");
		}
		Candidate candidate = userList.get(0);

		if (candidate == null || !candidate.getPassword().equals(password)) {
			logger.warn("User & Password does not match");
			throw new Exception("User & Password does not match");

		}
		Integer userId = candidate.getUserId();
		CandidateVo candidateVo = Transformer.CANDIDATE_TRANSFORMER.transform(candidate);
		candidateVo.setPermissionVos(getUserPermissions(UserType.CANDIDATE, userId));
		candidateVo.setToken(createToken(userId, UserType.CANDIDATE,
				candidate.getCollege() != null ? candidate.getCollege().getCollegeId() : null));
		return candidateVo;

	}

	public String createToken(Integer userId, UserType userType, Integer collegeId) {
		Integer expirySeconds = Integer.parseInt(SystemConfigResolver.getProperty("jwt.expiry.seconds", "0"));
		Map<String, Object> findAllLogOffTimeNullByUserNameParams = new HashMap<String, Object>();
		findAllLogOffTimeNullByUserNameParams.put("_1_userId", userId);
		findAllLogOffTimeNullByUserNameParams.put("_2_userType", userType);

		List<UserLogin> userLoginList = userLoginDao.listEntityByParameter(UserLoginDao.findAllLogOffTimeNullByUserName,
				findAllLogOffTimeNullByUserNameParams, null, null);
		if (userLoginList != null && userLoginList.size() > 0) {
			userLoginList.forEach(userLogin -> {
				userLogin.setLastModified(new Date());
				userLogin.setLastModifiedBy(new Long(userId));
				if (userLogin.getTokenExpiryTime().getTime() < (new Date()).getTime()) {
					userLogin.setLogoffTime(userLogin.getTokenExpiryTime());
				} else {
					userLogin.setLogoffTime(new Date());
				}
			});
			userLoginDao.updateBatch(userLoginList);
		}

		UserLogin userLogin = new UserLogin(userId, userType,
				new Date(System.currentTimeMillis() + (expirySeconds * 1000)), new Date(), new Long(userId), new Date(),
				new Long(userId));
		Integer userLoginId = userLoginDao.create(userLogin);

		Map<String, Object> claims = new HashMap<>();
		claims.put(Constants.CLAIM_ATTR_ISS, Constants.CLAIM_ATTR_ISS_VAL);
		claims.put(Constants.CLAIM_ATTR_SUB, userId + "");
		claims.put(Constants.CLAIM_ATTR_TYPE, userType);
		claims.put(Constants.CLAIM_ATTR_ULID, userLoginId + "");
		if (collegeId != null) {
			claims.put(Constants.CLAIM_ATTR_CLID, collegeId + "");
		}
		long now = System.currentTimeMillis() / 1000l;
		claims.put(Constants.CLAIM_ATTR_EXP, now + expirySeconds);
		claims.put(Constants.CLAIM_ATTR_IAT, now);
		claims.put(Constants.CLAIM_ATTR_JTI, UUID.randomUUID().toString());
		JWTSigner jwtSigner = new JWTSigner(SystemConfigResolver.getProperty("jwt.secret.key", ""));
		String signedToken = jwtSigner.sign(claims);
		return signedToken;
	}

	private List<PermissionVo> getUserPermissions(UserType userType, Integer userId) {
		List<PermissionVo> permissionVos = new ArrayList<>();
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_2_userType", userType);
		paramsKayAndValues.put("_1_userId", userId);
		paramsKayAndValues.put("_3_active", true);
		List<UserHasPermission> userHasPermissionList = userHasPermissionDao.listEntityByParameter(
				UserHasPermissionDao.findAllPermissionByUserIdAndUserType, paramsKayAndValues, null, null);
		if (userHasPermissionList != null) {
			userHasPermissionList.forEach(userHasPermission -> {
				Permission permission = userHasPermission.getPermission();
				PermissionVo permissionVo = new PermissionVo();
				permissionVo.setPermissionId(permission.getPermissionId());
				permissionVo.setPermissionName(permission.getPermissionName());
				permissionVo.setActive(permission.getActive());
				permissionVos.add(permissionVo);
			});
		}

		return permissionVos;
	}

}