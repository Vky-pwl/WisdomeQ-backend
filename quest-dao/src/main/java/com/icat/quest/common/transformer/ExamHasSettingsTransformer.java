package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.ExamSettingsVo;
import com.icat.quest.model.ExamHasSettings;

public class ExamHasSettingsTransformer implements Transformer<ExamHasSettings, ExamSettingsVo> {

	@Override
	public ExamSettingsVo transform(ExamHasSettings examHasSettings) {
		if (examHasSettings == null) {
			return null;
		}
		ExamSettingsVo examSettingsVo = new ExamSettingsVo();
		examSettingsVo.setExamSettingId(examHasSettings.getExamSettingId());
		examSettingsVo.setAllowBackButton(examHasSettings.getAllowBackButton());
		examSettingsVo.setAllowExamResume(examHasSettings.getAllowExamResume());
		examSettingsVo.setAllowExamResumeCount(examHasSettings.getAllowExamResumeCount());
		examSettingsVo.setReattemptCount(examHasSettings.getReattemptCount());
		examSettingsVo.setAllowReattempts(examHasSettings.getAllowReattempts());
		examSettingsVo.setPrivacy(examHasSettings.getPrivacy());
		examSettingsVo.setActive(examSettingsVo.getActive());
		examSettingsVo.setDisplayQuestion(examHasSettings.getDisplayQuestion().name());
		examSettingsVo.setDisplayQuestionDesc(examHasSettings.getDisplayQuestion().getDescription());
		examSettingsVo.setReattemptCount(examSettingsVo.getReattemptCount());
		return examSettingsVo;
	}

}
