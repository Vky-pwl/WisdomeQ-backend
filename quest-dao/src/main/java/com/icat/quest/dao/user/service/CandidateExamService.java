package com.icat.quest.dao.user.service;

import java.util.Map;

import com.icat.quest.common.vo.CandidateExamVo;
import com.icat.quest.common.vo.ExamMetadatDashboardVo;
import com.icat.quest.common.vo.UserType;

public interface CandidateExamService {

	Long createCandidateExam(CandidateExamVo candidateExamVo) throws Exception;

	Map<String, Object> getExamListByCandidateId(Integer userId, Integer pageNo, Integer pageSize, Boolean attended, UserType userType);

	ExamMetadatDashboardVo getExamMetadatDashboardVo(Integer candidateId);

}
