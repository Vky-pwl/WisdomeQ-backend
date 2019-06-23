package com.icat.quest.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.ExamStatusVo;
import com.icat.quest.common.vo.TestConductorHasTestCodeVo;

public interface TestConductorHasTestCodeService {

	Integer updateTestConductorHasTestCode(TestConductorHasTestCodeVo testConductorHasTestCodeVo,Integer userId,Boolean flag) throws Exception;

	TestConductorHasTestCodeVo readTestConductorHasTestCode(Integer testConductorHasTestCodeId,Integer userId) throws Exception;

	Map<String,Object> getTestCodeByTestConductorLicenseId(Integer testConductorLicenseId,Integer userId,Boolean flag, Integer pageNo, Integer pageSize) throws Exception;

	void deactiveTestConductorHasTestCode(List<Integer> testConductorHasTestCodeIdList,Integer userId,Boolean flag);

	Map<String, Object> getResults(Integer examId, Integer userId, Boolean flag,
			Integer pageNo, Integer pageSize) throws Exception;

	ExamStatusVo getExamStatus(Integer examId);

	void assignedUserTestCode(List<Integer> userIdList, Integer testConductorLicenseId, Integer userId, Boolean flag,
			String tinyKey) throws Exception;

	void updateTestCodes();

}
