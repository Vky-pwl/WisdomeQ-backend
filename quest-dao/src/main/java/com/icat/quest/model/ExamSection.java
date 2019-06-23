package com.icat.quest.model;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.SectionName;

@Entity
@Table(name = "ExamSection")
public class ExamSection implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSectionId;
	private Exam exam;
	private QuestionCategory questionCategory;
	private byte[] examSectionDescription;
	private Integer sequence;
	private Long durationInSeconds;
	private Integer totalMarks;
	private Integer totalQuestion;
	private Boolean sicoFlag;
	private byte[] examSectionInstructions;
	private SectionName sectionName;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;

	public ExamSection() {
	}

	public ExamSection(Exam exam, QuestionCategory questionCategory, byte[] examSectionDescription, Integer sequence,
			Long durationInSeconds, Integer totalMarks, Integer totalQuestion, Boolean sicoFlag,
			byte[] examSectionInstructions, SectionName sectionName, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.exam = exam;
		this.questionCategory = questionCategory;
		this.examSectionDescription = examSectionDescription;
		this.sequence = sequence;
		this.durationInSeconds = durationInSeconds;
		this.totalMarks = totalMarks;
		this.totalQuestion = totalQuestion;
		this.sicoFlag = sicoFlag;
		this.examSectionInstructions = examSectionInstructions;
		this.sectionName = sectionName;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examSectionId", unique = true, nullable = false)
	public Integer getExamSectionId() {
		return examSectionId;
	}

	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examId")
	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionCategoryId")
	public QuestionCategory getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(QuestionCategory questionCategory) {
		this.questionCategory = questionCategory;
	}

	@Lob
	@Column(name = "examSectionDescription", columnDefinition = "BLOB")
	public byte[] getExamSectionDescription() {
		return examSectionDescription;
	}

	public void setExamSectionDescription(byte[] examSectionDescription) {
		this.examSectionDescription = examSectionDescription;
	}

	@Lob
	@Column(name = "examSectionInstructions", columnDefinition = "BLOB")
	public byte[] getExamSectionInstructions() {
		return examSectionInstructions;
	}

	public void setExamSectionInstructions(byte[] examSectionInstructions) {
		this.examSectionInstructions = examSectionInstructions;
	}

	@Column(name = "sequence")
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	@Column(name = "totalQuestion")
	public Integer getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "sicoFlag")
	public Boolean getSicoFlag() {
		return sicoFlag;
	}

	public void setSicoFlag(Boolean sicoFlag) {
		this.sicoFlag = sicoFlag;
	}

	@Column(name = "active", nullable = false)
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "sectionName", nullable = false)
	public SectionName getSectionName() {
		return sectionName;
	}

	public void setSectionName(SectionName sectionName) {
		this.sectionName = sectionName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return created;
	}

	@Column(name = "createdBy", nullable = false, length = 20)
	public void setCreated(Date created) {
		this.created = created;
	}

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
