/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.TestConductor;

public interface TestConductorDao extends GenericDao<TestConductor,Integer>{

	
public String findTestConductorLogin="from com.icat.quest.model.TestConductor testConductor where testConductor.active is true and testConductor.contactEmail=:_1_testConductorName";
	
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.TestConductor testConductor where "
			+ "testConductor.active = :_1_active and (testConductor.firstName like :_2_testConductorName or testConductor.lastName like :_2_testConductorName) and testConductor.adminType=:_3_adminType  order by testConductor.testConductorId desc";
	
	public String findAllByFilter="from com.icat.quest.model.TestConductor testConductor where "
			+ "testConductor.active = :_1_active  and testConductor.adminType=:_3_adminType order by testConductor.testConductorId desc";
	
	public String findAllBySearchKey="from com.icat.quest.model.TestConductor testConductor where "
			+ "(testConductor.firstName like :_2_testConductorName or testConductor.lastName like :_2_testConductorName) and testConductor.adminType=:_3_adminType order by testConductor.testConductorId desc";
	public String findAll = "from com.icat.quest.model.TestConductor testConductor where testConductor.adminType=:_3_adminType order by testConductor.testConductorId desc";
	
	public String findAllTestConductorByParentId="from com.icat.quest.model.TestConductor testConductor where testConductor.parentTestConductorId=:_1_parentTcId";
	public String findAllTestConductorByParentIdWithFilter="from com.icat.quest.model.TestConductor testConductor where testConductor.parentTestConductorId=:_1_parentTcId and testConductor.active=:_2_active";
	
	public String findSuperAdmin = "from com.icat.quest.model.TestConductor testConductor where testConductor.adminType='SUPERADMIN' and testConductor.active=:_2_active";
	
}
