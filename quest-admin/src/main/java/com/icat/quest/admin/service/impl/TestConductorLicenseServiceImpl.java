/**
 *
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.icat.quest.common.vo.*;
import com.icat.quest.dao.*;
import com.icat.quest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.TestConductorLicenseService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.SpringApplicationContext;
import com.icat.quest.common.utils.UniqueCodeGeneratorImpl;
import com.icat.quest.service.TestConductorHasTestCodeService;
import com.icat.quest.service.UserHasPermissionService;
import com.icat.quest.utils.TinyLinkService;

@Service
public class TestConductorLicenseServiceImpl implements TestConductorLicenseService {

    @Autowired
    private TestConductorLicenseDao testConductorLicenseDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private TestConductorDao testConductorDao;
    @Autowired
    private CandidateDao candidateDao;
    @Autowired
    private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
    @Autowired
    private UserHasPermissionService userHasPermissionService;
    @Autowired
    private TestConductorHasTestCodeService testConductorHasTestCodeService;
    @Autowired
    private UserHasPermissionDao userHasPermissionDao;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Integer assignExternalLicense(Integer candidateId, Integer examId, Integer userId) {
        TinyLinkService tinyLinkService = (TinyLinkService) SpringApplicationContext.getBean("tinyLinkService");

        Exam exam = examDao.read(examId);
        TestConductor testConductor = testConductorDao.read(userId);
        Candidate candidate = candidateDao.read(candidateId);
        List createList = new ArrayList<>();

        Map<String, Object> paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_1_examId", examId);
        paramsKayAndValues.put("_2_testConductorId", userId);
        TestConductorLicense testConductorLicense = testConductorLicenseDao.findEntityByParameter(
                TestConductorLicenseDao.findRemainingLicenseByTestConductorId, paramsKayAndValues);
        if (testConductorLicense == null) {
            String testConductorCode = UniqueCodeGeneratorImpl.getTestConductorCode(testConductor.getFirstName(),
                    testConductor.getTestConductorId() + "", examId + "");
            testConductorLicense = new TestConductorLicense(exam, testConductor, 1, 0, testConductorCode, true,
                    new Date(), new Long(userId), new Date(), new Long(userId));
            createList.add(testConductorLicense);
        } else {
            testConductorLicense.setLicenseCount(testConductorLicense.getLicenseCount() + 1);
            testConductorLicense.setLastModified(new Date());
            testConductorLicense.setLastModifiedBy(new Long(userId));
            testConductorLicenseDao.update(testConductorLicense);
        }
        List<Boolean> attended = new ArrayList<>();
        attended.add(false);
        paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_2_examId", examId);
        paramsKayAndValues.put("_1_candidateId", candidateId);

        TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao
                .findEntityByParameter(TestConductorHasTestCodeDao.findByUserIdAndExamId, paramsKayAndValues);
        if (testConductorHasTestCode == null) {
            testConductorHasTestCode = new TestConductorHasTestCode();
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
            testConductorHasTestCode.setExamStartDate(exam.getStartDate());
            testConductorHasTestCode.setTinyKey(tinyLinkService.shortenURL(examId, userId,
                    exam.getStartDate() != null ? exam.getStartDate().getTime() : null));
            testConductorHasTestCode.setStatus(ExamStatus.PRISTINE);
            testConductorHasTestCode.setRemainingTime(exam.getDurationInSeconds());
            ExamStatusVo examStatusVo = testConductorHasTestCodeService.getExamStatus(examId);
            if (examStatusVo != null) {
                testConductorHasTestCode.setCurrentQuestionId(examStatusVo.getCurrentQuestionId());
                testConductorHasTestCode.setCurrentQuestionStatus(ExamStatus.PRISTINE);
                testConductorHasTestCode.setCurrentSectionId(examStatusVo.getCurrentSectionId());
                testConductorHasTestCode.setSectionRemainingTime(examStatusVo.getCurrentSectionRemaingTime());
                testConductorHasTestCode.setCurrentSectionStatus(ExamStatus.PRISTINE);
            }
            createList.add(testConductorHasTestCode);
            testConductorLicenseDao.createBatch(createList);
        }
        return testConductorLicense.getTestConductorLicenseId();
    }

    @Override
    public Map<String, Object> getExternalLicenseList(Integer candidateId, Integer userId, Integer pageSize,
                                                      Integer pageNo) {
        Map<String, Object> paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_1_userId", candidateId);
        paramsKayAndValues.put("_2_testConductorId", userId);
        Integer startIndex = (pageNo * pageSize) - pageSize;
        Map<String, Object> responseMap = new HashMap<>();
        if (pageNo == 1) {
            List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao
                    .listEntityByParameter(TestConductorHasTestCodeDao.findByUserId, paramsKayAndValues, null, null);
            responseMap.put("count", testConductorHasTestCodeList.size());
            List<TestConductorLicenseVo> testConductorLicenseVoList = new ArrayList<>();
            testConductorHasTestCodeList.stream().skip(startIndex).limit(pageSize).forEach(testConductorHasTestCode -> {
                testConductorLicenseVoList.add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER
                        .transform(testConductorHasTestCode.getTestConductorLicense()));
            });
            responseMap.put("testConductorLicenseVoList", testConductorLicenseVoList);
        } else {
            List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao
                    .listEntityByParameter(TestConductorHasTestCodeDao.findByUserId, paramsKayAndValues, startIndex,
                            pageSize);
            List<TestConductorLicenseVo> testConductorLicenseVoList = new ArrayList<>();
            testConductorHasTestCodeList.forEach(testConductorHasTestCode -> {
                testConductorLicenseVoList.add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER
                        .transform(testConductorHasTestCode.getTestConductorLicense()));
            });
            responseMap.put("testConductorLicenseVoList", testConductorLicenseVoList);

        }
        return responseMap;
    }

    @Override
    public Integer assignAdminLicense(TestConductorLicenseVo testConductorLicenseVo, Integer userId) throws Exception {
        if (testConductorLicenseVo.getExamVo() == null && testConductorLicenseVo.getExamVo().getExamId() == null) {
            throw new Exception("ExamId should not be null");
        }
        if (testConductorLicenseVo.getTestConductorVo() == null
                || testConductorLicenseVo.getTestConductorVo().getTestConductorId() == null) {
            throw new Exception("TestConductorId should not be null");
        }
        Integer examId = testConductorLicenseVo.getExamVo().getExamId();
        Integer testConductorId = testConductorLicenseVo.getTestConductorVo().getTestConductorId();
        Map<String, Object> params = new HashMap<>();
        params.put("_1_examId", examId);
        params.put("_2_testConductorId", testConductorId);

        Integer tcId = (Integer) testConductorLicenseDao.singleRowResult(TestConductorLicenseDao.findByTCIdAndExamId,
                params);

        if (tcId != null) {
            throw new Exception("TestConductorLicense already Exist");
        }

        TestConductorLicense testConductorLicense = new TestConductorLicense();
        testConductorLicense.setActive(true);
        testConductorLicense.setCreated(new Date());
        testConductorLicense.setCreatedBy(new Long(userId));
        testConductorLicense.setLastModified(new Date());
        testConductorLicense.setLastModifiedBy(new Long(userId));
        testConductorLicense.setExam(examDao.read(examId));
        testConductorLicense.setTestConductor(testConductorDao.read(testConductorId));
        testConductorLicense.setLicenseCount(testConductorLicenseVo.getLicenseCount());
        testConductorLicense.setRemainingLicenseCount(testConductorLicenseVo.getLicenseCount());
        testConductorLicense.setTestConductorCode(
                UniqueCodeGeneratorImpl.getTestConductorCode(testConductorLicense.getTestConductor().getFirstName(),
                        testConductorLicense.getTestConductor().getTestConductorId() + "",
                        testConductorLicense.getExam().getExamId() + ""));
        Integer id = testConductorLicenseDao.create(testConductorLicense);
        return id;
    }

    @Override
    public Integer assignTestConductorLicense(TestConductorLicenseVo testConductorLicenseVo, Integer userId)
            throws Exception {

        Integer examId = testConductorLicenseVo.getExamVo().getExamId();
        Integer testConductorId = testConductorLicenseVo.getTestConductorVo().getTestConductorId();

        Map<String, Object> params = new HashMap<>();
        params.put("_1_examId", examId);
        params.put("_2_testConductorId", testConductorId);

        Integer tcId = (Integer) testConductorLicenseDao.singleRowResult(TestConductorLicenseDao.findByTCIdAndExamId,
                params);

        if (tcId != null) {
            throw new Exception("TestConductorLicense already created");
        }

        Map<String, Object> paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_1_examId", examId);
        paramsKayAndValues.put("_2_testConductorId", testConductorLicenseVo.getParentTestConductorId());
        List<TestConductorLicense> testConductorLicensesAdmin = testConductorLicenseDao.listEntityByParameter(
                TestConductorLicenseDao.findRemainingLicenseByTestConductorId, paramsKayAndValues, null, null);
        if (testConductorLicensesAdmin == null || testConductorLicensesAdmin.isEmpty()) {
            throw new Exception("User have No credit : ");
        }
        TestConductorLicense testConductorLicenseAdmin = testConductorLicensesAdmin.get(0);
        if (testConductorLicenseAdmin.getRemainingLicenseCount() < testConductorLicenseVo.getLicenseCount()) {
            throw new Exception("RemainingLicenseCount is less than assign License count: "
                    + testConductorLicenseAdmin.getRemainingLicenseCount());
        }

        TestConductor testConductor = testConductorDao.read(testConductorId);
        if (testConductor == null || testConductor.getParentTestConductorId() == null || !testConductor
                .getParentTestConductorId().equals(testConductorLicenseVo.getParentTestConductorId())) {
            throw new Exception("Client should be Same College of Examiner ");
        }

        testConductorLicenseAdmin.setRemainingLicenseCount(
                testConductorLicenseAdmin.getRemainingLicenseCount() - testConductorLicenseVo.getLicenseCount());
        testConductorLicenseAdmin.setLastModified(new Date());
        testConductorLicenseAdmin.setLastModifiedBy(new Long(userId));

        TestConductorLicense testConductorLicense = new TestConductorLicense();
        testConductorLicense.setActive(true);
        testConductorLicense.setCreated(new Date());
        testConductorLicense.setCreatedBy(new Long(userId));
        testConductorLicense.setLastModified(new Date());
        testConductorLicense.setLastModifiedBy(new Long(userId));
        testConductorLicense.setExam(examDao.read(examId));
        testConductorLicense.setTestConductor(testConductor);
        testConductorLicense.setLicenseCount(testConductorLicenseVo.getLicenseCount());
        testConductorLicense.setRemainingLicenseCount(testConductorLicenseVo.getLicenseCount());
        testConductorLicense.setTestConductorCode(
                UniqueCodeGeneratorImpl.getTestConductorCode(testConductorLicense.getTestConductor().getFirstName(),
                        testConductorLicense.getTestConductor().getTestConductorId() + "",
                        testConductorLicense.getExam().getExamId() + ""));
        Integer id = testConductorLicenseDao.create(testConductorLicense);
        testConductorLicenseDao.update(testConductorLicenseAdmin);
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer updateTestConductorLicense(TestConductorLicenseVo testConductorLicenseVo, Integer userId,
                                              Boolean flag) throws Exception {
        if (testConductorLicenseVo.getTestConductorLicenseId() == null) {
            throw new Exception("TestConductorLicenseId should not be null");
        }
        TestConductorLicense testConductorLicense = testConductorLicenseDao
                .read(testConductorLicenseVo.getTestConductorLicenseId());
        if (testConductorLicense == null) {
            throw new Exception("Record not found");
        }

        Integer updateCount = testConductorLicenseVo.getLicenseCount() - testConductorLicense.getLicenseCount();

        Integer examId = testConductorLicenseVo.getExamVo().getExamId();
        @SuppressWarnings("rawtypes")
        List testConductorList = new ArrayList<>();

        if ((!flag && testConductorLicenseVo.getParentTestConductorId() != null)
                || (flag && userId.equals(testConductorLicenseVo.getParentTestConductorId()))) {
            Map<String, Object> paramsKayAndValues = new HashMap<>();
            paramsKayAndValues.put("_1_examId", examId);
            paramsKayAndValues.put("_2_testConductorId", testConductorLicenseVo.getParentTestConductorId());
            List<TestConductorLicense> testConductorLicensesAdmin = testConductorLicenseDao.listEntityByParameter(
                    TestConductorLicenseDao.findRemainingLicenseByTestConductorId, paramsKayAndValues, null, null);
            if (testConductorLicensesAdmin == null || testConductorLicensesAdmin.isEmpty()
                    || testConductorLicensesAdmin.get(0).getRemainingLicenseCount() - updateCount < 0) {
                throw new Exception("Parent User have No credit : ");
            }
            TestConductorLicense testConductorLicenseAdmin = testConductorLicensesAdmin.get(0);
            testConductorLicenseAdmin
                    .setRemainingLicenseCount(testConductorLicenseAdmin.getRemainingLicenseCount() - updateCount);
            testConductorLicenseAdmin.setLastModified(new Date());
            testConductorLicenseAdmin.setLastModifiedBy(new Long(userId));
            testConductorList.add(testConductorLicenseAdmin);
            if (testConductorLicenseVo.getActive() != null
                    && !testConductorLicense.getActive().equals(testConductorLicenseVo.getActive())) {
                if (testConductorLicenseVo.getActive() && testConductorLicenseAdmin
                        .getRemainingLicenseCount() >= testConductorLicenseVo.getRemainingLicenseCount()) {
                    testConductorLicenseAdmin
                            .setRemainingLicenseCount(testConductorLicenseAdmin.getRemainingLicenseCount()
                                    - testConductorLicenseVo.getRemainingLicenseCount());
                } else {
                    testConductorLicenseAdmin
                            .setRemainingLicenseCount(testConductorLicenseAdmin.getRemainingLicenseCount()
                                    + testConductorLicenseVo.getRemainingLicenseCount());
                }
            }
        }

        if (testConductorLicenseVo.getLicenseCount() != null) {
            testConductorLicense.setRemainingLicenseCount(testConductorLicenseVo.getLicenseCount()
                    - (testConductorLicense.getLicenseCount() - testConductorLicense.getRemainingLicenseCount()));
            testConductorLicense.setLicenseCount(testConductorLicenseVo.getLicenseCount());
        }

        if (testConductorLicenseVo.getActive() != null) {
            testConductorLicense.setActive(testConductorLicenseVo.getActive());
        }
        testConductorLicense.setLastModified(new Date());
        testConductorLicense.setLastModifiedBy(new Long(userId));
        testConductorList.add(testConductorLicense);
        testConductorLicenseDao.updateBatch(testConductorList);

        return testConductorLicenseVo.getTestConductorLicenseId();
    }

    @Override
    public TestConductorLicenseVo readTestConductorLicense(Integer testConductorLicenseId, Integer userId)
            throws Exception {
        if (testConductorLicenseId == null) {
            throw new Exception("testConductorLicenseId should not be null");
        }
        TestConductorLicense testConductorLicense = testConductorLicenseDao.read(testConductorLicenseId);
        if (testConductorLicense == null) {
            throw new Exception("Record not found");
        }
        return Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense);
    }

    @Override
    public Map<String, Object> listTestConductorLicense(Integer pageNo, Integer pageSize, String searchKey,
                                                        Boolean active, Integer userId) throws Exception {
        List<TestConductorLicense> testConductorLicenseList = new ArrayList<>();
        if (pageNo == null)
            pageNo = 50;
        if (pageSize == null)
            pageSize = 1;
        Map<String, Object> parameters = new HashMap<>();
        Integer startIndex = null;
        if (pageNo != 1)
            startIndex = (pageNo * pageSize) - pageSize;
        if (searchKey != null && active != null) {
            searchKey = "%" + searchKey + "%";
            parameters.put("_2_testConductorLicenseName", searchKey);
            parameters.put("_1_active", active);
            testConductorLicenseList = testConductorLicenseDao.listEntityByParameter(
                    TestConductorLicenseDao.findAllBySearchKeyWithFilter, parameters, startIndex, pageSize);
        } else if (searchKey == null && active != null) {
            parameters.put("_1_active", active);
            testConductorLicenseList = testConductorLicenseDao
                    .listEntityByParameter(TestConductorLicenseDao.findAllByFilter, parameters, startIndex, pageSize);
        } else if (searchKey != null && active == null) {
            searchKey = "%" + searchKey + "%";
            parameters.put("_2_testConductorLicenseName", searchKey);
            testConductorLicenseList = testConductorLicenseDao.listEntityByParameter(
                    TestConductorLicenseDao.findAllBySearchKey, parameters, startIndex, pageSize);
        } else {
            testConductorLicenseList = testConductorLicenseDao.listEntityByParameter(TestConductorLicenseDao.findAll,
                    parameters, startIndex, pageSize);
        }
        Map<String, Object> testConductorLicenseResultSet = new HashMap<>();
        if (pageNo == 1) {
            startIndex = (pageNo * pageSize) - pageSize;
            testConductorLicenseResultSet.put("count", testConductorLicenseList.size());
            List<TestConductorLicenseVo> testConductorLicenseVos = new ArrayList<>();
            testConductorLicenseList.stream().skip(startIndex).limit(pageSize).forEach(testConductorLicense -> {
                testConductorLicenseVos
                        .add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense));
            });
            testConductorLicenseResultSet.put("testConductorLicenseVoList", testConductorLicenseVos);
        } else {
            List<TestConductorLicenseVo> testConductorLicenseVos = new ArrayList<>();
            testConductorLicenseList.forEach(testConductorLicense -> {
                testConductorLicenseVos
                        .add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense));
            });
            testConductorLicenseResultSet.put("testConductorLicenseVoList", testConductorLicenseVos);
        }

        return testConductorLicenseResultSet;
    }

    @Override
    public Map<String, Object> listBytestConductorId(Integer testConductorId, Integer pageNo, Integer pageSize,
                                                     Integer userId, UserType userType) {
        Map<String, Object> paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_1_testConductorId", testConductorId);
        Map<String, Object> testConductorLicenseResultSet = new HashMap<>();
        List<TestConductorLicenseVo> testConductorLicenseVos = new ArrayList<>();
        Integer startIndex = (pageNo * pageSize) - pageSize;

        if (pageNo == 1) {
            List<TestConductorLicense> testConductorLicenseList = testConductorLicenseDao.listEntityByParameter(
                    TestConductorLicenseDao.findAllByTestConductorId, paramsKayAndValues, null, null);
            testConductorLicenseResultSet.put("count", testConductorLicenseList.size());
            testConductorLicenseList.stream().skip(startIndex).limit(pageSize).forEach(testConductorLicense -> {
                testConductorLicenseVos
                        .add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense));
            });
            setPermissionExam(testConductorLicenseVos, userType, userId);

        } else {
            List<TestConductorLicense> testConductorLicenseList = testConductorLicenseDao.listEntityByParameter(
                    TestConductorLicenseDao.findAllByTestConductorId, paramsKayAndValues, startIndex, pageSize);
            testConductorLicenseList.forEach(testConductorLicense -> {
                testConductorLicenseVos
                        .add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense));
            });
            setPermissionExam(testConductorLicenseVos, userType, userId);
        }
        getPermissionsForTestConductorLicenseList(testConductorLicenseVos, userType);
        /*testConductorLicenseVos = testConductorLicenseVos.stream()
                .filter(testConductorLicenseVo -> testConductorLicenseVo.getExamVo().getPermissionVoList() != null && testConductorLicenseVo.getTestConductorVo().getPermissionVos() == null)
                .map(testConductorLicenseVo -> {
                    TestConductorVo testConductorVo = testConductorLicenseVo.getTestConductorVo();
                    testConductorVo.setPermissionVos(testConductorLicenseVo.getExamVo().getPermissionVoList());
                    testConductorLicenseVo.setTestConductorVo(testConductorVo);
                    return testConductorLicenseVo;
                }).collect(Collectors.toList());*/
        testConductorLicenseResultSet.put("testConductorLicenseVoList", testConductorLicenseVos);
        return testConductorLicenseResultSet;
    }

    @Override
    public List<TestConductorLicenseVo> listByExamId(Integer testConductorId, Integer examId) {
        Map<String, Object> paramsKayAndValues = new HashMap<>();
        paramsKayAndValues.put("_1_testConductorId", testConductorId);
        paramsKayAndValues.put("_2_examId", examId);

        List<TestConductorLicense> testConductorLicenseList = testConductorLicenseDao
                .listEntityByParameter(TestConductorLicenseDao.findAllByExamId, paramsKayAndValues, null, null);
        List<TestConductorLicenseVo> testConductorLicenseVos = new ArrayList<>();
        testConductorLicenseList.stream().forEach(testConductorLicense -> {
            testConductorLicenseVos.add(Transformer.TESTCONDUCTOR_LICENSE_TRANSFORMER.transform(testConductorLicense));
        });
        return testConductorLicenseVos;
    }

	private void setPermissionExam(List<TestConductorLicenseVo> testConductorLicenseVos, UserType userType,
			Integer userId) {

		if (userType != null && userId != null) {
            List<Exam> examList = testConductorLicenseVos.stream()
                    .filter(testConductorLicenseVo -> testConductorLicenseVo.getExamVo() != null && testConductorLicenseVo.getExamVo().getExamId() != null)
                    .map(testConductorLicenseVo -> {
                        Exam exam = new Exam();
                        exam.setExamId(testConductorLicenseVo.getExamVo().getExamId());
                        return exam;
                    })
                    .collect(Collectors.toList());
			Map<Integer, List<PermissionVo>> permissionMap = userHasPermissionService
                    .getPermissionListGroupByExamId(userId, userType, examList);
			for (TestConductorLicenseVo testConductorLicenseVo : testConductorLicenseVos) {
				if (permissionMap.containsKey(testConductorLicenseVo.getExamVo().getExamId())) {
					testConductorLicenseVo.getExamVo()
							.setPermissionVoList(permissionMap.get(testConductorLicenseVo.getExamVo().getExamId()));
					testConductorLicenseVo.getTestConductorVo().setPermissionVos(permissionMap.get(testConductorLicenseVo.getExamVo().getExamId()));
				}
			}
		}
	}

    private void getPermissionsForTestConductorLicenseList(List<TestConductorLicenseVo> testConductorLicenseVoList,
                                                           UserType adminType) {
        testConductorLicenseVoList.forEach((testConductorLicenseVo -> {
            if (testConductorLicenseVo != null
                    && testConductorLicenseVo.getTestConductorVo() != null
                    && testConductorLicenseVo.getTestConductorVo().getPermissionVos() != null) {
                return;
            }
            Map<String, Object> params = new HashMap<>();
            TestConductorVo testConductorVo = testConductorLicenseVo.getTestConductorVo();
            params.put("_1_userId", testConductorVo.getTestConductorId());
            params.put("_2_userType", adminType);
            params.put("_3_active", testConductorVo.getActive());
            params.put("_4_examId", testConductorLicenseVo.getExamVo().getExamId());
            List<UserHasPermission> userHasPermissionList = userHasPermissionDao.listEntityByParameter(
                    UserHasPermissionDao.findAllPermissionByUserIdAndUserTypeExamId, params, null, null);
            List<PermissionVo> permissionVoList = userHasPermissionList.stream().map(
                    (userHasPermission) -> Transformer.PERMISSION_TRANSFORMER.transform(userHasPermission.getPermission()))
                    .collect(Collectors.toList());
            testConductorVo.setPermissionVos(permissionVoList);
            testConductorLicenseVo.setTestConductorVo(testConductorVo);
        }));
    }

}
