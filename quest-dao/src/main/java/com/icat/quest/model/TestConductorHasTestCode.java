package com.icat.quest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.ExamStatus;

@Entity
@Table(name = "TestConductorHasTestCode")
public class TestConductorHasTestCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer testConductorHasTestCodeId;
	private TestConductorLicense testConductorLicense;
	private String testCode;
	private Candidate user;
	private Date examStartDate;
	private Exam exam;
	private Integer totalQuestion;
	private Integer totalMarks;
	private String tinyKey;
	private Integer totalAttempts;
	private Integer totalCorrectAnswer;
	private Float userTotalMarks;
	private Date submitDate;
	private ExamStatus status;
	private Long remainingTime;
	private Long sectionRemainingTime;
	private Integer currentSectionId;
	private ExamStatus currentSectionStatus;
	private Integer currentQuestionId;
	private ExamStatus currentQuestionStatus;
	private Boolean attended;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;

	public TestConductorHasTestCode() {
		super();
	}

	public TestConductorHasTestCode(Exam exam, Candidate user, String tinyKey) {
		this.exam = exam;
		this.user = user;
		this.tinyKey = tinyKey;
	}

	public TestConductorHasTestCode(Exam exam, String tinyKey) {
		this.exam = exam;
		this.tinyKey = tinyKey;
	}

	public TestConductorHasTestCode(Exam exam) {
		this.exam = exam;
	}

	public TestConductorHasTestCode(TestConductorLicense testConductorLicense, String testCode, Candidate user,
			Boolean active, Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.testConductorLicense = testConductorLicense;
		this.testCode = testCode;
		this.user = user;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testConductorHasTestCodeId", unique = true, nullable = false)
	public Integer getTestConductorHasTestCodeId() {
		return testConductorHasTestCodeId;
	}

	public void setTestConductorHasTestCodeId(Integer testConductorHasTestCodeId) {
		this.testConductorHasTestCodeId = testConductorHasTestCodeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "testConductorLicenseId", nullable = false)
	public TestConductorLicense getTestConductorLicense() {
		return testConductorLicense;
	}

	public void setTestConductorLicense(TestConductorLicense testConductorLicense) {
		this.testConductorLicense = testConductorLicense;
	}

	@Column(name = "testCode")
	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public Candidate getUser() {
		return user;
	}

	public void setUser(Candidate user) {
		this.user = user;
	}

	@Column(name = "attended")
	public Boolean getAttended() {
		return attended;
	}

	public void setAttended(Boolean attended) {
		this.attended = attended;
	}

	@Column(name = "totalMarks")
	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	@Column(name = "totalAttempts")
	public Integer getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(Integer totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	@Column(name = "userTotalMarks")
	public Float getUserTotalMarks() {
		return userTotalMarks;
	}

	public void setUserTotalMarks(Float userTotalMarks) {
		this.userTotalMarks = userTotalMarks;
	}
	
	@Column(name = "totalCorrectAnswer")
	public Integer getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}

	public void setTotalCorrectAnswer(Integer totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}

	@Column(name = "totalQuestion")
	public Integer getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examId", nullable = false)
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	@Column(name = "tinyKey")
	public String getTinyKey() {
		return tinyKey;
	}

	public void setTinyKey(String tinyKey) {
		this.tinyKey = tinyKey;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "status", nullable = false)
	public ExamStatus getStatus() {
		return status;
	}

	public void setStatus(ExamStatus status) {
		this.status = status;
	}

	@Column(name = "remainingTime")
	public Long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(Long remainingTime) {
		this.remainingTime = remainingTime;
	}

	
	
	@Column(name = "sectionRemainingTime")
	public Long getSectionRemainingTime() {
		return sectionRemainingTime;
	}

	public void setSectionRemainingTime(Long sectionRemainingTime) {
		this.sectionRemainingTime = sectionRemainingTime;
	}

	@Column(name = "currentSectionId")
	public Integer getCurrentSectionId() {
		return currentSectionId;
	}

	public void setCurrentSectionId(Integer currentSectionId) {
		this.currentSectionId = currentSectionId;
	}
	
	

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "currentSectionStatus", nullable = false)
	public ExamStatus getCurrentSectionStatus() {
		return currentSectionStatus;
	}

	public void setCurrentSectionStatus(ExamStatus currentSectionStatus) {
		this.currentSectionStatus = currentSectionStatus;
	}

	@Column(name = "currentQuestionId")
	public Integer getCurrentQuestionId() {
		return currentQuestionId;
	}

	public void setCurrentQuestionId(Integer currentQuestionId) {
		this.currentQuestionId = currentQuestionId;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "currentQuestionStatus", nullable = false)
	public ExamStatus getCurrentQuestionStatus() {
		return currentQuestionStatus;
	}

	public void setCurrentQuestionStatus(ExamStatus currentQuestionStatus) {
		this.currentQuestionStatus = currentQuestionStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "examStartDate", length = 19)
	public Date getExamStartDate() {
		return examStartDate;
	}

	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submitDate", length = 19)
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "active", nullable = false)
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
