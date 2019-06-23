/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.TestConductorLicense;


public interface TestConductorLicenseDao extends GenericDao<TestConductorLicense,Integer>{

	
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.TestConductorLicense testConductorLicense where "
			+ "testConductorLicense.active =:_1_active and testConductorLicense.testConductorLicenseName like :_2_testConductorLicenseName order by testConductorLicense.testConductorLicenseId desc";
	public String findAllByFilter="from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.active =:_1_active order by testConductorLicense.testConductorLicenseId desc";
	public String findAllBySearchKey="from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.testConductorLicenseName like :_2_testConductorLicenseName order by testConductorLicense.testConductorLicenseId desc";
	public String findAll = "from com.icat.quest.model.TestConductorLicense testConductorLicense order by testConductorLicense.testConductorLicenseId desc";
	public String findAllByTestConductorId = "from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.testConductor.testConductorId=:_1_testConductorId and testConductorLicense.active = true order by testConductorLicense.testConductorLicenseId desc";
	public String findRemainingLicenseByTestConductorId = "from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.exam.examId=:_1_examId and testConductorLicense.testConductor.testConductorId=:_2_testConductorId and testConductorLicense.active =true";
	public String findByTCIdAndExamId = "select distinct testConductorId from  TestConductorLicense where examId=:_1_examId and testConductorId=:_2_testConductorId and active =true";
	public String findAllByExamId = "from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.testConductor.parentTestConductorId=:_1_testConductorId and testConductorLicense.exam.examId = :_2_examId and testConductorLicense.active = true order by testConductorLicense.testConductorLicenseId desc";
	
	public String findAllSALicenseByExamId = "from com.icat.quest.model.TestConductorLicense testConductorLicense where testConductorLicense.testConductor.adminType='SUPERADMIN' and testConductorLicense.exam.examId = :_1_examId and testConductorLicense.active = true";
}
