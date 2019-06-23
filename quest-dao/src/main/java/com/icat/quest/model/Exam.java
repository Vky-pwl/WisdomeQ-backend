package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "Exam")
public class Exam implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examId;
	private ExamHasSettings examHasSettings;
	private String examName;
	private byte[] examDescription;
	private Date startDate;
	private Date endDate;
	private Integer sectionCount;
	private Integer questionCount;
	private Long durationInSeconds;
	private Integer totalMarks;
	private String testCode;
	private Boolean status;
	private Boolean publish;
	private ExamCategory examCategory;
	private Boolean allowReattempts;
	private Integer passingPercentage;
	private Boolean negativeMarking;
	private byte[] examInstructions;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
		public Exam() {
		}



	public Exam(String examName, byte[] examDescription, Date startDate, Date endDate, Integer sectionCount,
				Integer questionCount, Long durationInSeconds, Integer totalMarks, String testCode, Boolean status,
				ExamCategory examCategory, Boolean allowReattempts, Integer passingPercentage, Boolean negativeMarking,
				byte[] examInstructions, Boolean active, Date created, Long createdBy, Date lastModified,
				Long lastModifiedBy) {
			super();
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
			this.examCategory = examCategory;
			this.allowReattempts = allowReattempts;
			this.passingPercentage = passingPercentage;
			this.negativeMarking = negativeMarking;
			this.examInstructions = examInstructions;
			this.active = active;
			this.created = created;
			this.createdBy = createdBy;
			this.lastModified = lastModified;
			this.lastModifiedBy = lastModifiedBy;
		}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examId", unique = true, nullable = false)
	public Integer getExamId() {
		return examId;
	}



	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examSettingId")
	public ExamHasSettings getExamHasSettings() {
		return examHasSettings;
	}



	public void setExamHasSettings(ExamHasSettings examHasSettings) {
		this.examHasSettings = examHasSettings;
	}



	@Column(name = "examName")
	public String getExamName() {
		return examName;
	}



	public void setExamName(String examName) {
		this.examName = examName;
	}


	@Lob
	@Column(name = "examDescription", columnDefinition = "BLOB")
	public byte[] getExamDescription() {
		return examDescription;
	}



	public void setExamDescription(byte[] examDescription) {
		this.examDescription = examDescription;
	}


	@Lob
	@Column(name = "examInstructions", columnDefinition = "BLOB")
	public byte[] getExamInstructions() {
		return examInstructions;
	}



	public void setExamInstructions(byte[] examInstructions) {
		this.examInstructions = examInstructions;
	}



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startDate", nullable = false, length = 19)
	public Date getStartDate() {
		return startDate;
	}






	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}






	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endDate", nullable = false, length = 19)
	public Date getEndDate() {
		return endDate;
	}






	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	@Column(name = "testCode")
	public String getTestCode() {
		return testCode;
	}






	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}




	@Column(name = "sectionCount")
	public Integer getSectionCount() {
		return sectionCount;
	}



	public void setSectionCount(Integer sectionCount) {
		this.sectionCount = sectionCount;
	}


	@Column(name = "publish", columnDefinition = "Boolean default 0")
	public Boolean getPublish() {
		return publish;
	}



	public void setPublish(Boolean publish) {
		this.publish = publish;
	}



	@Column(name = "questionCount")
	public Integer getQuestionCount() {
		return questionCount;
	}



	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}



	@Column(name = "durationInSeconds")
	public Long getDurationInSeconds() {
		return durationInSeconds;
	}



	public void setDurationInSeconds(Long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}


	@Column(name = "totalMarks")
	public Integer getTotalMarks() {
		return totalMarks;
	}



	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}




	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examCategoryId")
	public ExamCategory getExamCategory() {
		return examCategory;
	}



	public void setExamCategory(ExamCategory examCategory) {
		this.examCategory = examCategory;
	}



	@Column(name = "allowReattempts")
	public Boolean getAllowReattempts() {
		return allowReattempts;
	}



	public void setAllowReattempts(Boolean allowReattempts) {
		this.allowReattempts = allowReattempts;
	}



	@Column(name = "passingPercentage")
	public Integer getPassingPercentage() {
		return passingPercentage;
	}



	public void setPassingPercentage(Integer passingPercentage) {
		this.passingPercentage = passingPercentage;
	}



	@Column(name = "negativeMarking")
	public Boolean getNegativeMarking() {
		return negativeMarking;
	}



	public void setNegativeMarking(Boolean negativeMarking) {
		this.negativeMarking = negativeMarking;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}






	@Column(name = "active",nullable = false)
	public Boolean getActive() {
		return active;
	}




	public void setActive(Boolean active) {
		this.active = active;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return created;
	}




	public void setCreated(Date created) {
		this.created = created;
	}




	@Column(name = "createdBy", nullable = false, length = 20)
	public Long getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModified", nullable = false, length = 19)
	public Date getLastModified() {
		return lastModified;
	}




	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}




	@Column(name = "lastModifiedBy", nullable = false, length = 20)
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}




	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	
		
}
