/**
 * 
 */
package com.icat.quest.admin.service;

import java.util.Map;

import com.icat.quest.common.vo.TestConductorVo;

public interface TestConductorService {

	
	Integer updateTestConductor(TestConductorVo testConductorVo,Integer userId) throws Exception;

	TestConductorVo readTestConductor(Integer testConductorId,Integer userId) throws Exception;

	Map<String,Object> listTestConductor(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId,String adminType) throws Exception;

	Integer craeteTestConductor(TestConductorVo testConductorVo,Integer userId) throws Exception;

	Map<String, Object> listExaminerByAdminId(Integer pageNo, Integer pageSize, Boolean active, Integer userId,
			String adminType);

	
}
