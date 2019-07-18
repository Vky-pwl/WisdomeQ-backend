/**
 * 
 */
package com.icat.quest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.SpringApplicationContext;
import com.icat.quest.common.utils.UniqueCodeGeneratorImpl;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.ExamStatus;
import com.icat.quest.common.vo.ExamStatusVo;
import com.icat.quest.common.vo.TestConductorHasTestCodeVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.TestConductorLicenseDao;
import com.icat.quest.generic.dao.framework.impl.CustomGenericTypeImpl;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.Exam;
import com.icat.quest.model.TestConductor;
import com.icat.quest.model.TestConductorHasTestCode;
import com.icat.quest.model.TestConductorLicense;
import com.icat.quest.service.TestConductorHasTestCodeService;
import com.icat.quest.utils.TinyLinkService;

@Service
public class TestConductorHasTestCodeServiceImpl implements TestConductorHasTestCodeService {

	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private TestConductorLicenseDao testConductorLicenseDao;


	@SuppressWarnings("unchecked")
	@Override
	public void deactiveTestConductorHasTestCode(List<Integer> testConductorHasTestCodeIdList, Integer userId,
			Boolean flag) {

		List<TestConductorHasTestCode> testConductorHasTestCodes = testConductorHasTestCodeDao
				.listEntityByIdList(testConductorHasTestCodeIdList);
		TestConductorLicense testConductorLicense = testConductorHasTestCodes.get(0).getTestConductorLicense();
		@SuppressWarnings("rawtypes")
		List updateList = new ArrayList<>();
		for (TestConductorHasTestCode testConductorHasTestCode : testConductorHasTestCodes) {
			if (!testConductorHasTestCode.getActive()) {
				continue;
			}
			if (!flag && testConductorHasTestCode.getTestConductorLicense().getTestConductor().getTestConductorId()
					.equals(userId)) {
				testConductorHasTestCode.setActive(false);
				testConductorHasTestCode.setLastModified(new Date());
				testConductorHasTestCode.setLastModifiedBy(new Long(userId));
				updateList.add(testConductorHasTestCode);
			}
		}
		testConductorLicense
				.setRemainingLicenseCount(testConductorLicense.getRemainingLicenseCount() + updateList.size());
		testConductorLicense.setLastModified(new Date());
		testConductorLicense.setLastModifiedBy(new Long(userId));
		updateList.add(testConductorLicense);

		testConductorHasTestCodeDao.updateBatch(updateList);

	}

