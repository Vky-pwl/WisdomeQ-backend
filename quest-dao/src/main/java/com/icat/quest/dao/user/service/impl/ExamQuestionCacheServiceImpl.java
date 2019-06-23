package com.icat.quest.dao.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.ExamStatus;
import com.icat.quest.common.vo.ExamStatusVo;
import com.icat.quest.common.vo.SectionQuestionKeyMap;
import com.icat.quest.dao.CandidateExamSummaryDao;
import com.icat.quest.dao.ExamSectionHasQuestionDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.dao.user.service.ExamQuestionCacheService;
import com.icat.quest.dao.user.service.TestConductorHasTestCodeUserService;
import com.icat.quest.generic.dao.framework.impl.CustomGenericTypeImpl;
import com.icat.quest.model.ExamSectionHasQuestion;
import com.icat.quest.model.TestConductorHasTestCode;

public class ExamQuestionCacheServiceImpl implements ExamQuestionCacheService {

	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private ExamSectionHasQuestionDao examSectionHasQuestionDao;
	@Autowired
	private CandidateExamSummaryDao candidateExamSummaryDao;
	@Autowired
	private TestConductorHasTestCodeUserService	testConductorHasTestCodeUserService;

	private static Map<Integer, ExamSectionHasQuestionVo> examSectionHasQuestionMap = new HashMap<>();
	private static Map<Integer, ExamStatusVo> userExamStatus = new HashMap<>();
	private static Map<Integer, Map<Integer, List<Integer>>> userExamSectQuestionIdList = new HashMap<>();
	private static Map<Integer, Long> sectionHasSectionDuration = new HashMap<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamQuestionCacheServiceImpl.class);
	
	@Override
	public ExamStatusVo getExamStatus(Integer TCHTCId) {
		if (!userExamStatus.containsKey(TCHTCId)) {
			loadExamStatus(TCHTCId);
		}
		return userExamStatus.get(TCHTCId);
	}

	@Override
	public List<ExamSectionHasQuestionVo> getQuestionListByExamId(Integer examId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		List<ExamSectionHasQuestion> list = examSectionHasQuestionDao
				.listEntityByParameter(ExamSectionHasQuestionDao.findAllByExamId, paramsKayAndValues, null, null);
		List<ExamSectionHasQuestionVo> examSectionHasQuestionVos = new ArrayList<>();
		if (list != null && list.size() > 0) {
			list.forEach(examSectionHasQuestion -> {
				examSectionHasQuestionVos
						.add(Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion));
			});
		}
		return examSectionHasQuestionVos;
	}

	@Override
	public void updateExamStatus(ExamStatusVo examStatusVo) {
		ExamStatusVo examStatusVo2 = userExamStatus.get(examStatusVo.getTestConductorHasTestCodeId());

		if (examStatusVo2 == null) {
			loadExamStatus(examStatusVo.getTestConductorHasTestCodeId());
			examStatusVo2 = userExamStatus.get(examStatusVo.getTestConductorHasTestCodeId());
		}
		examStatusVo2.setUpdateFlag(false);
		if (examStatusVo.getCurrentSectionRemaingTime() != null && examStatusVo.getCurrentSectionRemaingTime() >= 0
				&& examStatusVo2.getCurrentSectionRemaingTime() > examStatusVo.getCurrentSectionRemaingTime()) {
			examStatusVo2.setCurrentSectionRemaingTime(examStatusVo.getCurrentSectionRemaingTime());
		}
		if (examStatusVo.getExamRemaingTime() != null && examStatusVo.getExamRemaingTime() >= 0
				&& examStatusVo2.getExamRemaingTime() > examStatusVo.getExamRemaingTime()) {
			examStatusVo2.setExamRemaingTime(examStatusVo.getExamRemaingTime());
		}
		if (examStatusVo.getCurrentSectionStatus() != null) {
			examStatusVo2.setCurrentSectionStatus(examStatusVo.getCurrentSectionStatus());
		}
		if (examStatusVo.getCurrentExamStatus() != null) {
			examStatusVo2.setCurrentExamStatus(examStatusVo.getCurrentExamStatus());
		}
		if (examStatusVo.getCurrentQuestionStatus() != null) {
			examStatusVo2.setCurrentQuestionStatus(examStatusVo.getCurrentQuestionStatus());
		}

	}

	@Override
	public void removeExamStatus(Integer TCHTCId) {
		if (userExamStatus.containsKey(TCHTCId)) {
			userExamStatus.remove(TCHTCId);
		}
		removeSectionQuestionIds(TCHTCId);
	}

	@Override
	public void removeSectionQuestionIds(Integer TCHTCId) {
		if (userExamSectQuestionIdList.containsKey(TCHTCId)) {
			userExamSectQuestionIdList.remove(TCHTCId);
		}
	}

	@Override
	public ExamSectionHasQuestionVo getQuestion(Integer userId, Integer examId, Long examRemainingTime,
			Long sectionRemainingTime, Boolean flag, Integer examSectionHasQuestionId, Integer TCHTCId) {
		Integer questionId = getQuestAndupdateNextQuestion(userId, examId, sectionRemainingTime, examRemainingTime,
				flag,TCHTCId);
		if (examSectionHasQuestionId != null && questionId != null && questionId.equals(examSectionHasQuestionId)) {
			questionId = getQuestAndupdateNextQuestion(userId, examId, sectionRemainingTime, examRemainingTime, flag,TCHTCId);
		}
		if (questionId == null) {
			return null;
		}
		return getQuestion(questionId, examId);
	}

	private void cacheQuestion(Integer examId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		List<ExamSectionHasQuestion> examSectionHasQuestions = examSectionHasQuestionDao
				.listEntityByParameter(ExamSectionHasQuestionDao.findAllByExamId, paramsKayAndValues, null, null);
		if (examSectionHasQuestions != null) {
			for (ExamSectionHasQuestion examSectionHasQuestion : examSectionHasQuestions) {
				ExamSectionHasQuestionVo examSectionHasQuestionVo = Transformer.EXAMSECTION_QUESTION_TRANSFORMER
						.transform(examSectionHasQuestion);
				examSectionHasQuestionVo.getQuestionBankVo().setCorrectAnswer(null);
				examSectionHasQuestionVo.getQuestionBankVo().setCorrectOption(null);
				examSectionHasQuestionVo.getQuestionBankVo().getOptions().forEach(optionVo -> {
					optionVo.setMarks(0f);
				});
				examSectionHasQuestionMap.put(examSectionHasQuestion.getExamSectionHasQuestionId(),
						examSectionHasQuestionVo);
			}
		}
	}

	private ExamSectionHasQuestionVo getQuestion(Integer questionId, Integer examId) {
		ExamSectionHasQuestionVo examSectionHasQuestionVo = null;
		for (int i = 0; i < 3; i++) {
			if (!examSectionHasQuestionMap.containsKey(questionId)) {
				cacheQuestion(examId);
				examSectionHasQuestionVo = examSectionHasQuestionMap.get(questionId);
			} else {
				examSectionHasQuestionVo = examSectionHasQuestionMap.get(questionId);
			}
			if (examSectionHasQuestionVo != null) {
				break;
			}

		}
		return examSectionHasQuestionVo;
	}

	@Override
	public ExamSectionHasQuestionVo getQuestionAndUpdateStatus(Integer examId, Integer userId, Integer questionId,
			Long examRemainingTime, Long sectionRemainingTime, Integer TCHTCId) {
		ExamStatusVo examStatusVo = getExamStatus(TCHTCId);
		if (examStatusVo == null) {
			LOGGER.warn("Exam Status null: " + questionId);
			return null;
		}
		examStatusVo.setUpdateFlag(false);
		if (sectionRemainingTime != null && examStatusVo.getCurrentSectionRemaingTime() > sectionRemainingTime) {
			examStatusVo.setCurrentSectionRemaingTime(sectionRemainingTime);
		}
		if (examRemainingTime != null && examStatusVo.getExamRemaingTime() > examRemainingTime) {
			examStatusVo.setExamRemaingTime(examRemainingTime);
		}
		examStatusVo.setCurrentQuestionStatus(ExamStatus.INPROGRESS);
		examStatusVo.setCurrentSectionStatus(ExamStatus.INPROGRESS);
		examStatusVo.setCurrentExamStatus(ExamStatus.INPROGRESS);
		return getQuestion(questionId, examId);
	}

	private void loadExamStatus(Integer TCHTCId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_TCHTCID", TCHTCId);
		TestConductorHasTestCode testConductorHasTestCode = testConductorHasTestCodeDao
				.findEntityByParameter(TestConductorHasTestCodeDao.findById, paramsKayAndValues);
		if (testConductorHasTestCode != null) {
			ExamStatusVo examStatusVo = new ExamStatusVo();
			examStatusVo.setCurrentExamId(testConductorHasTestCode.getExam().getExamId());
			examStatusVo.setCurrentExamStatus(testConductorHasTestCode.getStatus());
			examStatusVo.setExamRemaingTime(testConductorHasTestCode.getRemainingTime());
			examStatusVo.setCurrentQuestionId(testConductorHasTestCode.getCurrentQuestionId());
			examStatusVo.setCurrentQuestionStatus(testConductorHasTestCode.getCurrentQuestionStatus());
			examStatusVo.setCurrentSectionId(testConductorHasTestCode.getCurrentSectionId());
			examStatusVo.setCurrentSectionStatus(testConductorHasTestCode.getCurrentSectionStatus());
			examStatusVo.setCurrentSectionRemaingTime(testConductorHasTestCode.getSectionRemainingTime());
			examStatusVo.setTestConductorHasTestCodeId(testConductorHasTestCode.getTestConductorHasTestCodeId());
			userExamStatus.put(testConductorHasTestCode.getTestConductorHasTestCodeId(), examStatusVo);
		}
	}

	private Integer getQuestAndupdateNextQuestion(Integer userId, Integer examId, Long sectionRemainingTime,
			Long examRemainingTime, Boolean flag, Integer TCHTCId) {

		ExamStatusVo examStatusVo = getExamStatus(TCHTCId);
		if (sectionRemainingTime != null && examStatusVo.getCurrentSectionRemaingTime() > sectionRemainingTime) {
			examStatusVo.setCurrentSectionRemaingTime(sectionRemainingTime);
		}
		if (examRemainingTime != null && examStatusVo.getExamRemaingTime() > examRemainingTime) {
			examStatusVo.setExamRemaingTime(examRemainingTime);
		}
		Integer questionId = examStatusVo.getCurrentQuestionId();
		examStatusVo.setUpdateFlag(false);
		if (!flag) {
			examStatusVo.setCurrentSectionRemaingTime(sectionRemainingTime);
			examStatusVo.setExamRemaingTime(examRemainingTime);
			return questionId;
		}
		Map<Integer, List<Integer>> sectionQuestionIdList = getSectionQuestionIds(TCHTCId, examId);
		if (sectionQuestionIdList == null || sectionQuestionIdList.isEmpty()) {
			examStatusVo.setCurrentExamStatus(ExamStatus.COMPLETED);
			examStatusVo.setCurrentSectionStatus(ExamStatus.COMPLETED);
			examStatusVo.setCurrentQuestionStatus(ExamStatus.COMPLETED);
			examStatusVo.setCurrentSectionRemaingTime(0l);
			examStatusVo.setExamRemaingTime(0l);
			testConductorHasTestCodeUserService.submitExam(TCHTCId, userId);
			return null;
		}
		List<Integer> ids = sectionQuestionIdList.get(examStatusVo.getCurrentSectionId());
		if (ids != null && ids.size() > 0) {
			ids.remove(examStatusVo.getCurrentQuestionId());
		}
		if (ids == null || ids.isEmpty()) {
			if (sectionQuestionIdList.containsKey(examStatusVo.getCurrentSectionId())) {
				sectionQuestionIdList.remove(examStatusVo.getCurrentSectionId());
				if (sectionQuestionIdList == null || sectionQuestionIdList.isEmpty()) {
					examStatusVo.setCurrentExamStatus(ExamStatus.COMPLETED);
					examStatusVo.setCurrentSectionStatus(ExamStatus.COMPLETED);
					examStatusVo.setCurrentQuestionStatus(ExamStatus.COMPLETED);
					examStatusVo.setCurrentSectionRemaingTime(0l);
					examStatusVo.setExamRemaingTime(0l);
					testConductorHasTestCodeUserService.submitExam(TCHTCId, userId);
					return null;
				}
			}
			for (Map.Entry<Integer, List<Integer>> entryMap : sectionQuestionIdList.entrySet()) {
				examStatusVo.setCurrentSectionId(entryMap.getKey());
				ids = entryMap.getValue();
				examStatusVo.setCurrentQuestionId(ids.get(0));
				examStatusVo.setCurrentQuestionStatus(ExamStatus.PRISTINE);
				examStatusVo.setCurrentSectionStatus(ExamStatus.PRISTINE);
				examStatusVo.setCurrentSectionRemaingTime(
						sectionHasSectionDuration.get(examStatusVo.getCurrentSectionId()));
				// examStatusVo.setExamRemaingTime(examRemainingTime);
				break;
			}
			return null;
		}

		examStatusVo.setCurrentQuestionId(ids.get(0));
		examStatusVo.setCurrentQuestionStatus(ExamStatus.INPROGRESS);
		examStatusVo.setCurrentSectionStatus(ExamStatus.INPROGRESS);
		examStatusVo.setCurrentExamStatus(ExamStatus.INPROGRESS);
		// examStatusVo.setCurrentSectionRemaingTime(sectionRemainingTime);
		// examStatusVo.setExamRemaingTime(examRemainingTime);
		return examStatusVo.getCurrentQuestionId();
	}

	private Map<Integer, List<Integer>> getSectionQuestionIds(Integer TCHTCId, Integer examId) {
		if (!userExamSectQuestionIdList.containsKey(TCHTCId)) {
			loadUserSectionQuestionId(TCHTCId, examId);
		}
		return userExamSectQuestionIdList.get(TCHTCId);
	}

	private void loadUserSectionQuestionId(Integer TCHTCId, Integer examId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("TCHTCId", TCHTCId);
		paramsKayAndValues.put("examId", examId);

		List<Object[]> examSectionQuestionIds = candidateExamSummaryDao
				.listCompositeSqlQuery(CandidateExamSummaryDao.findAllUnAttemptExamQuestionIdByTCHTCId, paramsKayAndValues);
		Map<Integer, List<Integer>> sectionQuestionIdList = new HashMap<>();
		if (examSectionQuestionIds != null) {
			for (Object[] obj : examSectionQuestionIds) {
				SectionQuestionKeyMap sectionQuestionKeyMap = new CustomGenericTypeImpl<SectionQuestionKeyMap>(
						SectionQuestionKeyMap.class).converter(obj, null);
				if (sectionQuestionKeyMap != null) {
					if (!sectionQuestionIdList.containsKey(sectionQuestionKeyMap.getExamSectionId())) {
						List<Integer> ids = new ArrayList<>();
						ids.add(sectionQuestionKeyMap.getExamSectionHasQuestionId());
						sectionQuestionIdList.put(sectionQuestionKeyMap.getExamSectionId(), ids);
						sectionHasSectionDuration.put(sectionQuestionKeyMap.getExamSectionId(),
								sectionQuestionKeyMap.getDuration());
					} else {
						sectionQuestionIdList.get(sectionQuestionKeyMap.getExamSectionId())
								.add(sectionQuestionKeyMap.getExamSectionHasQuestionId());
					}
				}
			}
		}

		for (Integer sectionId : sectionQuestionIdList.keySet()) {
			Collections.shuffle(sectionQuestionIdList.get(sectionId));
		}

		userExamSectQuestionIdList.put(TCHTCId, sectionQuestionIdList);
	}

	@Override
	public void updateExamStatusBatchUpdate() {
		Map<Integer, ExamStatusVo> userExamStatusMap = userExamStatus;
		if(userExamStatusMap.isEmpty()) {
			return;
		}
		
		Set<Integer> tchtcIdList = userExamStatusMap.keySet();
		List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao.listEntityByIdList(tchtcIdList.stream().collect(Collectors.toList()));

		if (testConductorHasTestCodeList != null && testConductorHasTestCodeList.size() > 0) {
			for (TestConductorHasTestCode testConductorHasTestCode : testConductorHasTestCodeList) {
				if (userExamStatus.containsKey(testConductorHasTestCode.getTestConductorHasTestCodeId()) && testConductorHasTestCode.getSubmitDate() == null) {
					ExamStatusVo examStatusVo = userExamStatus.get(testConductorHasTestCode.getTestConductorHasTestCodeId());
					// testConductorHasTestCode.setAttended(true);
					if (testConductorHasTestCode.getExamStartDate() == null) {
						testConductorHasTestCode.setExamStartDate(new Date());
					}
					testConductorHasTestCode.setStatus(examStatusVo.getCurrentExamStatus());
					if (examStatusVo.getExamRemaingTime() != null) {
						testConductorHasTestCode.setRemainingTime(examStatusVo.getExamRemaingTime());
					}
					testConductorHasTestCode.setCurrentSectionId(examStatusVo.getCurrentSectionId());
					testConductorHasTestCode.setCurrentSectionStatus(examStatusVo.getCurrentSectionStatus());
					if (examStatusVo.getCurrentSectionRemaingTime() != null) {
						testConductorHasTestCode.setSectionRemainingTime(examStatusVo.getCurrentSectionRemaingTime());
					}
					testConductorHasTestCode.setCurrentQuestionId(examStatusVo.getCurrentQuestionId());
					testConductorHasTestCode.setCurrentQuestionStatus(examStatusVo.getCurrentQuestionStatus());
					testConductorHasTestCode.setLastModified(new Date());
				}
			}
			testConductorHasTestCodeDao.updateBatch(testConductorHasTestCodeList);
		}
		LOGGER.debug("Exam Status Updated: ");
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void clearQuestionCacheJob() {
		Map<Integer, ExamSectionHasQuestionVo> examQuestionMap = examSectionHasQuestionMap;
		if (examQuestionMap != null && examQuestionMap.size() > 0) {
			Map<String, Object> paramsKayAndValues = new HashMap<>();
			Date prevDate = new Date();
			prevDate.setHours(prevDate.getHours() - 12);
			paramsKayAndValues.put("_1_prevDate", prevDate);
			paramsKayAndValues.put("_2_curDate", new Date());
			List<Integer> questionIdList = (List<Integer>) candidateExamSummaryDao.listSingleRowResult(
					CandidateExamSummaryDao.findAllQuesIdAttendedByCandInLastHour, paramsKayAndValues);
			if (questionIdList != null && questionIdList.size() > 0) {
				examSectionHasQuestionMap = examQuestionMap.entrySet().stream()
						.filter(map -> questionIdList.contains(map.getKey()))
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			}
		}
	}

}
