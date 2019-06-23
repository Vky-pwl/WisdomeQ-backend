package com.icat.quest.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.CandidateResultVo;
import com.icat.quest.common.vo.EAIResultVo;

public interface ExamResultService {

	CandidateResultVo getCertificate(Integer examId, Integer candidateId);

	Map<String, Object> getExamTotalAttemps(Integer pageNo, Integer pageSize, Integer collegeId,
			Integer specilizationId);

	Map<String, Object> getCandRankExamList(int pageSize, int pageNo, Integer collegeId, Integer candidateId,
			Integer specilizationId, Float percentile);

	List<EAIResultVo> getEAIResult( Integer testConductorHasTestCodeId, Integer candidateId);

	Map<String, Object> getResultsByExamId(Integer examId, Integer pageNo, Integer pageSize, Integer collegeId,
			Integer specilizationId, Float percentile);

	Map<String, Object> getResultsByExamId(Integer examId, Integer pageNo, Integer pageSize, Integer collegeId,
			Integer specilizationId, Long startDate, Long endDate);

	Map<String, String> getSkillAssestment(Integer testConductorHasTestCodeId, Integer candidateId);

	Map<String, Object> getResultListByExamId(Integer examId, Integer pageNo, Integer pageSize, Integer collegeId,
			Integer specilizationId, Float percentile, Long startDate, Long endDate);

}