	@Override
	public void assignedUserTestCode(List<Integer> userIdList, Integer testConductorLicenseId, Integer userId,
			Boolean flag, String tinyKey) throws Exception {
		TinyLinkService tinyLinkService = (TinyLinkService) SpringApplicationContext.getBean("tinyLinkService");		
		TestConductorLicense testConductorLicense = testConductorLicenseDao.read(testConductorLicenseId);
		if (flag && !testConductorLicense.getTestConductor().getTestConductorId().equals(userId)) {
			throw new Exception("Not Authorized");
		}
		Exam exam = testConductorLicense.getExam();
		Integer examId = exam.getExamId();
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_1_examId", examId);
		paramsKayAndValues.put("_2_userIds", userIdList);
		@SuppressWarnings("unchecked")
		List<Integer> users = (List<Integer>) testConductorHasTestCodeDao
				.listSingleRowResult(TestConductorHasTestCodeDao.listUserIdByExamIdAndUserList, paramsKayAndValues);
		if (users.size() > 0) {
			userIdList.removeAll(users);
		}
		if (userIdList.isEmpty()) {
			return;
		}
		if (testConductorLicense.getRemainingLicenseCount() < userIdList.size()) {
			throw new Exception("License Exceed ");
		}
		TestConductor testConductor = testConductorLicense.getTestConductor();
		Integer collegeId = null;
		if (!testConductor.getAdminType().equals(UserType.SUPERADMIN)) {
			collegeId = testConductor.getCollege().getCollegeId();
		}
		List<TestConductorHasTestCode> testConductorHasTestCodes = new ArrayList<>();
		List<Candidate> candidateList = candidateDao.listEntityByIdList(userIdList);
		for (Candidate candidate : candidateList) {
			if (collegeId == null || collegeId.equals(candidate.getCollege().getCollegeId())) {
				TestConductorHasTestCode testConductorHasTestCode = new TestConductorHasTestCode();
				testConductorHasTestCode.setActive(true);
				testConductorHasTestCode.setCreated(new Date());
				testConductorHasTestCode.setCreatedBy(new Long(userId));
				testConductorHasTestCode.setLastModified(new Date());
				testConductorHasTestCode.setLastModifiedBy(new Long(userId));
				testConductorHasTestCode.setTestConductorLicense(testConductorLicense);
				testConductorHasTestCode.setUser(candidate);
				testConductorHasTestCode.setExam(exam);
				testConductorHasTestCode.setTotalMarks(exam.getTotalMarks());
				testConductorHasTestCode.setTotalQuestion(exam.getQuestionCount());
				testConductorHasTestCode.setAttended(false);
				testConductorHasTestCode.setExamStartDate(new Date());
				testConductorHasTestCode.setStatus(ExamStatus.PRISTINE);
				testConductorHasTestCode.setRemainingTime(exam.getDurationInSeconds());
				ExamStatusVo examStatusVo = getExamStatus(examId);
				if (examStatusVo != null) {
					testConductorHasTestCode.setCurrentQuestionId(examStatusVo.getCurrentQuestionId());
					testConductorHasTestCode.setCurrentQuestionStatus(ExamStatus.PRISTINE);
					testConductorHasTestCode.setCurrentSectionId(examStatusVo.getCurrentSectionId());
					testConductorHasTestCode.setSectionRemainingTime(examStatusVo.getCurrentSectionRemaingTime());
					testConductorHasTestCode.setCurrentSectionStatus(ExamStatus.PRISTINE);
				}
				testConductorHasTestCodes.add(testConductorHasTestCode);
			}
		}
		testConductorHasTestCodeDao.createBatch(testConductorHasTestCodes);
		for(TestConductorHasTestCode testConductorHasTestCode: testConductorHasTestCodes) {
			tinyKey = tinyLinkService.shortenURL(testConductorHasTestCode.getTestConductorHasTestCodeId(),
					userId, exam.getStartDate() != null ? exam.getStartDate().getTime() : null);
			testConductorHasTestCode.setTinyKey(tinyKey);
			testConductorHasTestCode.setTestCode(
					UniqueCodeGeneratorImpl.getExamCode(
							testConductorLicense.getTestConductorLicenseId() + "",
							examId + "",
							testConductorHasTestCode.getTestConductorHasTestCodeId() + ""));
			
		}
		
		if (testConductorHasTestCodes.size() > 0) {
			testConductorHasTestCodeDao.updateBatch(testConductorHasTestCodes);
			
			testConductorLicense
					.setRemainingLicenseCount(testConductorLicense.getRemainingLicenseCount() - userIdList.size());
			
			testConductorLicenseDao.update(testConductorLicense);
		}
		
	}

	@Override
	public ExamStatusVo getExamStatus(Integer examId) {
		ExamStatusVo examStatusVo = null;
		Map<String, Object> paramsKeyAndValues = new HashMap<String, Object>();
		paramsKeyAndValues.put("_1_examId", examId);
		List<String> excludeList = new ArrayList<>();
		excludeList.add("currentExamId");
		excludeList.add("currentExamStatus");
		excludeList.add("currentSectionStatus");
		excludeList.add("currentQuestionStatus");
		excludeList.add("examRemaingTime");
		excludeList.add("updateFlag");
		excludeList.add("testConductorHasTestCodeId");


		Object[] examStatusObjectArray = testConductorHasTestCodeDao
				.listSqlQuerySingleResult(TestConductorHasTestCodeDao.findCurrentStatus, paramsKeyAndValues);
		examStatusVo = new CustomGenericTypeImpl<ExamStatusVo>(ExamStatusVo.class).converter(examStatusObjectArray,
				excludeList);
		return examStatusVo;
	}

