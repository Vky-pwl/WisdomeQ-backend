package com.icat.quest.common.vo;

public class ExamSettingsVo {
	
	private Integer examSettingId;
	private Boolean allowReattempts = false;
	private Integer reattemptCount = 0;
	private Boolean allowExamResume = false;
	private Integer allowExamResumeCount = 0;
	private String displayQuestion;
	private String displayQuestionDesc;
	private Boolean allowBackButton = false;
	private Boolean privacy = false;
	private Boolean active = true;
	private Integer examId;
	
	public ExamSettingsVo() {
		super();
	}

	public Integer getExamSettingId() {
		return examSettingId;
	}

	public void setExamSettingId(Integer examSettingId) {
		this.examSettingId = examSettingId;
	}

	public Boolean getAllowReattempts() {
		return allowReattempts;
	}

	public void setAllowReattempts(Boolean allowReattempts) {
		this.allowReattempts = allowReattempts;
	}

	public Integer getReattemptCount() {
		return reattemptCount;
	}

	public void setReattemptCount(Integer reattemptCount) {
		this.reattemptCount = reattemptCount;
	}

	public Boolean getAllowExamResume() {
		return allowExamResume;
	}

	public void setAllowExamResume(Boolean allowExamResume) {
		this.allowExamResume = allowExamResume;
	}

	public Integer getAllowExamResumeCount() {
		return allowExamResumeCount;
	}

	public void setAllowExamResumeCount(Integer allowExamResumeCount) {
		this.allowExamResumeCount = allowExamResumeCount;
	}

	public String getDisplayQuestion() {
		return displayQuestion;
	}

	public void setDisplayQuestion(String displayQuestion) {
		this.displayQuestion = displayQuestion;
	}


	public String getDisplayQuestionDesc() {
		return displayQuestionDesc;
	}

	public void setDisplayQuestionDesc(String displayQuestionDesc) {
		this.displayQuestionDesc = displayQuestionDesc;
	}

	public Boolean getAllowBackButton() {
		return allowBackButton;
	}

	public void setAllowBackButton(Boolean allowBackButton) {
		this.allowBackButton = allowBackButton;
	}

	public Boolean getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Boolean privacy) {
		this.privacy = privacy;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	

}
