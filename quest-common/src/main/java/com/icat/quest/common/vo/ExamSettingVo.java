package com.icat.quest.common.vo;

import java.util.List;

public class ExamSettingVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSettingId;
	private Integer settingId;
	private String settingName;
	private Integer examId;
	private Boolean active;
	private List<Integer> settingIdList;
	
	
	
	public ExamSettingVo() {
		super();
	}



	public ExamSettingVo(Integer examSettingId, Integer settingId, String settingName, Integer examId, Boolean active) {
		super();
		this.examSettingId = examSettingId;
		this.settingId = settingId;
		this.settingName = settingName;
		this.examId = examId;
		this.active = active;
	}



	public Integer getExamSettingId() {
		return examSettingId;
	}



	public void setExamSettingId(Integer examSettingId) {
		this.examSettingId = examSettingId;
	}



	public Integer getSettingId() {
		return settingId;
	}



	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}



	public Integer getExamId() {
		return examId;
	}



	public void setExamId(Integer examId) {
		this.examId = examId;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public List<Integer> getSettingIdList() {
		return settingIdList;
	}



	public void setSettingIdList(List<Integer> settingIdList) {
		this.settingIdList = settingIdList;
	}
	
	



	public String getSettingName() {
		return settingName;
	}



	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}



	@Override
	public String toString() {
		return "ExamSettingVo [examSettingId=" + examSettingId + ", settingId=" + settingId + ", examId=" + examId
				+ ", active=" + active + ", settingIdList=" + settingIdList + "]";
	}
	
	
}
