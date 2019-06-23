/**
 * 
 */
package com.icat.quest.admin.service;

import java.util.Map;

import com.icat.quest.common.vo.MasterSettingVo;


public interface MasterSettingService {

	
	Integer updateMasterSetting(MasterSettingVo masterSettingVo,Integer userId) throws Exception;

	MasterSettingVo readMasterSetting(Integer masterSettingId,Integer userId) throws Exception;

	Map<String,Object> listMasterSetting(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId) throws Exception;

	Integer createMasterSetting(MasterSettingVo masterSettingVo,Integer userId) throws Exception;

	
}
