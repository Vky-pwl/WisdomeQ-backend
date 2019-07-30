/**
 * 
 */
package com.icat.quest.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.common.vo.UserHasPermissionBatchVo;
import com.icat.quest.common.vo.UserHasPermissionVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamDao;
import com.icat.quest.dao.PermissionDao;
import com.icat.quest.dao.UserHasPermissionDao;
import com.icat.quest.model.Exam;
import com.icat.quest.model.Permission;
import com.icat.quest.model.UserHasPermission;
import com.icat.quest.service.UserHasPermissionService;

@Service
public class UserHasPermissionServiceImpl implements UserHasPermissionService {

	@Autowired
	private UserHasPermissionDao userHasPermissionDao;
	@Autowired
	private ExamDao examDao;
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public List<Integer> craeteBatchUserHasPermission(UserHasPermissionBatchVo userHasPermissionBatchVo,
			Integer principalId) throws Exception {
		Integer userId = userHasPermissionBatchVo.getUserId();
		List<Integer> permissionIdList = userHasPermissionBatchVo.getPermissionIdList();
		Integer examId = userHasPermissionBatchVo.getExamId();
		UserType userType = UserType.valueOf(userHasPermissionBatchVo.getUserType());
		if (userId == null || examId == null || userType == null) {
			throw new Exception("Invalid input");
		}
		if(permissionIdList == null) {
			permissionIdList = new ArrayList<>();
		}
		
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		paramsKayAndValues.put("_3_userId", userId);
		paramsKayAndValues.put("_4_userType", userType);
		List<Integer> updatePermissionIdList = new ArrayList<>();
		List<UserHasPermission> userHasPermissions = userHasPermissionDao
				.listEntityByParameter(UserHasPermissionDao.findAllByExamIdAndUserId, paramsKayAndValues, null, null);
		if (userHasPermissions != null && userHasPermissions.size() > 0) {
			for (UserHasPermission userHasPermission : userHasPermissions) {
				if (permissionIdList.contains(userHasPermission.getPermission().getPermissionId())) {
					userHasPermission.setActive(true);
				} else {
					userHasPermission.setActive(false);
				}
				userHasPermission.setLastModified(new Date());
				userHasPermission.setLastModifiedBy(new Long(principalId));
				updatePermissionIdList.add(userHasPermission.getPermission().getPermissionId());
			}
			userHasPermissionDao.updateBatch(userHasPermissions);
		}
		
		if(permissionIdList.isEmpty()) {
			return permissionIdList;
		}
		
		List<Permission> permissionList = permissionDao.listEntityByIdList(permissionIdList);
		Exam exam = examDao.read(examId);

		List<UserHasPermission> userHasPermissionsList = new ArrayList<>();
		for (Permission permission : permissionList) {
			if (!updatePermissionIdList.contains(permission.getPermissionId())) {
				UserHasPermission userHasPermission = new UserHasPermission(permission, exam, userId, userType, true,
						new Date(), new Long(principalId), new Date(), new Long(principalId));
				userHasPermissionsList.add(userHasPermission);
			}
		}

		if (userHasPermissionsList.size() > 0) {
			return userHasPermissionDao.createBatch(userHasPermissionsList);
		} else {
			return permissionIdList;
		}

	}

	@Override
	public void updateBatchUserHasPermission(List<Integer> userHasPermissionIdList, Integer userId) throws Exception {
		if (userHasPermissionIdList == null || userHasPermissionIdList.isEmpty())
			throw new Exception("Invalid Input");
		List<UserHasPermission> userHasPermissionList = userHasPermissionDao
				.listEntityByIdList(userHasPermissionIdList);
		if (userHasPermissionList != null && userHasPermissionList.size() > 0) {
			for (UserHasPermission userHasPermission : userHasPermissionList) {
				userHasPermission.setActive(false);
				userHasPermission.setLastModified(new Date());
				userHasPermission.setLastModifiedBy(new Long(userId));
			}
			userHasPermissionDao.updateBatch(userHasPermissionList);
		}
	}

