package com.icat.quest.service;

import java.util.List;

import com.icat.quest.common.vo.ExamSettingsVo;
import com.icat.quest.model.ExamHasSettings;

public interface ExamHasSettingsService {

	Integer createExamSetting(ExamSettingsVo examSettingsVo, Integer loggedUserId);

	void updateExamSetting(ExamSettingsVo examSettingsVo, Integer loggedUserId);

	List<ExamSettingsVo> listExamSettings();

	ExamHasSettings createSetting(ExamSettingsVo examSettingsVo, Integer loggedUserId);

}
