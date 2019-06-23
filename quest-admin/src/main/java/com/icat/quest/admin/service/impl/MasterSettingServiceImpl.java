/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.MasterSettingService;
import com.icat.quest.common.vo.MasterSettingVo;
import com.icat.quest.dao.MasterSettingDao;
import com.icat.quest.model.MasterSetting;


@Service
public class MasterSettingServiceImpl implements MasterSettingService {

	@Autowired
	private MasterSettingDao masterSettingDao;

	@Override
	public Integer createMasterSetting(MasterSettingVo masterSettingVo, Integer userId) {

		MasterSetting masterSetting = new MasterSetting(masterSettingVo.getSettingName(), true, new Date(), new Long(userId),
				new Date(), new Long(userId));
		Integer id = masterSettingDao.create(masterSetting);
		return id;
	}

	@Override
	public Integer updateMasterSetting(MasterSettingVo masterSettingVo, Integer userId) throws Exception {
		if (masterSettingVo.getSettingId() == null)
			throw new Exception("MasterSettingId should not be null");

		MasterSetting masterSetting = masterSettingDao.read(masterSettingVo.getSettingId());
		if (masterSetting == null)
			throw new Exception("Record not found");

		if (masterSettingVo.getSettingName() != null ) {
			masterSetting.setSettingName(masterSettingVo.getSettingName().trim());
		}
		if (masterSettingVo.getActive() != null) {
			masterSetting.setActive(masterSettingVo.getActive());
		}
		masterSetting.setLastModified(new Date());
		masterSetting.setLastModifiedBy(new Long(userId));
		masterSettingDao.update(masterSetting);
		return masterSettingVo.getSettingId();
	}

	@Override
	public MasterSettingVo readMasterSetting(Integer settingId, Integer userId) throws Exception {
		MasterSettingVo masterSettingVo = new MasterSettingVo();
		if (settingId == null)
			throw new Exception("MasterSettingId should not be null");

		MasterSetting masterSetting = masterSettingDao.read(settingId);
		if (masterSetting == null)
			throw new Exception("Record not found");

		masterSettingVo.setSettingId(masterSetting.getSettingId());
		masterSettingVo.setSettingName(masterSetting.getSettingName());
		masterSettingVo.setActive(masterSetting.getActive());

		return masterSettingVo;
	}

	@Override
	public Map<String, Object> listMasterSetting(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<MasterSetting> masterSettingList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;
		if (searchKey != null && active != null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_masterSettingName", searchKey);
			parameters.put("_1_active", active);
			masterSettingList = masterSettingDao.listEntityByParameter(MasterSettingDao.findAllBySearchKeyWithFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			masterSettingList = masterSettingDao.listEntityByParameter(MasterSettingDao.findAllByFilter, parameters, startIndex,
					pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_masterSettingName", searchKey);
			masterSettingList = masterSettingDao.listEntityByParameter(MasterSettingDao.findAllBySearchKey, parameters,
					startIndex, pageSize);
		} else {
			masterSettingList = masterSettingDao.listEntityByParameter(MasterSettingDao.findAll, parameters, startIndex,
					pageSize);
		}
		Map<String, Object> masterSettingResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			masterSettingResultSet.put("count", masterSettingList.size());
			masterSettingResultSet.put("masterSettingVoList",
					transformMasterSettingVoList(masterSettingList.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList())));
		} else {
			masterSettingResultSet.put("masterSettingVoList", transformMasterSettingVoList(masterSettingList));
		}

		return masterSettingResultSet;
	}

	private List<MasterSettingVo> transformMasterSettingVoList(List<MasterSetting> masterSettingList) {
		List<MasterSettingVo> masterSettingVoList = new ArrayList<MasterSettingVo>();
		if (masterSettingList != null) {
			masterSettingList.forEach(masterSetting -> {
				MasterSettingVo masterSettingVo = new MasterSettingVo();
				masterSettingVo.setSettingId(masterSetting.getSettingId());
				masterSettingVo.setSettingName(masterSetting.getSettingName());
				masterSettingVo.setActive(masterSetting.getActive());
				masterSettingVoList.add(masterSettingVo);
			});
		}
		return masterSettingVoList;
	}

}
