package com.icat.quest.common.vo;

public class SectionQuestionKeyMap {
	
	private Integer examSectionId;
	private Integer examSectionHasQuestionId;
	private Long	duration;
	
	public SectionQuestionKeyMap() {
		super();
	}

	

	public SectionQuestionKeyMap(Integer examSectionId, Integer examSectionHasQuestionId, Long duration) {
		super();
		this.examSectionId = examSectionId;
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.duration = duration;
	}



	public Long getDuration() {
		return duration;
	}



	public void setDuration(Long duration) {
		this.duration = duration;
	}



	public Integer getExamSectionId() {
		return examSectionId;
	}

	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}

	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}

	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}
	
	

}
