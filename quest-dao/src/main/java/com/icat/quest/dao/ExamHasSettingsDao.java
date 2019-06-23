
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExamHasSettings;


public interface ExamHasSettingsDao extends GenericDao<ExamHasSettings,Integer>{
	
	public String findAll = "from com.icat.quest.model.ExamHasSettings examHasSettings where examHasSettings.active = true";

}