	@Override
	public UserHasPermissionVo readUserHasPermission(Integer userHasPermissionId, Integer userId) throws Exception {
		if (userHasPermissionId == null) {
			throw new Exception("UserHasPermissionId should not be null");
		}
		UserHasPermission userHasPermission = userHasPermissionDao.read(userHasPermissionId);
		if (userHasPermission == null) {
			throw new Exception("Record not found");
		}
		return Transformer.USER_PERMISSION_TRANSFORMER.transform(userHasPermission);
	}

	@Override
	public List<UserHasPermissionVo> listUserHasPermission(Integer userId, UserType userType, Boolean active,
			Integer examId) throws Exception {

		List<UserHasPermissionVo> userHasPermissionVoList = new ArrayList<UserHasPermissionVo>();
		List<UserHasPermission> userHasPermissionList = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();

		parameters.put("_1_userId", userId);
		parameters.put("_2_userType", userType);
		parameters.put("_3_active", active);
		parameters.put("_4_examId", examId);

		userHasPermissionList = userHasPermissionDao.listEntityByParameter(
				UserHasPermissionDao.findAllPermissionByUserIdAndUserTypeExamId, parameters, null, null);

		userHasPermissionList.forEach(userHasPermission -> {
			userHasPermissionVoList.add(Transformer.USER_PERMISSION_TRANSFORMER.transform(userHasPermission));
		});
		return userHasPermissionVoList;
	}

	@Override
	public boolean isPermission(Integer userId, Integer examId, UserType userType, Integer permissionId) {
		Map<String, Object> params = new HashMap<>();
		params.put("_1_examId", examId);
		params.put("_2_userId", userId);
		params.put("_3_userType", userType.name());
		params.put("_4_permissionId", permissionId);

		@SuppressWarnings("unchecked")
		List<Integer> userIdList = (List<Integer>) permissionDao.listSingleRowResult(UserHasPermissionDao.checkEligble,
				params);
		if (userIdList == null || userIdList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isPermissionTemp(Integer collegeId, Integer examId, Integer permissionId) {
		Map<String, Object> params = new HashMap<>();
		params.put("_3_examId", examId);
		params.put("_1_collegeId", collegeId);
		params.put("_2_permissionId", permissionId);

		@SuppressWarnings("unchecked")
		List<Integer> perms = (List<Integer>) permissionDao.listSingleRowResult(UserHasPermissionDao.checkEligbleTemp,
				params);
		if (perms == null || perms.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Map<Integer, List<PermissionVo>> getPermissionListGroupByExamId(Integer userId, UserType userType,
																		   List<Exam> examList) {
		Map<Integer, List<PermissionVo>> permissionListGroupByExamId = new HashMap<>();
		List<UserHasPermission> userHasPermissions = new ArrayList<>();
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		if (userType.name().equals(UserType.SUPERADMIN.name())) {
			paramsKayAndValues.put("_1_active", true);
			userHasPermissions = userHasPermissionDao.listEntityByParameter(
					UserHasPermissionDao.findAllPermissions, paramsKayAndValues, null, null);
		} else {
			paramsKayAndValues.put("_1_userId", userId);
			paramsKayAndValues.put("_2_userType", userType);
			paramsKayAndValues.put("_3_active", true);
			if(examList.size()>0) {
				paramsKayAndValues.put("_4_examId", examList.stream().map(Exam::getExamId).collect(Collectors.toList()));
				userHasPermissions = userHasPermissionDao.listEntityByParameter(
						UserHasPermissionDao.findAllPermissionByUserIdAndUserTypeExamId, paramsKayAndValues, null, null);
			} else {
				userHasPermissions = userHasPermissionDao.listEntityByParameter(
						UserHasPermissionDao.findAllPermissionByUserIdAndUserType, paramsKayAndValues, null, null);
			}
		}
		userHasPermissions.forEach(userHasPermission->{
			if(permissionListGroupByExamId.containsKey(userHasPermission.getExam().getExamId())) {
				permissionListGroupByExamId.get(userHasPermission.getExam().getExamId()).add(Transformer.PERMISSION_TRANSFORMER.transform(userHasPermission.getPermission()));
			}else {
				List<PermissionVo> permissionVos = new ArrayList<>();
				permissionVos.add(Transformer.PERMISSION_TRANSFORMER.transform(userHasPermission.getPermission()));
				permissionListGroupByExamId.put(userHasPermission.getExam().getExamId(), permissionVos);
			}
		});
		return permissionListGroupByExamId;
	}

}
