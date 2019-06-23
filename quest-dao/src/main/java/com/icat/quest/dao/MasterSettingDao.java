/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.MasterSetting;


public interface MasterSettingDao extends GenericDao<MasterSetting,Integer>{

	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.MasterSetting masterSetting where "
			+ "masterSetting.active =:_1_active and masterSetting.settingName like :_2_masterSettingName order by masterSetting.settingId desc";
	public String findAllByFilter="from com.icat.quest.model.MasterSetting masterSetting where masterSetting.active =:_1_active order by masterSetting.settingId desc";
	public String findAllBySearchKey="from com.icat.quest.model.MasterSetting masterSetting where masterSetting.settingName like :_2_masterSettingName order by masterSetting.settingId desc";
	public String findAll = "from com.icat.quest.model.MasterSetting masterSetting order by masterSetting.settingId desc";
	
}
