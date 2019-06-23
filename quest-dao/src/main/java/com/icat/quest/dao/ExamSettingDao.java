/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExamSetting;


public interface ExamSettingDao extends GenericDao<ExamSetting,Integer>{

	public String findAllByExamId = "from com.icat.quest.model.ExamSetting examSetting where examSetting.examId=:_1_examId order by examSettingId desc ";
	public String findAllActiveByExamId = "from com.icat.quest.model.ExamSetting examSetting where examSetting.examId=:_1_examId and examSetting.active = true  order by examSettingId desc ";
	public String findAllByExamIdAndSettingId = "from com.icat.quest.model.ExamSetting examSetting where examSetting.examId=:_1_examId and examSetting.settingId=:_2_settingId";
	
}
