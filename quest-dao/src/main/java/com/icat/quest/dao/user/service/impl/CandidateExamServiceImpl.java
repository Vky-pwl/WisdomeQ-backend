package com.icat.quest.dao.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.AnswerType;
import com.icat.quest.common.vo.CandidateExamVo;
import com.icat.quest.common.vo.ExamMetadatDashboardVo;
import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.common.vo.PermissionVo;
import com.icat.quest.common.vo.TestConductorIdAndAttendeVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.CandidateExamDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.user.service.CandidateExamService;
import com.icat.quest.model.CandidateExam;
import com.icat.quest.model.Exam;
import com.icat.quest.model.TestConductorHasTestCode;
import com.icat.quest.service.UserHasPermissionService;

@Service
public class CandidateExamServiceImpl implements CandidateExamService {

	@Autowired
	private CandidateExamDao candidateExamDao;
	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private UserHasPermissionService		userHasPermissionService;
	
	@Override
	public Long createCandidateExam(CandidateExamVo candidateExamVo) throws Exception {
		if (candidateExamVo.getExamSectionHasQuestionId() == null ) {
			throw new Exception("Input error");
		}
		
		if(new Boolean(true).equals(candidateExamVo.getActive()) && candidateExamVo.getUserAnswer() == null) {
			throw new Exception("Input error");	
		}
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_1_TCHTCId", candidateExamVo.getTestConductorHasTestCodeId());
		paramsKayAndValues.put("_2_questionId", candidateExamVo.getExamSectionHasQuestionId());
		

		CandidateExam candidateExam = candidateExamDao.findEntityByParameter(CandidateExamDao.findByTCHTCIdAndQuestionId,
				paramsKayAndValues);
		if (candidateExam == null) {
			candidateExam = new CandidateExam(candidateExamVo.getTestConductorHasTestCodeId(),candidateExamVo.getExamSectionHasQuestionId(),
					candidateExamVo.getCandidateId(), AnswerType.valueOf(candidateExamVo.getUserAnswer()), true,
					new Date());
			candidateExamDao.create(candidateExam);
		} else {
			if(candidateExamVo.getUserAnswer() == null) {
				Long id = candidateExam.getCandidateExamId();
				candidateExamDao.delete(candidateExam);
				return id;
			}
			candidateExam.setUserAnswer(AnswerType.valueOf(candidateExamVo.getUserAnswer()));
			candidateExamVo.setActive(candidateExamVo.getActive());
			candidateExamDao.update(candidateExam);
		}

		return candidateExam.getCandidateExamId();
	}

	@Override
	public Map<String, Object> getExamListByCandidateId(Integer userId, Integer pageNo, Integer pageSize,
			Boolean attended, UserType userType) {
		Map<String, Object> responseMap = new HashMap<>();
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_userId", userId);
		List<TestConductorHasTestCode> testConductorHasTestCodeList = new ArrayList<TestConductorHasTestCode>();
		int startIndex = (pageSize * pageNo) - pageSize;
		List<ExamVo> examVos = new ArrayList<>();
		if (pageSize == 1) {
			if (attended != null) {
				paramsKayAndValues.put("_2_attended", attended);
				testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(
						TestConductorHasTestCodeDao.listExamByCandidateIdByAttended, paramsKayAndValues, null, null);
			} else {
				testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(
						TestConductorHasTestCodeDao.listExamByCandidateId, paramsKayAndValues, null, null);
			}
			responseMap.put("count", testConductorHasTestCodeList.size());
			examVos = transformExamVoList(testConductorHasTestCodeList);
			responseMap.put("examVoList", examVos);
		} else {
			if (attended != null) {
				paramsKayAndValues.put("_2_attended", attended);
				testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(
						TestConductorHasTestCodeDao.listExamByCandidateIdByAttended, paramsKayAndValues, startIndex,
						pageSize);
			} else {
				testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByParameter(
						TestConductorHasTestCodeDao.listExamByCandidateId, paramsKayAndValues, startIndex, pageSize);

			}
			examVos = transformExamVoList(testConductorHasTestCodeList);
			responseMap.put("examVoList", examVos);
		}
		List<Exam> examList =
				testConductorHasTestCodeList.stream()
						.map((testConductorHasTestCode)->testConductorHasTestCode.getExam())
						.collect(Collectors.toList());
		if (userType != null && userId != null) {
			Map<Integer, List<PermissionVo>> permissionMap = userHasPermissionService
					.getPermissionListGroupByExamId(userId, userType, examList);
			for (ExamVo examVo : examVos) {
				if (permissionMap.containsKey(examVo.getExamId())) {
					examVo.setPermissionVoList(permissionMap.get(examVo.getExamId()));
				}
			}
		}
		
		return responseMap;
	}

	@Override
	public ExamMetadatDashboardVo getExamMetadatDashboardVo(Integer candidateId) {

		List<TestConductorIdAndAttendeVo> testConductorIdAndAttendeVos = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		Gson gson = builder.create();
		Map<String, Object> paramsKeyAndValues = new HashMap<String, Object>();
		paramsKeyAndValues.put("_1_candidateId", candidateId);

		List<Object[]> testConductorIdAndAttendeArrayList = testConductorHasTestCodeDao.listCompositeSqlQuery(
				TestConductorHasTestCodeDao.findAllIdAndAttendeByCandidateId, paramsKeyAndValues);

		try {
			List<Object> testConductorIdAndAttendeObjectList = testConductorHasTestCodeDao.convertToObjectList(
					testConductorIdAndAttendeArrayList, new TestConductorIdAndAttendeVo(), null, null);
			testConductorIdAndAttendeVos = mapper.readValue(gson.toJson(testConductorIdAndAttendeObjectList),
					new TypeReference<List<TestConductorIdAndAttendeVo>>() {
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
		int takenCount = 0;
		int remainingCount = 0;
		int totalCount = 0;
		if (testConductorIdAndAttendeVos != null) {
			for (TestConductorIdAndAttendeVo testConductorIdAndAttendeVo : testConductorIdAndAttendeVos) {
				totalCount++;
				if (testConductorIdAndAttendeVo.getAttended()) {
					takenCount++;
				} else {
					remainingCount++;
				}
			}
		}
		return new ExamMetadatDashboardVo(totalCount, takenCount, remainingCount);
	}

	private List<ExamVo> transformExamVoList(List<TestConductorHasTestCode> testConductorHasTestCodeList) {
		List<ExamVo> examVoList = new ArrayList<ExamVo>();
		if (testConductorHasTestCodeList != null) {
			testConductorHasTestCodeList.forEach(testConductorHasTestCode -> {
				Exam exam = testConductorHasTestCode.getExam();
				ExamVo examVo = Transformer.EXAM_TRANSFORMER.transform(exam);
				examVo.setExamStatus(testConductorHasTestCode.getStatus());
				examVo.setRemainingTime(testConductorHasTestCode.getRemainingTime() != null
						? testConductorHasTestCode.getRemainingTime()
						: exam.getDurationInSeconds());
				examVo.setTinyKey(testConductorHasTestCode.getTinyKey());
				examVo.setSectionRemainingTime(testConductorHasTestCode.getSectionRemainingTime());
				examVo.setCurrentSectionId(testConductorHasTestCode.getCurrentSectionId());
				examVo.setTestConductorHasTestCodeId(testConductorHasTestCode.getTestConductorHasTestCodeId());
				examVoList.add(examVo);
			});
		}
		return examVoList;
	}

	
}
