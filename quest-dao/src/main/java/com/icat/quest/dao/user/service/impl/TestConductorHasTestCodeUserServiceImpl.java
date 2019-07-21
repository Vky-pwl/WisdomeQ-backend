package com.icat.quest.dao.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExamStatus;
import com.icat.quest.common.vo.ExamStatusVo;
import com.icat.quest.common.vo.TestConductorHasTestCodeVo;
import com.icat.quest.dao.CandidateExamSummaryDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;
import com.icat.quest.dao.user.service.TestConductorHasTestCodeUserService;
import com.icat.quest.model.CandidateExamSummary;
import com.icat.quest.model.TestConductorHasTestCode;

@Service
public class TestConductorHasTestCodeUserServiceImpl implements TestConductorHasTestCodeUserService {

	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private CandidateExamSummaryDao candidateExamSummaryDao;
	@Autowired
	private ExamQuestionCacheService	examQuestionCacheService;
	
	@Override
	public void submitExam(Integer tchtcId, Integer userId) {
		TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao.read(tchtcId);
		ExamStatusVo examStatusVo = examQuestionCacheService.getExamStatus(tchtcId);
		examQuestionCacheService.removeExamStatus(tchtcId);
		
		testConductorHasTestCode.setAttended(true);
		testConductorHasTestCode.setStatus(ExamStatus.COMPLETED);
		testConductorHasTestCode.setCurrentQuestionStatus(ExamStatus.COMPLETED);
		testConductorHasTestCode.setCurrentSectionStatus(ExamStatus.COMPLETED);
		testConductorHasTestCode.setSubmitDate(new Date());
		testConductorHasTestCode.setLastModified(new Date());
		testConductorHasTestCode.setLastModifiedBy(new Long(userId));
		testConductorHasTestCode.setCurrentQuestionId(examStatusVo.getCurrentQuestionId());
		testConductorHasTestCode.setCurrentSectionId(examStatusVo.getCurrentSectionId());
		testConductorHasTestCode.setRemainingTime(examStatusVo.getExamRemaingTime());
		testConductorHasTestCode.setSectionRemainingTime(examStatusVo.getCurrentSectionRemaingTime());
		setTotalMarksAndAttempts(testConductorHasTestCode.getTestConductorHasTestCodeId(), testConductorHasTestCode);
		testConductorHasTestCodeDao.update(testConductorHasTestCode);

	}

	
	private void setTotalMarksAndAttempts(Integer tchtcId, TestConductorHasTestCode testConductorHasTestCode) {
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_1_TCHTCId", tchtcId);
		List<CandidateExamSummary> candidateExamSummarieList = candidateExamSummaryDao
				.listEntityByParameter(CandidateExamSummaryDao.findByTCHTCID, paramsKayAndValues, null, null);
		if (candidateExamSummarieList == null) {
			return;
		}
		testConductorHasTestCode.setTotalAttempts(candidateExamSummarieList.size());
		testConductorHasTestCode.setTotalCorrectAnswer(0);
		testConductorHasTestCode.setUserTotalMarks(0f);
		candidateExamSummarieList.forEach(candidateExamSummary -> {
			if(!candidateExamSummary.getSicoFlag()) {
			if (candidateExamSummary.getAnswerFlag()) {
				testConductorHasTestCode.setUserTotalMarks(
						testConductorHasTestCode.getUserTotalMarks() + candidateExamSummary.getQuestionMarks());
				testConductorHasTestCode.setTotalCorrectAnswer(testConductorHasTestCode.getTotalCorrectAnswer() + 1);
				
			} else {
				testConductorHasTestCode.setUserTotalMarks(
						testConductorHasTestCode.getUserTotalMarks() - candidateExamSummary.getQuestionNegativeMarks());
			}
			}
		});
	}

	@Override
	public TestConductorHasTestCodeVo getResultByExamId(Integer tchtcId, Integer userId) throws Exception {
		return Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER.transform(testConductorHasTestCodeDao.read(tchtcId));
	}
	 
	 


}
