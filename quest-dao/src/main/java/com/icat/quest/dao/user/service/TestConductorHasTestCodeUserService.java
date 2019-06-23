package com.icat.quest.dao.user.service;

import com.icat.quest.common.vo.TestConductorHasTestCodeVo;

public interface TestConductorHasTestCodeUserService {

	TestConductorHasTestCodeVo getResultByExamId(Integer examId, Integer userId) throws Exception;

	void submitExam(Integer tchtcId, Integer userId);

}
