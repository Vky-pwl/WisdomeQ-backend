package com.icat.quest.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.ExamSettingVo;

public interface ExamSettingService {

	
	Map<String, Object> getListByExamId(Integer examId, Integer userId, int pageNo, int pageSize);

	List<Integer> createBatchExamSetting(ExamSettingVo examSettingVo, Integer userId) throws Exception;

	List<ExamSettingVo> getListExamId(Integer examId);

}
