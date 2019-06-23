
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.PublishExamLicense;


public interface PublishExamLicenseDao extends GenericDao<PublishExamLicense,Integer>{

	public String findByTCLCode="from com.icat.quest.model.PublishExamLicense publishExamLicense where publishExamLicense.active is true and publishExamLicense.testConductorLicenseCode=:_1_code";
	
	public String findByTCLId="from com.icat.quest.model.PublishExamLicense publishExamLicense where publishExamLicense.active is true and publishExamLicense.testConductorLicense.testConductorLicenseId=:_1_TCLId and active is true";
	
	
}