	@Override
	public Integer updateTestConductorHasTestCode(TestConductorHasTestCodeVo testConductorHasTestCodeVo, Integer userId,
			Boolean flag) throws Exception {
		if (testConductorHasTestCodeVo.getTestConductorHasTestCodeId() == null)
			throw new Exception("testConductorHasTestCodeId should not be null");

		TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao
				.read(testConductorHasTestCodeVo.getTestConductorHasTestCodeId());
		if (flag && !testConductorHasTestCode.getTestConductorLicense().getTestConductor().getTestConductorId()
				.equals(userId)) {
			throw new Exception("Not Authorized user");
		}
		if (testConductorHasTestCode == null)
			throw new Exception("Record not found");

		if (testConductorHasTestCodeVo.getUserVo() != null
				&& testConductorHasTestCodeVo.getUserVo().getUserId() != null) {
			testConductorHasTestCode.setUser(candidateDao.read(testConductorHasTestCodeVo.getUserVo().getUserId()));
		}

		if (testConductorHasTestCodeVo.getActive() != null
				&& !testConductorHasTestCode.getActive().equals(testConductorHasTestCodeVo.getActive())) {
			testConductorHasTestCode.setActive(testConductorHasTestCodeVo.getActive());
		}
		testConductorHasTestCode.setLastModified(new Date());
		testConductorHasTestCode.setLastModifiedBy(new Long(userId));
		testConductorHasTestCodeDao.update(testConductorHasTestCode);
		return testConductorHasTestCodeVo.getTestConductorHasTestCodeId();
	}

	@Override
	public TestConductorHasTestCodeVo readTestConductorHasTestCode(Integer testConductorHasTestCodeId, Integer userId)
			throws Exception {
		if (testConductorHasTestCodeId == null)
			throw new Exception("testConductorHasTestCodeId should not be null");

		TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao
				.read(testConductorHasTestCodeId);
		if (testConductorHasTestCode == null)
			throw new Exception("Record not found");

		return Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER.transform(testConductorHasTestCode);
	}

	@Override
	public Map<String, Object> getTestCodeByTestConductorLicenseId(Integer testConductorLicenseId, Integer userId,
			Boolean flag, Integer pageNo, Integer pageSize) throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();
		int startRows = (pageNo * pageSize) - pageSize;

		paramsKayAndValues.put("_1_testConductorLicenseId", testConductorLicenseId);

		List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(
				TestConductorHasTestCodeDao.listByTestConductorLicenseId, paramsKayAndValues, null, null);

		if (flag && testConductorHasTestCodeList != null && testConductorHasTestCodeList.size() > 0) {
			TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeList.get(0);
			if (!testConductorHasTestCode.getTestConductorLicense().getTestConductor().getTestConductorId()
					.equals(userId)
					|| !userId.equals(testConductorHasTestCode.getTestConductorLicense().getTestConductor()
							.getParentTestConductorId())) {
				throw new Exception("Not Authorized");
			}
		}
		List<TestConductorHasTestCodeVo> testConductorHasTestCodeVos = new ArrayList<>();
				testConductorHasTestCodeList.stream().forEach(testConductorHasTestCode->{
					testConductorHasTestCodeVos.add(Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER.transform(testConductorHasTestCode));
					});
		int count =0;
		Map<CandidateVo, List<TestConductorHasTestCodeVo>> groupByCandidateId = new HashMap<>();
		List<Map<String,Object>> results = new ArrayList<>();
		for (TestConductorHasTestCodeVo testConductorHasTestCodeVo : testConductorHasTestCodeVos) {
			if (groupByCandidateId.containsKey(testConductorHasTestCodeVo.getUserVo())) {
				groupByCandidateId.get(testConductorHasTestCodeVo.getUserVo()).add(testConductorHasTestCodeVo);
			} else {
				count++;
				if(count>startRows && count<=pageSize) {
				List<TestConductorHasTestCodeVo> testConductorHasTestCodeVosLocal = new ArrayList<>();
				testConductorHasTestCodeVosLocal.add(testConductorHasTestCodeVo);
				groupByCandidateId.put(testConductorHasTestCodeVo.getUserVo(), testConductorHasTestCodeVosLocal);
				Map<String, Object> obj = new HashMap<>();
				obj.put("candidateVo", testConductorHasTestCodeVo.getUserVo());
				obj.put("examVoList", testConductorHasTestCodeVosLocal);
				results.add(obj);
				}
			}
		}
		
