/**
 * 
 */
package com.icat.quest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.vo.ExamSettingVo;
import com.icat.quest.dao.ExamSettingDao;
import com.icat.quest.dao.MasterSettingDao;
import com.icat.quest.model.ExamSetting;
import com.icat.quest.model.MasterSetting;
import com.icat.quest.service.ExamSettingService;


@Service
public class ExamSettingServiceImpl implements ExamSettingService {

	@Autowired
	private ExamSettingDao examSettingDao;
	@Autowired
	private MasterSettingDao	masterSettingDao;

	@Override
	public List<Integer> createBatchExamSetting(ExamSettingVo examSettingVo, Integer userId) throws Exception {
		if(examSettingVo.getSettingIdList() ==  null ||  examSettingVo.getSettingIdList().isEmpty() || examSettingVo.getExamId() == null) {
			throw new Exception("Invalid Input");
		}
		List<Integer> settingIdList = examSettingVo.getSettingIdList();
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		 paramsKayAndValues.put("_1_examId", examSettingVo.getExamId());
		 
		List<ExamSetting> examSettingList =  examSettingDao.listEntityByParameter(ExamSettingDao.findAllByExamId, paramsKayAndValues,null, null);
		if(examSettingList != null) {
			for(ExamSetting examSetting : examSettingList) {
			if(settingIdList.contains(examSetting.getMasterSetting().getSettingId())) {
				examSetting.setActive(true);
				examSetting.setLastModified(new Date());
				examSetting.setLastModifiedBy(new Long(userId));
				settingIdList.remove(examSetting.getMasterSetting().getSettingId());
			} else {
				examSetting.setActive(false);
				examSetting.setLastModified(new Date());
				examSetting.setLastModifiedBy(new Long(userId));		
			}
			}
			examSettingDao.updateBatch(examSettingList);
		}
		if(settingIdList.isEmpty()) {
			return examSettingVo.getSettingIdList();
		}
		List<ExamSetting> examSettings = new ArrayList<>();
		List<MasterSetting> masterSettingList = masterSettingDao.listEntityByIdList(settingIdList);
		for(MasterSetting masterSetting : masterSettingList) {
			ExamSetting examSetting = new ExamSetting(masterSetting, examSettingVo.getExamId(), true, new Date(), new Long(userId), new Date(), new Long(userId));
			examSettings.add(examSetting);
		}
		examSettingDao.createBatch(examSettings);
		return examSettingVo.getSettingIdList();
	}

		
	@Override
	public Map<String, Object> getListByExamId(Integer examId, Integer userId, int pageNo, int pageSize){
		
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		int startIndex = (pageNo*pageSize)-pageSize;
		Map<String, Object> responseBody = new HashMap<>();
		if(pageNo != 1) {
		List<ExamSetting> examSettings = examSettingDao.listEntityByParameter(ExamSettingDao.findAllActiveByExamId, paramsKayAndValues, startIndex, pageSize);
		responseBody.put("examSettingVoList", transformExamSettings(examSettings));
		
		} else {
			List<ExamSetting> examSettings = examSettingDao.listEntityByParameter(ExamSettingDao.findAllActiveByExamId, paramsKayAndValues, null, null);
			responseBody.put("count", examSettings.size());
			responseBody.put("examSettingVoList", transformExamSettings(examSettings.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList())));
		}
		return responseBody;
	}
	
	private List<ExamSettingVo> transformExamSettings(List<ExamSetting> examSettings){
		List<ExamSettingVo> examSettingVos = new ArrayList<>();
		examSettings.forEach(examSetting->{
			ExamSettingVo examSettingVo = new ExamSettingVo(examSetting.getExamSettingId(), examSetting.getMasterSetting().getSettingId(), examSetting.getMasterSetting().getSettingName(), examSetting.getExamId(), examSetting.getActive());
			examSettingVos.add(examSettingVo);
		});
		return examSettingVos;
	}
	
	@Override
	public List<ExamSettingVo> getListExamId(Integer examId){
		
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		List<ExamSetting> examSettings = examSettingDao.listEntityByParameter(ExamSettingDao.findAllActiveByExamId, paramsKayAndValues, null, null);
		
		return transformExamSettings(examSettings);
	}
	
}
