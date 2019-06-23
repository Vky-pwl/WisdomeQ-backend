package com.icat.quest.common.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamSectionVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSectionId;
	private ExamVo examVo;
	private String examSectionDescription;
	private String examSectionInstructions;
	private Integer sequence;
	private Long durationInSeconds;
	private Integer totalMarks;
	private Integer totalQuestion;
	private Boolean sicoFlag;
	private Boolean active;
	private QuestionCategoryVo questionCategoryVo;
	private List<CandidateQuestionStatusVo> randomMap;
	private byte[] examSectionDescriptionData;
	private byte[] examSectionInstructionsData;
	private String sectionName;
	
	public ExamSectionVo() {
	}

	public ExamSectionVo(Integer examSectionId, ExamVo examVo, String examSectionDescription,
			String examSectionInstructions, Integer sequence, Long durationInSeconds, Integer totalMarks,
			Integer totalQuestion, Boolean sicoFlag, Boolean active, QuestionCategoryVo questionCategoryVo,
			List<CandidateQuestionStatusVo> randomMap) {
		super();
		this.examSectionId = examSectionId;
		this.examVo = examVo;
		this.examSectionDescription = examSectionDescription;
		this.examSectionInstructions = examSectionInstructions;
		this.sequence = sequence;
		this.durationInSeconds = durationInSeconds;
		this.totalMarks = totalMarks;
		this.totalQuestion = totalQuestion;
		this.sicoFlag = sicoFlag;
		this.active = active;
		this.questionCategoryVo = questionCategoryVo;
		this.randomMap = randomMap;
	}

	public Integer getExamSectionId() {
		return examSectionId;
	}

	@JsonProperty("examSectionId")
	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}

	public ExamVo getExamVo() {
		return examVo;
	}

	@JsonProperty("examVo")
	public void setExamVo(ExamVo examVo) {
		this.examVo = examVo;
	}

	public String getExamSectionDescription() {
		return examSectionDescription;
	}

	@JsonProperty("examSectionDescription")
	public void setExamSectionDescription(String examSectionDescription) {
		this.examSectionDescription = examSectionDescription;
	}

	public Integer getSequence() {
		return sequence;
	}

	@JsonProperty("sequence")
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Long getDurationInSeconds() {
		return durationInSeconds;
	}

	@JsonProperty("durationInSeconds")
	public void setDurationInSeconds(Long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	@JsonProperty("totalMarks")
	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Integer getTotalQuestion() {
		return totalQuestion;
	}

	@JsonProperty("totalQuestion")
	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public Boolean getSicoFlag() {
		return sicoFlag;
	}

	@JsonProperty("sicoFlag")
	public void setSicoFlag(Boolean sicoFlag) {
		this.sicoFlag = sicoFlag;
	}

	public Boolean getActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	public QuestionCategoryVo getQuestionCategoryVo() {
		return questionCategoryVo;
	}

	public void setQuestionCategoryVo(QuestionCategoryVo questionCategoryVo) {
		this.questionCategoryVo = questionCategoryVo;
	}

	public List<CandidateQuestionStatusVo> getRandomMap() {
		return randomMap;
	}

	public void setRandomMap(List<CandidateQuestionStatusVo> randomMap) {
		this.randomMap = randomMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getExamSectionInstructions() {
		return examSectionInstructions;
	}

	public void setExamSectionInstructions(String examSectionInstructions) {
		this.examSectionInstructions = examSectionInstructions;
	}
	
	

	public byte[] getExamSectionDescriptionData() {
		return examSectionDescriptionData;
	}

	public void setExamSectionDescriptionData(byte[] examSectionDescriptionData) {
		this.examSectionDescriptionData = examSectionDescriptionData;
	}

	public byte[] getExamSectionInstructionsData() {
		return examSectionInstructionsData;
	}


	public void setExamSectionInstructionsData(byte[] examSectionInstructionsData) {
		this.examSectionInstructionsData = examSectionInstructionsData;
	}

	
	
	public String getSectionName() {
		return sectionName.trim();
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Override
	public String toString() {
		return "ExamSectionVo [examSectionId=" + examSectionId + ", examVo=" + examVo + ", sequence="
				+ sequence + ", durationInSeconds=" + durationInSeconds + ", totalMarks=" + totalMarks
				+ ", totalQuestion=" + totalQuestion + ", sicoFlag=" + sicoFlag + ", active=" + active
				+ ", questionCategoryVo=" + questionCategoryVo + ", randomMap=" + randomMap + "]";
	}
	
	

}