		responseMap.put("count", count);
		responseMap.put("testConductorHasTestCodeVoList", results);

		return responseMap;
	}

	@Override
	public Map<String, Object> getResults(Integer examId, Integer userId, Boolean flag, Integer pageNo,
			Integer pageSize) throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		Map<String, Object> responseMap = new HashMap<>();
		int startRows = (pageNo * pageSize) - pageSize;
		if (pageNo == 1) {
			paramsKayAndValues.put("_1_examId", examId);
			List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao
					.listEntityByParameter(TestConductorHasTestCodeDao.listResultByExamId, paramsKayAndValues, null,
							null);
			if (flag && testConductorHasTestCodeList != null && testConductorHasTestCodeList.size() > 0) {
				TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeList.get(0);
				if (!testConductorHasTestCode.getTestConductorLicense().getTestConductor().getTestConductorId()
						.equals(userId)) {
					throw new Exception("Not Authorized");
				}
			}
			responseMap.put("count", testConductorHasTestCodeList.size());
			List<TestConductorHasTestCodeVo> testConductorHasTestCodeVos = new ArrayList<>();
			testConductorHasTestCodeList.stream().skip(startRows).limit(pageSize).forEach(testConductorHasTestCode -> {
				testConductorHasTestCodeVos
						.add(Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER.transform(testConductorHasTestCode));
			});
			responseMap.put("testConductorHasTestCodeVoList", testConductorHasTestCodeVos);

		} else {
			paramsKayAndValues.put("_1_examId", examId);
			List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao
					.listEntityByParameter(TestConductorHasTestCodeDao.listResultByExamId, paramsKayAndValues,
							startRows, pageSize);
			if (flag && testConductorHasTestCodeList != null && testConductorHasTestCodeList.size() > 0) {
				TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeList.get(0);
				if (!testConductorHasTestCode.getTestConductorLicense().getTestConductor().getTestConductorId()
						.equals(userId)) {
					throw new Exception("Not Authorized");
				}
			}
			List<TestConductorHasTestCodeVo> testConductorHasTestCodeVos = new ArrayList<>();
			testConductorHasTestCodeList.forEach(testConductorHasTestCode -> {
				testConductorHasTestCodeVos
						.add(Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER.transform(testConductorHasTestCode));
			});
			responseMap.put("testConductorHasTestCodeVoList", testConductorHasTestCodeVos);

		}

		return responseMap;
	}

	@Override
	public void updateTestCodes() {
		TinyLinkService tinyLinkService = (TinyLinkService) SpringApplicationContext.getBean("tinyLinkService");
		List<TestConductorHasTestCode> testConductorHasTestCodes = testConductorHasTestCodeDao.listEntity("from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode");
		testConductorHasTestCodes.forEach(testConductorHasTestCode -> {
			System.out.println("***");
			String testCode = UniqueCodeGeneratorImpl.getExamCode(testConductorHasTestCode.getTestConductorLicense().getTestConductorLicenseId() + "",testConductorHasTestCode.getExam().getExamId() + "", testConductorHasTestCode.getTestConductorHasTestCodeId() + "");
			String tinyKey = tinyLinkService.shortenURL(testConductorHasTestCode.getTestConductorHasTestCodeId(),testConductorHasTestCode.getUser().getUserId(), testConductorHasTestCode.getExamStartDate().getTime());
			System.out.println(testCode);
			System.out.println(tinyKey);
			
			testConductorHasTestCode.setTestCode(testCode);
			testConductorHasTestCode.setTinyKey(tinyKey);

		});
		
		testConductorHasTestCodeDao.updateBatch(testConductorHasTestCodes);

	}
	
}
