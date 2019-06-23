package com.icat.quest.admin.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.TestConductorLicenseVo;
import com.icat.quest.common.vo.UserType;

public interface TestConductorLicenseService {


	Integer updateTestConductorLicense(TestConductorLicenseVo testConductorLicenseVo,Integer userId, Boolean flag) throws Exception;

	TestConductorLicenseVo readTestConductorLicense(Integer testConductorLicenseId,Integer userId) throws Exception;

	Map<String,Object> listTestConductorLicense(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId) throws Exception;

	Integer assignAdminLicense(TestConductorLicenseVo testConductorLicenseVo,Integer userId) throws Exception;

	Integer assignTestConductorLicense(TestConductorLicenseVo testConductorLicenseVo,Integer userId) throws Exception;

	Integer assignExternalLicense(Integer candidateId, Integer examId, Integer userId);

	Map<String, Object> getExternalLicenseList(Integer candidateId, Integer userId, Integer pageSize, Integer pageNo);

	
	Map<String, Object> listBytestConductorId(Integer testConductorId, Integer pageNo, Integer pageSize, Integer userId,
			UserType userType);

	List<TestConductorLicenseVo> listByExamId(Integer testConductorId, Integer examId);

}
