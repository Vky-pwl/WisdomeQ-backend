package com.icat.quest.user.service;

import java.util.Map;

import com.icat.quest.common.vo.CandidateVo;

public interface CandidateCompositeService {

	Map<String, Object> signupCandidateComposite(CandidateVo candidateVo) throws Exception;

	Map<String, Object> attachExam(String testConductorLicenseCode, Integer userId) throws Exception;

}
