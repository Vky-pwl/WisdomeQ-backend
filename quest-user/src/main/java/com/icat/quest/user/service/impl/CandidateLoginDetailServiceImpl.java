package com.icat.quest.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.auth.login.service.AuthServiceImpl;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.CandidateQuestionStatusVo;
import com.icat.quest.common.vo.CandidateVo;
import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.common.vo.UserType;
import com.icat.quest.dao.ExamSectionDao;
import com.icat.quest.dao.ExamSectionHasQuestionDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.user.service.CandidateQuestionStatusService;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.Exam;
import com.icat.quest.model.ExamSection;
import com.icat.quest.model.TestConductorHasTestCode;
import com.icat.quest.user.service.CandidateLoginDetailService;

@Service
public class CandidateLoginDetailServiceImpl implements CandidateLoginDetailService {

	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private AuthServiceImpl authServiceImpl;
	@Autowired
	private ExamSectionDao examSectionDao;
	@Autowired
	private ExamSectionHasQuestionDao examSectionHasQuestionDao;
	@Autowired
	private ExamQuestionCacheService examQuestionCacheService;
	private Long sectionRemainingTime = 0L;
	private Integer currentSectionId = null;
	@Autowired
	private CandidateQuestionStatusService candidateQuestionStatusService;

	@Override
	public Map<String, Object> getCandidateLoginDetails(String token, Boolean flag) {
		Map<String, Object> responseMap = new HashMap<>();
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_urlKey", token);
		TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao
				.findEntityByParameter(TestConductorHasTestCodeDao.findAllByUrlKey, paramsKayAndValues);
		if (testConductorHasTestCode == null) {
			return null;
		}

		Candidate candidate = testConductorHasTestCode.getUser();
		Exam exam = testConductorHasTestCode.getExam();
		responseMap.put("userVo", getUserVo(candidate));
		responseMap.put("examVo", getExamVo(exam, flag, candidate.getUserId(), testConductorHasTestCode));
		responseMap.put("examStatusVo",
				examQuestionCacheService.getExamStatus(testConductorHasTestCode.getTestConductorHasTestCodeId()));
		return responseMap;
	}

	private ExamVo getExamVo(Exam exam, Boolean flag, Integer candidateId,
			TestConductorHasTestCode testConductorHasTestCode) {
		ExamVo examVo = Transformer.EXAM_TRANSFORMER.transform(exam);
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", exam.getExamId());
		List<ExamSection> examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllByExamId,
				paramsKayAndValues, null, null);
		examVo.setExamSectionVoList(transformExamSectionVoList(examSectionList, flag, candidateId,testConductorHasTestCode.getTestConductorHasTestCodeId()));
		examVo.setExamStatus(testConductorHasTestCode.getStatus());
		examVo.setRemainingTime(
				testConductorHasTestCode.getRemainingTime() != null ? testConductorHasTestCode.getRemainingTime()
						: exam.getDurationInSeconds());
		examVo.setTinyKey(testConductorHasTestCode.getTinyKey());
		examVo.setSectionRemainingTime(testConductorHasTestCode.getSectionRemainingTime() != null
				? testConductorHasTestCode.getSectionRemainingTime()
				: sectionRemainingTime);
		examVo.setCurrentSectionId(
				testConductorHasTestCode.getCurrentSectionId() != null ? testConductorHasTestCode.getCurrentSectionId()
						: currentSectionId);
		return examVo;
	}

	private List<ExamSectionVo> transformExamSectionVoList(List<ExamSection> examSectionList, Boolean flag,
			Integer candidateId, Integer testConductorHasTestCodeId) {
		List<ExamSectionVo> examSectionVoList = new ArrayList<ExamSectionVo>();
		if (examSectionList != null) {
			for (ExamSection examSection : examSectionList) {
				ExamSectionVo examSectionVo = Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection);
				examSectionVo.setRandomMap(getRandomMap(examSection.getExamSectionId(), flag, candidateId,testConductorHasTestCodeId));
				if (examSection.getSequence() == 1) {
					currentSectionId = examSection.getExamSectionId();
					sectionRemainingTime = examSection.getDurationInSeconds();
				}
				examSectionVoList.add(examSectionVo);
			}
		}
		return examSectionVoList;
	}

	@SuppressWarnings("unchecked")
	private List<CandidateQuestionStatusVo> getRandomMap(Integer examSectionId, Boolean flag, Integer candidateId, Integer testConductorHasTestCodeId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examSectionId", examSectionId);
		List<Integer> idList = (List<Integer>) examSectionHasQuestionDao
				.listSingleRowResult(ExamSectionHasQuestionDao.findAllIdByExamSectionId, paramsKayAndValues);
		if (flag && idList != null && !idList.isEmpty()) {
			return candidateQuestionStatusService.getList(idList, candidateId,testConductorHasTestCodeId);
		}

		return new ArrayList<>();
	}

	private CandidateVo getUserVo(Candidate candidate) {
		CandidateVo candidateVo = Transformer.CANDIDATE_TRANSFORMER.transform(candidate);
		candidateVo.setToken(authServiceImpl.createToken(candidate.getUserId(), UserType.CANDIDATE,
				candidate.getCollege() != null ? candidate.getCollege().getCollegeId() : null));
		return candidateVo;
	}

}
