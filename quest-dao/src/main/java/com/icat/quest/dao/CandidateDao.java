
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.Candidate;


public interface CandidateDao extends GenericDao<Candidate,Integer>{

	public String findUserLogin="from com.icat.quest.model.Candidate user where user.active is true and (user.contactEmail=:_1_userName or user.contactNumber=:_1_userName)";
	
	public String userIdListByCriteria = "SELECT distinct userId FROM User user where\n" + 
			"CASE WHEN :_1_active is null THEN 1=1 ELSE user.active=:_1_active END \n" + 
			"and \n" + 
			"CASE WHEN :_2_userName is null THEN 1=1 ELSE (user.firstName like :_2_userName or user.middleName like :_2_userName or user.lastName like :_2_userName) END \n" + 
			"AND \n" + 
			"CASE WHEN :_3_collegeId is null THEN 1=1 ELSE user.collegeId=:_3_collegeId END\n" + 
			"AND\n" + 
			"CASE WHEN :_4_testConductorLicenseId is null THEN 1=1 ELSE user.userId not in(select distinct TCHTC.userId from TestConductorHasTestCode TCHTC where TCHTC.testConductorLicenseId= :_4_testConductorLicenseId and TCHTC.active is true) END\n" + 
			"AND\n" + 
			"CASE WHEN :_5_testConductorId is null THEN 1=1 ELSE user.collegeId =(select distinct TC.collegeId from TestConductor TC where TC.testConductorId= :_5_testConductorId and TC.active is true) END\n" + 
			"order by user.userId desc";
	
	public String findByEmail = "select userId from User where contactEmail=:_1_contactEmail";
	public String findByNumber = "select userId from User where contactNumber=:_1_contactNumber";

	public String findAll="from com.icat.quest.model.Candidate user";
	
	
}
