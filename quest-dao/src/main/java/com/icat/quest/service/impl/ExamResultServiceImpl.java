package com.icat.quest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.utils.ResultPercentileService;
import com.icat.quest.common.utils.SkillAssessmentReport;
import com.icat.quest.common.vo.CandidateExamSummaryDescVo;
import com.icat.quest.common.vo.CandidateResultVo;
import com.icat.quest.common.vo.EAIResultVo;
import com.icat.quest.common.vo.ExamResultCompositeVo;
import com.icat.quest.common.vo.ExamResultShortDescVo;
import com.icat.quest.common.vo.Level;
import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.common.vo.SectionResultVo;
import com.icat.quest.common.vo.TestConductorHasTestCodeVo;
import com.icat.quest.common.vo.UserMarksVo;
import com.icat.quest.dao.CandidateDao;
import com.icat.quest.dao.CandidateExamSummaryDao;
import com.icat.quest.dao.QuestionCategoryDao;
import com.icat.quest.dao.TestConductorHasTestCodeDao;
import com.icat.quest.generic.dao.framework.impl.CustomGenericTypeImpl;
import com.icat.quest.model.Candidate;
import com.icat.quest.model.CandidateExamSummary;
import com.icat.quest.model.QuestionCategory;
import com.icat.quest.model.TestConductorHasTestCode;
import com.icat.quest.service.ExamResultService;

