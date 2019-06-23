package com.icat.quest.common.vo;

import java.io.Serializable;

public class ExamCategoryVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examCategoryId;
	private String examCategoryName;
	private Integer examSubCategoryId;
	private String examSubCategoryName;
	private Boolean active;
	public ExamCategoryVo() {
		super();
	}
	
	
	

	public ExamCategoryVo(Integer examCategoryId, String examCategoryName, Integer examSubCategoryId,
			String examSubCategoryName, Boolean active) {
		super();
		this.examCategoryId = examCategoryId;
		this.examCategoryName = examCategoryName;
		this.examSubCategoryId = examSubCategoryId;
		this.examSubCategoryName = examSubCategoryName;
		this.active = active;
	}




	public Integer getExamCategoryId() {
		return examCategoryId;
	}




	public void setExamCategoryId(Integer examCategoryId) {
		this.examCategoryId = examCategoryId;
	}




	public String getExamCategoryName() {
		return examCategoryName;
	}




	public void setExamCategoryName(String examCategoryName) {
		this.examCategoryName = examCategoryName;
	}




	public Integer getExamSubCategoryId() {
		return examSubCategoryId;
	}




	public void setExamSubCategoryId(Integer examSubCategoryId) {
		this.examSubCategoryId = examSubCategoryId;
	}




	public String getExamSubCategoryName() {
		return examSubCategoryName;
	}




	public void setExamSubCategoryName(String examSubCategoryName) {
		this.examSubCategoryName = examSubCategoryName;
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




	@Override
	public String toString() {
		return "ExamCategoryVo [examCategoryId=" + examCategoryId + ", examCategoryName=" + examCategoryName
				+ ", examSubCategoryId=" + examSubCategoryId + ", examSubCategoryName=" + examSubCategoryName
				+ ", active=" + active + "]";
	}
	
}
