package com.icat.quest.common.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examId;
	private String examName;
	private String examDescription;
	private Long startDate;
	private Long endDate;
	private Integer sectionCount;
	private Integer questionCount;
	private Long durationInSeconds;
	private Integer totalMarks;
	private String testCode;
	private Boolean status;
	private ExamCategoryVo examCategoryVo;
	private Boolean allowReattempts;
	private Integer passingPercentage;
	private Boolean negativeMarking;
	private Boolean active;
	private List<ExamSectionVo> examSectionVoList;
	private String tinyKey;
	private ExamStatus examStatus;
	private Long remainingTime;
	private String examInstructions;
	private Long sectionRemainingTime;
	private Integer currentSectionId;
	private List<PermissionVo> permissionVoList;
	private Boolean publish;
	private byte[] examDescriptionData;
	private byte[] examInstructionsData;
	private ExamSettingsVo examSettingsVo;
	private Integer testConductorHasTestCodeId;
	
	public ExamVo() {
	}

	public ExamVo(Integer examId, String examName, String examDescription, Long startDate, Long endDate,
			Integer sectionCount, Integer questionCount, Long durationInSeconds, Integer totalMarks, String testCode,
			Boolean status, ExamCategoryVo examCategoryVo, Boolean allowReattempts, Integer passingPercentage,
			Boolean negativeMarking, Boolean active, List<ExamSectionVo> examSectionVoList, String tinyKey,
			ExamStatus examStatus, Long remainingTime, String examInstructions, Long sectionRemainingTime,
			Integer currentSectionId) {
		super();
		this.examId = examId;
		this.examName = examName;
		this.examDescription = examDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sectionCount = sectionCount;
		this.questionCount = questionCount;
		this.durationInSeconds = durationInSeconds;
		this.totalMarks = totalMarks;
		this.testCode = testCode;
		this.status = status;
		this.examCategoryVo = examCategoryVo;
		this.allowReattempts = allowReattempts;
		this.passingPercentage = passingPercentage;
		this.negativeMarking = negativeMarking;
		this.active = active;
		this.examSectionVoList = examSectionVoList;
		this.tinyKey = tinyKey;
		this.examStatus = examStatus;
		this.remainingTime = remainingTime;
		this.examInstructions = examInstructions;
		this.sectionRemainingTime = sectionRemainingTime;
		this.currentSectionId = currentSectionId;
	}

	public Integer getExamId() {
		return examId;
	}

	@JsonProperty("examId")
	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	@JsonProperty("examName")
	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getExamDescription() {
		return examDescription;
	}

	@JsonProperty("examDescription")
	public void setExamDescription(String examDescription) {
		this.examDescription = examDescription;
	}

	public Long getStartDate() {
		return startDate;
	}

	@JsonProperty("startDate")
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	@JsonProperty("endDate")
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Integer getSectionCount() {
		return sectionCount;
	}

	@JsonProperty("sectionCount")
	public void setSectionCount(Integer sectionCount) {
		this.sectionCount = sectionCount;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	@JsonProperty("questionCount")
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
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

	public String getTestCode() {
		return testCode;
	}

	@JsonProperty("testCode")
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public Boolean getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public ExamCategoryVo getExamCategoryVo() {
		return examCategoryVo;
	}

	@JsonProperty("examCategoryVo")
	public void setExamCategoryVo(ExamCategoryVo examCategoryVo) {
		this.examCategoryVo = examCategoryVo;
	}

	public Boolean getAllowReattempts() {
		return allowReattempts;
	}

	@JsonProperty("allowReattempts")
	public void setAllowReattempts(Boolean allowReattempts) {
		this.allowReattempts = allowReattempts;
	}

	public Integer getPassingPercentage() {
		return passingPercentage;
	}

	@JsonProperty("passingPercentage")
	public void setPassingPercentage(Integer passingPercentage) {
		this.passingPercentage = passingPercentage;
	}

	public Boolean getNegativeMarking() {
		return negativeMarking;
	}

	@JsonProperty("negativeMarking")
	public void setNegativeMarking(Boolean negativeMarking) {
		this.negativeMarking = negativeMarking;
	}

	public Boolean getActive() {
		return active;
	}

	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ExamSectionVo> getExamSectionVoList() {
		return examSectionVoList;
	}

	public void setExamSectionVoList(List<ExamSectionVo> examSectionVoList) {
		this.examSectionVoList = examSectionVoList;
	}

	public String getTinyKey() {
		return tinyKey;
	}

	@JsonProperty("tinyKey")
	public void setTinyKey(String tinyKey) {
		this.tinyKey = tinyKey;
	}

	public ExamStatus getExamStatus() {
		return examStatus;
	}

	@JsonProperty("examStatus")
	public void setExamStatus(ExamStatus examStatus) {
		this.examStatus = examStatus;
	}

	public Long getRemainingTime() {
		return remainingTime;
	}

	@JsonProperty("remainingTime")
	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getExamInstructions() {
		return examInstructions;
	}

	@JsonProperty("examInstructions")
	public void setExamInstructions(String examInstructions) {
		this.examInstructions = examInstructions;
	}

	public Long getSectionRemainingTime() {
		return sectionRemainingTime;
	}

	@JsonProperty("sectionRemainingTime")
	public void setSectionRemainingTime(Long sectionRemainingTime) {
		this.sectionRemainingTime = sectionRemainingTime;
	}

	public Integer getCurrentSectionId() {
		return currentSectionId;
	}

	@JsonProperty("currentSectionId")
	public void setCurrentSectionId(Integer currentSectionId) {
		this.currentSectionId = currentSectionId;
	}

	public List<PermissionVo> getPermissionVoList() {
		return permissionVoList;
	}

	public void setPermissionVoList(List<PermissionVo> permissionVoList) {
		this.permissionVoList = permissionVoList;
	}

	public Boolean getPublish() {
		return publish;
	}

	@JsonProperty("publish")
	public void setPublish(Boolean publish) {
		this.publish = publish;
	}
	
	
	public byte[] getExamDescriptionData() {
		return examDescriptionData;
	}
	
	public void setExamDescriptionData(byte[] examDescriptionData) {
		this.examDescriptionData = examDescriptionData;
	}

	public byte[] getExamInstructionsData() {
		return examInstructionsData;
	}
	
	
	public void setExamInstructionsData(byte[] examInstructionsData) {
		this.examInstructionsData = examInstructionsData;
	}
	
	

	public ExamSettingsVo getExamSettingsVo() {
		return examSettingsVo;
	}

	@JsonProperty("examSettingsVo")
	public void setExamSettingsVo(ExamSettingsVo examSettingsVo) {
		this.examSettingsVo = examSettingsVo;
	}

	
	
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}

	@Override
	public String toString() {
		return "ExamVo [examId=" + examId + ", examName=" + examName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", sectionCount=" + sectionCount
				+ ", questionCount=" + questionCount + ", durationInSeconds=" + durationInSeconds + ", totalMarks="
				+ totalMarks + ", testCode=" + testCode + ", status=" + status + ", examCategoryVo=" + examCategoryVo
				+ ", allowReattempts=" + allowReattempts + ", passingPercentage=" + passingPercentage
				+ ", negativeMarking=" + negativeMarking + ", active=" + active + ", examSectionVoList="
				+ examSectionVoList + ", tinyKey=" + tinyKey + ", examStatus=" + examStatus + ", remainingTime="
				+ remainingTime + ", sectionRemainingTime="
				+ sectionRemainingTime + ", currentSectionId=" + currentSectionId + ", permissionVoList="
				+ permissionVoList + ", publish=" + publish + "]";
	}

	
}