@Service
public class ExamResultServiceImpl implements ExamResultService {
	@Autowired
	private CandidateExamSummaryDao candidateExamSummaryDao;
	@Autowired
	private TestConductorHasTestCodeDao testConductorHasTestCodeDao;
	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private QuestionCategoryDao questionCategoryDao;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamResultServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCandRankExamList(int pageSize, int pageNo, Integer collegeId, Integer candidateId,
												   Integer specilizationId, Float percentile) {

		Map<String, Object> result = new HashMap<>();
		List<Integer> idList = null;
		Map<String, Object> param = new HashMap<>();
		param.put("_1_userId", candidateId);
		if (collegeId == null && specilizationId == null) {
			idList = (List<Integer>) testConductorHasTestCodeDao
					.listSingleRowResult(TestConductorHasTestCodeDao.findAllIdByCandId, param);
		} else if (collegeId != null && specilizationId == null) {
			param.put("_3_collegeId", collegeId);
			idList = (List<Integer>) testConductorHasTestCodeDao
					.listSingleRowResult(TestConductorHasTestCodeDao.findAllIdByCandIdCollId, param);
		} else if (collegeId == null && specilizationId != null) {
			param.put("_2_specializationId", specilizationId);
			idList = (List<Integer>) testConductorHasTestCodeDao
					.listSingleRowResult(TestConductorHasTestCodeDao.findAllIdByCandIdAndSpecId, param);
		} else {
			param.put("_3_collegeId", collegeId);
			param.put("_2_specializationId", specilizationId);
			idList = (List<Integer>) testConductorHasTestCodeDao
					.listSingleRowResult(TestConductorHasTestCodeDao.findAllIdByCandIdCollIdAndSpecId, param);
		}
		if (idList == null || idList.isEmpty()) {
			return result;
		}
		int startIndex = (pageNo * pageSize) - pageSize;
		param.put("count", idList.size());
		List<TestConductorHasTestCode> testConductorHasTestCodeList = testConductorHasTestCodeDao
				.listEntityByIdList(idList.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList()));
		Map<Integer, List<TestConductorHasTestCode>> mapTestCondTestCodeList = new HashMap<>();
		for (TestConductorHasTestCode testConductorHasTestCode : testConductorHasTestCodeList) {
			if (mapTestCondTestCodeList.containsKey(testConductorHasTestCode.getExam().getExamId())) {
				mapTestCondTestCodeList.get(testConductorHasTestCode.getExam().getExamId())
						.add(testConductorHasTestCode);
			} else {
				List<TestConductorHasTestCode> testCodes = new ArrayList<>();
				testCodes.add(testConductorHasTestCode);
				mapTestCondTestCodeList.put(testConductorHasTestCode.getExam().getExamId(), testCodes);
			}
		}
		List<TestConductorHasTestCodeVo> testConductorHasTestCodeVos = new ArrayList<>();
		for (Integer examId : mapTestCondTestCodeList.keySet()) {
			List<TestConductorHasTestCode> testConductorHasTestCodes = mapTestCondTestCodeList.get(examId);
			testConductorHasTestCodes.sort((TestConductorHasTestCode t1, TestConductorHasTestCode t2) -> t2
					.getUserTotalMarks().compareTo(t1.getUserTotalMarks()));
			int rank = 0;
			float marks = 0;
			for (TestConductorHasTestCode testConductorHasTestCode : testConductorHasTestCodes) {
				if (marks < testConductorHasTestCode.getUserTotalMarks()) {
					rank++;
					marks = testConductorHasTestCode.getUserTotalMarks();
				}
				if (testConductorHasTestCode.getUser().getUserId().equals(candidateId)) {
					TestConductorHasTestCodeVo testConductorHasTestCodeVo = Transformer.TESTCONDUCTOR_TESTCODE_TRANSFORMER
							.transform(testConductorHasTestCode);
					testConductorHasTestCodeVo.setRank(rank);
					testConductorHasTestCodeVo.setTotalCandidate(testConductorHasTestCodes.size());
					testConductorHasTestCodeVos.add(testConductorHasTestCodeVo);
					break;
				}
			}
		}
		result.put("candidateExamList", testConductorHasTestCodeVos);
		return result;
	}

	@Override
	public Map<String, Object> getExamTotalAttemps(Integer pageNo, Integer pageSize, Integer collegeId,
												   Integer specilizationId) {
		List<ExamResultShortDescVo> examResultShortDescVoList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();

		List<Object[]> resultsObjectArray = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		if (collegeId == null && specilizationId == null) {
			resultsObjectArray = testConductorHasTestCodeDao
					.listCompositeSqlQuery(TestConductorHasTestCodeDao.findAllExamAttempCount, param);
		} else if (collegeId != null && specilizationId == null) {
			param.put("_1_collegeId", collegeId);
			resultsObjectArray = testConductorHasTestCodeDao
					.listCompositeSqlQuery(TestConductorHasTestCodeDao.findAllExamAttempCountByCollId, param);

		} else if (collegeId == null && specilizationId != null) {
			param.put("_2_specializationId", specilizationId);
			resultsObjectArray = testConductorHasTestCodeDao
					.listCompositeSqlQuery(TestConductorHasTestCodeDao.findAllExamAttempCountBySpeId, param);

		} else {
			param.put("_1_collegeId", collegeId);
			param.put("_2_specializationId", specilizationId);
			resultsObjectArray = testConductorHasTestCodeDao
					.listCompositeSqlQuery(TestConductorHasTestCodeDao.findAllExamAttempCountBySpcAndColl, param);

		}
		Integer startIndex = (pageNo * pageSize) - pageSize;
		if (resultsObjectArray != null && resultsObjectArray.size() > 0) {
			for (Object[] obj : resultsObjectArray.stream().skip(startIndex).limit(pageSize)
					.collect(Collectors.toList())) {
				examResultShortDescVoList
						.add(new CustomGenericTypeImpl<ExamResultShortDescVo>(ExamResultShortDescVo.class)
								.converter(obj, null));
			}
			resultMap.put("count", resultsObjectArray.size());
			resultMap.put("results", examResultShortDescVoList);
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> getResultsByExamId(Integer examId, Integer pageNo, Integer pageSize, Integer collegeId,
												  Integer specilizationId, Float percentile) {
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_2_examId", examId);

		List<CandidateExamSummary> candidateExamSummarieList = candidateExamSummaryDao
				.listEntityByParameter(CandidateExamSummaryDao.findAllByExamId, paramsKayAndValues, null, null);

		List<Object[]> examSummaryResultVoArray = new ArrayList<>();
		if (collegeId == null && specilizationId == null) {
			examSummaryResultVoArray = candidateExamSummaryDao
					.listCompositeSqlQuery(CandidateExamSummaryDao.findExamResultDescByExamId, paramsKayAndValues);
		} else if (collegeId != null && specilizationId == null) {
			paramsKayAndValues.put("_3_collegeId", collegeId);
			examSummaryResultVoArray = candidateExamSummaryDao.listCompositeSqlQuery(
					CandidateExamSummaryDao.findExamResultDescByExamIdAndCollegeId, paramsKayAndValues);

		} else if (collegeId == null && specilizationId != null) {
			paramsKayAndValues.put("_4_specializationId", specilizationId);
			examSummaryResultVoArray = candidateExamSummaryDao.listCompositeSqlQuery(
					CandidateExamSummaryDao.findExamResultDescByExamIdAndSpec, paramsKayAndValues);

		} else {
			paramsKayAndValues.put("_3_collegeId", collegeId);
			paramsKayAndValues.put("_4_specializationId", specilizationId);
			examSummaryResultVoArray = candidateExamSummaryDao.listCompositeSqlQuery(
					CandidateExamSummaryDao.findExamResultDescByExamIdAndCollegeIdAndSpec, paramsKayAndValues);

		}

		/*List<CandidateExamSummaryDescVo> candidateExamSummaryDescVos = new ArrayList<>();
		if (examSummaryResultVoArray != null && examSummaryResultVoArray.size() > 0) {
			for (Object[] obj : examSummaryResultVoArray) {
				candidateExamSummaryDescVos
						.add(new CustomGenericTypeImpl<CandidateExamSummaryDescVo>(CandidateExamSummaryDescVo.class)
								.converter(obj, null));
			}
		}*/

		List<CandidateExamSummaryDescVo> candidateExamSummaryDescVos =
				examSummaryResultVoArray.stream().map((examSummaryResultVo) -> {
					return new CustomGenericTypeImpl<CandidateExamSummaryDescVo>(CandidateExamSummaryDescVo.class)
							.converter(examSummaryResultVo, null);
				}).collect(Collectors.toList());

		if (candidateExamSummaryDescVos == null || candidateExamSummaryDescVos.isEmpty()
				|| candidateExamSummarieList == null || candidateExamSummarieList.isEmpty()) {
			return new HashMap<>();
		} else {
			Map<Integer, List<CandidateExamSummary>> mapCandExamSummList = candidateExamSummarieList.stream()
					.collect(Collectors.groupingBy(CandidateExamSummary::getCandidateId));
			Map<Integer, List<CandidateExamSummaryDescVo>> mapCandExamDescList = candidateExamSummaryDescVos.stream()
					.collect(Collectors.groupingBy(CandidateExamSummaryDescVo::getCandidateId));
			List<CandidateResultVo> candidateResultVos = new ArrayList<>();
			for (Integer candidateId : mapCandExamDescList.keySet()) {
				List<CandidateExamSummary> candidateExamSummaries = null;
				if (mapCandExamSummList.containsKey(candidateId)) {
					candidateExamSummaries = mapCandExamSummList.get(candidateId);
				}
				List<CandidateExamSummaryDescVo> candidateExamSummaryDescVoList = mapCandExamDescList.get(candidateId);
				if (candidateExamSummaries != null) {
					candidateResultVos.add(getCandidateResult(candidateExamSummaries, candidateExamSummaryDescVoList));
				}
			}

			candidateResultVos.sort(
					(CandidateResultVo c1, CandidateResultVo c2) -> c2.getPercentile().compareTo(c1.getPercentile()));
			int rank = 0;
			for (CandidateResultVo candidateResultVo : candidateResultVos) {
				candidateResultVo.setRank(++rank);
			}

			int startIndex = (pageNo * pageSize) - pageSize;

			Map<String, Object> mapResults = new HashMap<>();
			if (percentile != null) {
				candidateResultVos = candidateResultVos.stream()
						.filter(candidateResultVo -> candidateResultVo.getPercentile() >= percentile)
						.collect(Collectors.toList());
			}
			mapResults.put("count", candidateResultVos.size());
			mapResults.put("examResult",
					candidateResultVos.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList()));
			return mapResults;
		}
	}

	@Override
	public CandidateResultVo getCertificate(Integer tchtcId, Integer candidateId) {
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_1_TCHTCID", tchtcId);
		paramsKayAndValues.put("",candidateId);
		List<CandidateExamSummary> candidateExamSummarieList = candidateExamSummaryDao
				.listEntityByParameter(CandidateExamSummaryDao.findByTCHTCID, paramsKayAndValues, null, null);

		List<Object[]> examSummaryResultVoArray = candidateExamSummaryDao
				.listCompositeSqlQuery(CandidateExamSummaryDao.findExamResultDescByExamIdAndCanId, paramsKayAndValues);
		List<CandidateExamSummaryDescVo> candidateExamSummaryDescVos = new ArrayList<>();
		if (examSummaryResultVoArray != null && examSummaryResultVoArray.size() > 0) {
			for (Object[] obj : examSummaryResultVoArray) {
				candidateExamSummaryDescVos
						.add(new CustomGenericTypeImpl<CandidateExamSummaryDescVo>(CandidateExamSummaryDescVo.class)
								.converter(obj, null));
			}
		}

		if (candidateExamSummaryDescVos == null || candidateExamSummaryDescVos.isEmpty()
				|| candidateExamSummarieList == null || candidateExamSummarieList.isEmpty()) {
			return null;
		} else {
			return getCandidateResult(candidateExamSummarieList, candidateExamSummaryDescVos);
		}
	}

	private CandidateResultVo getCandidateResult(List<CandidateExamSummary> candidateExamSummarieList,
												 List<CandidateExamSummaryDescVo> candidateExamSummaryDescVos) {
		Map<Integer, SectionResultVo> mapSectionResult = mapSectionResult(candidateExamSummarieList);
		Map<Integer, List<Integer>> sectionIdAndQuestionCategoryIdList = new HashMap<>();
		CandidateResultVo candidateResultVo = new CandidateResultVo();
		int count = 0;
		for (CandidateExamSummaryDescVo candidateExamSummaryDescVo : candidateExamSummaryDescVos) {
			if (count == 0) {
				candidateResultVo.setCandidateFirstName(candidateExamSummaryDescVo.getCandidateFirstName());
				candidateResultVo.setCandidateLastName(candidateExamSummaryDescVo.getCandidateLastName());
				candidateResultVo.setContactEmail(candidateExamSummaryDescVo.getContactEmail());
				candidateResultVo.setContactNumber(candidateExamSummaryDescVo.getContactNumber());
				candidateResultVo.setCandidateId(candidateExamSummaryDescVo.getCandidateId());
				candidateResultVo.setExamId(candidateExamSummaryDescVo.getExamId());
				candidateResultVo.setExamName(candidateExamSummaryDescVo.getExamName());
				candidateResultVo.setTotalMarks(candidateExamSummaryDescVo.getTotalMarks());
				candidateResultVo.setTotalQuestion(candidateExamSummaryDescVo.getTotalQuestion());
				candidateResultVo.setExamCategoryName(candidateExamSummaryDescVo.getExamCategoryName());
				candidateResultVo.setExamDate(candidateExamSummaryDescVo.getStartTime() != null
						? candidateExamSummaryDescVo.getStartTime().getTime()
						: null);
				candidateResultVo.setCertificateCode(candidateExamSummaryDescVo.getTestCode());
			}
			SectionResultVo sectionResultVo = mapSectionResult.get(candidateExamSummaryDescVo.getExamSectionId());
			if (sectionResultVo != null) {
				sectionResultVo.setSectionName(candidateExamSummaryDescVo.getSectionName());
				if (!sectionIdAndQuestionCategoryIdList.containsKey(candidateExamSummaryDescVo.getExamSectionId())) {
					sectionResultVo.setTotalMarks(candidateExamSummaryDescVo.getSectionTotalMarks());
					sectionResultVo.setExamSectionId(candidateExamSummaryDescVo.getExamSectionId());
					sectionResultVo.setExamSectionName(candidateExamSummaryDescVo.getExamSectionName());
					sectionResultVo.setTotalQuestion(candidateExamSummaryDescVo.getSectionTotalQuestion());

					Map<String, Object> percMap = ResultPercentileService
							.getPercentile(sectionResultVo.getSectionName(), sectionResultVo.getUserTotalMarks());
					sectionResultVo.setGrade(percMap.get("grade").toString());
					sectionResultVo.setPercentile((float) percMap.get("percentile"));
					sectionResultVo.setLevel((Level) percMap.get("level"));
					candidateResultVo.setUserTotalMarks(
							candidateResultVo.getUserTotalMarks() + sectionResultVo.getUserTotalMarks());
					candidateResultVo.setTotalAttemptQuestion(
							candidateResultVo.getTotalAttemptQuestion() + sectionResultVo.getTotalAttemptQuestion());
					candidateResultVo.setTotalCorrectAnswer(
							candidateResultVo.getTotalCorrectAnswer() + sectionResultVo.getTotalCorrectAnswer());
					if (sectionResultVo.getQuestionCategoryVoList()
							.containsKey(candidateExamSummaryDescVo.getQuestionCategoryId())) {
						sectionResultVo.getQuestionCategoryVoList()
								.get(candidateExamSummaryDescVo.getQuestionCategoryId())
								.setQuestionSubCategoryName(candidateExamSummaryDescVo.getQuestionCategoryName());
					} else {
						QuestionCategoryVo questionCategoryVo = new QuestionCategoryVo(
								candidateExamSummaryDescVo.getQuestionCategoryId());
						questionCategoryVo
								.setQuestionSubCategoryName(candidateExamSummaryDescVo.getQuestionCategoryName());
						sectionResultVo.getQuestionCategoryVoList()
								.put(candidateExamSummaryDescVo.getQuestionCategoryId(), questionCategoryVo);
					}
					candidateResultVo.getSectionResultList().add(sectionResultVo);

					List<Integer> questCatList = new ArrayList<>();
					questCatList.add(candidateExamSummaryDescVo.getQuestionCategoryId());
					sectionIdAndQuestionCategoryIdList.put(candidateExamSummaryDescVo.getExamSectionId(), questCatList);
				} else if (!sectionIdAndQuestionCategoryIdList.get(candidateExamSummaryDescVo.getExamSectionId())
						.contains(candidateExamSummaryDescVo.getQuestionCategoryId())) {
					sectionIdAndQuestionCategoryIdList.get(candidateExamSummaryDescVo.getExamSectionId())
							.add(candidateExamSummaryDescVo.getQuestionCategoryId());
					if (sectionResultVo.getQuestionCategoryVoList()
							.containsKey(candidateExamSummaryDescVo.getQuestionCategoryId())) {
						sectionResultVo.getQuestionCategoryVoList()
								.get(candidateExamSummaryDescVo.getQuestionCategoryId())
								.setQuestionSubCategoryName(candidateExamSummaryDescVo.getQuestionCategoryName());
					} else {
						QuestionCategoryVo questionCategoryVo = new QuestionCategoryVo(
								candidateExamSummaryDescVo.getQuestionCategoryId());
						questionCategoryVo
								.setQuestionSubCategoryName(candidateExamSummaryDescVo.getQuestionCategoryName());
						sectionResultVo.getQuestionCategoryVoList()
								.put(candidateExamSummaryDescVo.getQuestionCategoryId(), questionCategoryVo);
					}
				}
			}
			count++;
		}
		candidateResultVo.calculatePercentile();
		return candidateResultVo;

	}

	private Map<Integer, SectionResultVo> mapSectionResult(List<CandidateExamSummary> candidateExamSummarieList) {
		Map<Integer, SectionResultVo> mapSectionResult = new HashMap<>();
		if (candidateExamSummarieList != null && candidateExamSummarieList.size() > 0) {
			for (CandidateExamSummary candidateExamSummary : candidateExamSummarieList) {
				if (!mapSectionResult.containsKey(candidateExamSummary.getExamSectionId())) {
					SectionResultVo sectionResultVo = new SectionResultVo(candidateExamSummary.getExamSectionId());
					QuestionCategoryVo questionCategoryVo = new QuestionCategoryVo(
							candidateExamSummary.getQuestionCategoryId());
					sectionResultVo.incrTotalAttemptQuestion();
					if (candidateExamSummary.getAnswerFlag()) {
						sectionResultVo.addUserTotalMarks(candidateExamSummary.getQuestionMarks());
						sectionResultVo.incrTotalCorrectAnswer();
					} else {
						sectionResultVo.subUserTotalMarks(candidateExamSummary.getQuestionNegativeMarks());
					}
					questionCategoryVo.setTotalCorrectAnswer(sectionResultVo.getTotalCorrectAnswer());
					questionCategoryVo.setTotalAttempt(sectionResultVo.getTotalAttemptQuestion());
					questionCategoryVo.setUserTotalMarks(sectionResultVo.getUserTotalMarks());

					sectionResultVo.getQuestionCategoryVoList().put(candidateExamSummary.getQuestionCategoryId(),
							questionCategoryVo);
					mapSectionResult.put(candidateExamSummary.getExamSectionId(), sectionResultVo);
				} else {
					SectionResultVo sectionResultVo = mapSectionResult.get(candidateExamSummary.getExamSectionId());
					QuestionCategoryVo questionCategoryVo = null;
					if (sectionResultVo.getQuestionCategoryVoList()
							.containsKey(candidateExamSummary.getQuestionCategoryId())) {
						questionCategoryVo = sectionResultVo.getQuestionCategoryVoList()
								.get(candidateExamSummary.getQuestionCategoryId());
					} else {
						questionCategoryVo = new QuestionCategoryVo(candidateExamSummary.getQuestionCategoryId());
						sectionResultVo.getQuestionCategoryVoList().put(candidateExamSummary.getQuestionCategoryId(),
								questionCategoryVo);
					}
					sectionResultVo.incrTotalAttemptQuestion();
					questionCategoryVo.incrTotalAttempt();
					if (candidateExamSummary.getAnswerFlag()) {
						sectionResultVo.addUserTotalMarks(candidateExamSummary.getQuestionMarks());
						questionCategoryVo.addUserTotalMarks(candidateExamSummary.getQuestionMarks());
						sectionResultVo.incrTotalCorrectAnswer();
						questionCategoryVo.incrTotalCorrectAnswer();
					} else {
						sectionResultVo.subUserTotalMarks(candidateExamSummary.getQuestionNegativeMarks());
						questionCategoryVo.subUserTotalMarks(candidateExamSummary.getQuestionNegativeMarks());
					}
				}
			}
		}
		return mapSectionResult;
	}

	@Override
	public List<EAIResultVo> getEAIResult(Integer testConductorHasTestCodeId, Integer candidateId) {
		CandidateResultVo candidateResultVo = getCertificate(testConductorHasTestCodeId, candidateId);
		return EAIResultVo.getEAIResult(candidateResultVo.getPercentile(), candidateResultVo.getBelowAverage());
	}

	@Override
	public Map<String, String> getSkillAssestment(Integer testConductorHasTestCodeId, Integer candidateId) {
		CandidateResultVo candidateResultVo = getCertificate(testConductorHasTestCodeId, candidateId);
		String summary = SkillAssessmentReport.getSkillAssesstmentSkill(candidateResultVo.getTechnicalLevel(),
				candidateResultVo.getQuantitativeLevel());
		Map<String, String> resultSummary = new HashMap<>();
		resultSummary.put("quantativeLevel", candidateResultVo.getQuantitativeLevel().name());
		resultSummary.put("technicalLevel", candidateResultVo.getTechnicalLevel().name());
		resultSummary.put("summary", summary);
		return resultSummary;
	}

	@Override
	public Map<String, Object> getResultsByExamId(Integer examId, Integer pageNo, Integer pageSize, Integer collegeId,
												  Integer specilizationId, Long startDate, Long endDate) {

		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_2_examId", examId);
		paramsKayAndValues.put("_1_collegeId", collegeId);
		paramsKayAndValues.put("_3_specializationId", specilizationId);
		paramsKayAndValues.put("_4_startDate", startDate != null ? new Date(startDate) : null);
		paramsKayAndValues.put("_5_endDate", startDate != null ? new Date(endDate) : null);

		List<Object[]> userTotalMarksArray = testConductorHasTestCodeDao
				.listCompositeSqlQuery(TestConductorHasTestCodeDao.findUserMarks, paramsKayAndValues);
		if (userTotalMarksArray == null || userTotalMarksArray.size() == 0) {
			return new HashMap<>();
		}

		List<UserMarksVo> userMarksVos = new ArrayList<>();
		if (userTotalMarksArray != null && userTotalMarksArray.size() > 0) {
			for (Object[] obj : userTotalMarksArray) {
				userMarksVos.add(new CustomGenericTypeImpl<UserMarksVo>(UserMarksVo.class).converter(obj, null));
			}
		}

		int rank = 1;
		Float highScore = userMarksVos.get(0).getUserTotalMark();
		int count = 0;
		int startIndex = (pageNo * pageSize) - pageSize;
		int endIndex = (pageNo * pageSize);
		Map<Integer, UserMarksVo> mapUserMarksVo = new HashMap<>();
		List<Integer> tchtcIds = new ArrayList<>();
		for (UserMarksVo userMarksVo : userMarksVos) {
			if (highScore.equals(userMarksVo.getUserTotalMark())) {
				userMarksVo.setRank(rank);
			} else {
				highScore = userMarksVo.getUserTotalMark();
				userMarksVo.setRank(++rank);
			}
			if (count >= startIndex && count < endIndex) {
				tchtcIds.add(userMarksVo.getTestConductorHasTestCodeId());
				mapUserMarksVo.put(userMarksVo.getTestConductorHasTestCodeId(), userMarksVo);
			}
			count++;
		}

		paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_2_examId", examId);
		paramsKayAndValues.put("_1_TCHTCId", tchtcIds);
		List<CandidateExamSummary> candidateExamSummarieList = candidateExamSummaryDao
				.listEntityByParameter(CandidateExamSummaryDao.findAllByExamIdAndTCIds, paramsKayAndValues, null, null);

		List<Object[]> examSummaryResultVoArray = candidateExamSummaryDao.listCompositeSqlQuery(
				CandidateExamSummaryDao.findExamResultDescByExamIDAndTCHTCIds, paramsKayAndValues);

		List<CandidateExamSummaryDescVo> candidateExamSummaryDescVos = new ArrayList<>();
		if (examSummaryResultVoArray != null && examSummaryResultVoArray.size() > 0) {
			for (Object[] obj : examSummaryResultVoArray) {
				candidateExamSummaryDescVos
						.add(new CustomGenericTypeImpl<CandidateExamSummaryDescVo>(CandidateExamSummaryDescVo.class)
								.converter(obj, null));
			}
		}

		if (candidateExamSummaryDescVos == null || candidateExamSummaryDescVos.isEmpty()
				|| candidateExamSummarieList == null || candidateExamSummarieList.isEmpty()) {
			return new HashMap<>();
		} else {
			Map<Integer, List<CandidateExamSummary>> mapCandExamSummList = candidateExamSummarieList.stream()
					.collect(Collectors.groupingBy(CandidateExamSummary::getTestConductorHasTestCodeId));
			Map<Integer, List<CandidateExamSummaryDescVo>> mapCandExamDescList = candidateExamSummaryDescVos.stream()
					.collect(Collectors.groupingBy(CandidateExamSummaryDescVo::getTestConductorHasTestCodeId));
			List<CandidateResultVo> candidateResultVos = new ArrayList<>();
			for (Integer testConductorHasTestCodeId : tchtcIds) {
				if (mapCandExamSummList.containsKey(testConductorHasTestCodeId)
						&& mapCandExamDescList.containsKey(testConductorHasTestCodeId)) {
					CandidateResultVo candidateResultVo = getCandidateResult(
							mapCandExamSummList.get(testConductorHasTestCodeId),
							mapCandExamDescList.get(testConductorHasTestCodeId));
					UserMarksVo userMarksVo = mapUserMarksVo.get(testConductorHasTestCodeId);
					candidateResultVo.setRank(userMarksVo.getRank());
					candidateResultVos.add(candidateResultVo);
				}
			}

			Map<String, Object> mapResults = new HashMap<>();
			mapResults.put("count", userTotalMarksArray.size());
			mapResults.put("examResult", candidateResultVos);
			return mapResults;
		}
	}

	// new API
	@Override
	public Map<String, Object> getResultListByExamId(Integer examId, Integer pageNo, Integer pageSize,
													 Integer collegeId, Integer specilizationId, Float percentile, Long startDate, Long endDate) {
		Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
		paramsKayAndValues.put("_1_examId", examId);
		paramsKayAndValues.put("_2_collegeId", collegeId);
		paramsKayAndValues.put("_3_specializationId", specilizationId);
		paramsKayAndValues.put("_4_startDate", startDate != null ? new Date(startDate) : null);
		paramsKayAndValues.put("_5_endDate", startDate != null ? new Date(endDate) : null);

		List<String> excludeList = new ArrayList<>();
		excludeList.add("examSectionName");
		excludeList.add("questionCategoryName");
		excludeList.add("firstName");
		excludeList.add("lastName");
		excludeList.add("contactEmail");
		excludeList.add("contactNumber");

		List<Object[]> examResultComposite = testConductorHasTestCodeDao
				.listCompositeSqlQuery(CandidateExamSummaryDao.findAllResultByExamId, paramsKayAndValues);
		if (examResultComposite == null || examResultComposite.size() == 0) {
			return new HashMap<>();
		}

		List<ExamResultCompositeVo> examResultCompositeVoList = new ArrayList<>();
		if (examResultComposite != null && examResultComposite.size() > 0) {
			for (Object[] obj : examResultComposite) {
				examResultCompositeVoList
						.add(new CustomGenericTypeImpl<ExamResultCompositeVo>(ExamResultCompositeVo.class)
								.converter(obj, excludeList));
			}
		}
		Map<Integer, List<ExamResultCompositeVo>> mapExamResultList = new HashMap<>();
		for (ExamResultCompositeVo examResultCompositeVo : examResultCompositeVoList) {
			if (mapExamResultList.containsKey(examResultCompositeVo.getTestConductorHasTestCodeId())) {
				mapExamResultList.get(examResultCompositeVo.getTestConductorHasTestCodeId()).add(examResultCompositeVo);
			} else {
				List<ExamResultCompositeVo> examResultCompositeVos = new ArrayList<>();
				examResultCompositeVos.add(examResultCompositeVo);
				mapExamResultList.put(examResultCompositeVo.getTestConductorHasTestCodeId(), examResultCompositeVos);
			}
		}

		if (examResultCompositeVoList == null || examResultCompositeVoList.size() == 0) {
			return new HashMap<>();
		}

		List<Candidate> candidateList = candidateDao.listEntity(CandidateDao.findAll);

		Map<Integer, Candidate> mapCandidate = new HashMap<>();
		for (Candidate candidate : candidateList) {
			mapCandidate.put(candidate.getUserId(), candidate);
		}

		List<QuestionCategory> questionCategorieList = questionCategoryDao.listEntity(QuestionCategoryDao.findAll);

		Map<Integer, QuestionCategory> mapQuestionCategory = new HashMap<>();
		for (QuestionCategory questionCategory : questionCategorieList) {
			mapQuestionCategory.put(questionCategory.getQuestionCategoryId(), questionCategory);
		}

		List<CandidateResultVo> candidateResultVoList = new ArrayList<>();

		for (Integer tchtcId : mapExamResultList.keySet()) {
			candidateResultVoList
					.add(transformCandidateResult(mapExamResultList.get(tchtcId), mapCandidate, mapQuestionCategory));
		}

		candidateResultVoList.sort(Comparator.comparing(CandidateResultVo::getUserTotalMarks));

		int rank = 0;
		for (CandidateResultVo candidateResultVo : candidateResultVoList) {
			++rank;
			LOGGER.debug("percentile:: "+candidateResultVo.getPercentile());
			LOGGER.debug("rank:: "+rank);
			candidateResultVo.setRank(rank);
		}

		int startIndex = (pageNo * pageSize) - pageSize;

		Map<String, Object> mapResults = new HashMap<>();
		if (percentile != null) {
			candidateResultVoList = candidateResultVoList.stream()
					.filter(candidateResultVo -> candidateResultVo.getPercentile() >= percentile)
					.collect(Collectors.toList());
		}
		mapResults.put("count", candidateResultVoList.size());
		mapResults.put("examResult",
				candidateResultVoList.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList()));
		return mapResults;
	}

	private CandidateResultVo transformCandidateResult(List<ExamResultCompositeVo> examResultCompositeVos,
													   Map<Integer, Candidate> mapCandidate, Map<Integer, QuestionCategory> mapQuestionCategory) {
		CandidateResultVo candidateResultVo = new CandidateResultVo();
		for (ExamResultCompositeVo examResultCompositeVo : examResultCompositeVos) {
			Candidate candidate = mapCandidate.get(examResultCompositeVo.getCandidateId());
			candidateResultVo.setCandidateFirstName(candidate.getFirstName());
			candidateResultVo.setCandidateLastName(candidate.getLastName());
			candidateResultVo.setContactEmail(candidate.getContactEmail());
			candidateResultVo.setContactNumber(candidate.getContactNumber());
			candidateResultVo.setCandidateId(candidate.getUserId());
			candidateResultVo.setExamId(examResultCompositeVo.getExamId());
			candidateResultVo.setExamName(examResultCompositeVo.getExamName());
			candidateResultVo.setTotalMarks(examResultCompositeVo.getTotalMarks());
			candidateResultVo.setTotalQuestion(examResultCompositeVo.getTotalQuestion());
			candidateResultVo.setExamCategoryName(examResultCompositeVo.getExamCategoryName());
			candidateResultVo.setExamDate(examResultCompositeVo.getExamStartDate() != null
					? examResultCompositeVo.getExamStartDate().getTime()
					: null);
			candidateResultVo.setCertificateCode(examResultCompositeVo.getTestCode());
			candidateResultVo.setSectionResultList(getSectionResultList(examResultCompositeVos, mapQuestionCategory));
			candidateResultVo.calculateCandidateResult();
			break;
		}

		return candidateResultVo;

	}

	private List<SectionResultVo> getSectionResultList(List<ExamResultCompositeVo> examResultCompositeVoList,
													   Map<Integer, QuestionCategory> mapQuestionCategory) {

		Map<Integer, SectionResultVo> mapSectionResult = new HashMap<>();
		if (examResultCompositeVoList != null && examResultCompositeVoList.size() > 0) {
			for (ExamResultCompositeVo examResultCompositeVo : examResultCompositeVoList) {
				QuestionCategory questionCategory = mapQuestionCategory
						.get(examResultCompositeVo.getQuestionCategoryId());
				SectionResultVo sectionResultVo = mapSectionResult.get(examResultCompositeVo.getExamSectionId());
				if (sectionResultVo == null) {
					sectionResultVo = new SectionResultVo(examResultCompositeVo.getExamSectionId());
					sectionResultVo.setExamSectionName(questionCategory.getParentQuestionCategoryName());
					sectionResultVo.setSectionName(examResultCompositeVo.getSectionName());
					sectionResultVo.setTotalMarks(examResultCompositeVo.getSectionTotalMarks());
					sectionResultVo.setTotalQuestion(examResultCompositeVo.getSectionTotalQuestion());

					QuestionCategoryVo questionCategoryVo = new QuestionCategoryVo();
					questionCategoryVo.setQuestionSubCategoryId(questionCategory.getQuestionCategoryId());
					questionCategoryVo.setQuestionSubCategoryName(questionCategory.getQuestionCategoryName());
					questionCategoryVo.setActive(true);
					questionCategoryVo.incrTotalAttempt();
					if (examResultCompositeVo.getAnswerFlag() != null && examResultCompositeVo.getAnswerFlag()) {
						questionCategoryVo.incrTotalCorrectAnswer();
						questionCategoryVo.addUserTotalMarks(examResultCompositeVo.getQuestionMarks());
					} else {
						questionCategoryVo.subUserTotalMarks(examResultCompositeVo.getQuestionNegativeMarks());
					}
					sectionResultVo.getQuestionCategoryVoList().put(questionCategory.getQuestionCategoryId(),
							questionCategoryVo);
					mapSectionResult.put(examResultCompositeVo.getExamSectionId(), sectionResultVo);
				} else {
					sectionResultVo = mapSectionResult.get(examResultCompositeVo.getExamSectionId());
					QuestionCategoryVo questionCategoryVo = sectionResultVo.getQuestionCategoryVoList()
							.get(examResultCompositeVo.getQuestionCategoryId());
					if (questionCategoryVo == null) {
						questionCategoryVo = new QuestionCategoryVo();
						questionCategoryVo.setQuestionSubCategoryId(questionCategory.getQuestionCategoryId());
						questionCategoryVo.setQuestionSubCategoryName(questionCategory.getQuestionCategoryName());
						questionCategoryVo.setActive(true);
						questionCategoryVo.incrTotalAttempt();
						if (examResultCompositeVo.getAnswerFlag() != null && examResultCompositeVo.getAnswerFlag()) {
							questionCategoryVo.incrTotalCorrectAnswer();
							questionCategoryVo.addUserTotalMarks(examResultCompositeVo.getQuestionMarks());
						} else {
							questionCategoryVo.subUserTotalMarks(examResultCompositeVo.getQuestionNegativeMarks());
						}
						sectionResultVo.getQuestionCategoryVoList().put(questionCategory.getQuestionCategoryId(),
								questionCategoryVo);
					} else {
						questionCategoryVo.incrTotalAttempt();
						if (examResultCompositeVo.getAnswerFlag() != null && examResultCompositeVo.getAnswerFlag()) {
							questionCategoryVo.incrTotalCorrectAnswer();
							questionCategoryVo.addUserTotalMarks(examResultCompositeVo.getQuestionMarks());
						} else {
							questionCategoryVo.subUserTotalMarks(examResultCompositeVo.getQuestionNegativeMarks());
						}
					}
				}
			}
		}
		List<SectionResultVo> sectionResultVos = new ArrayList<>();
		for(Integer examSectionId : mapSectionResult.keySet()) {
			SectionResultVo sectionResultVo = mapSectionResult.get(examSectionId);
			sectionResultVo.calculateSectionResult();
			sectionResultVos.add(sectionResultVo);
		}
		return sectionResultVos;
	}

}
