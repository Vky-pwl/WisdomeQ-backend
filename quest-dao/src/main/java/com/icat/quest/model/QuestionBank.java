package com.icat.quest.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.AnswerType;
import com.icat.quest.common.vo.Level;

@Entity
@Table(name = "QuestionBank")
public class QuestionBank implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer questionId;
	private byte[] questionStatment;
	private AnswerType correctAnswer;
	private QuestionCategory questionCategory;
	private QuestionDescription questionDescription;
	private Integer questionExamType = 1;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	private Set<QuestionOption> questionOptions;
	private Level level;

	public QuestionBank() {
	}

	public QuestionBank(byte[] questionStatment, AnswerType correctAnswer, QuestionCategory questionCategory,
			QuestionDescription questionDescription, Integer questionExamType, Boolean active, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy, Set<QuestionOption> questionOptions, Level level) {
		super();
		this.questionStatment = questionStatment;
		this.correctAnswer = correctAnswer;
		this.questionCategory = questionCategory;
		this.questionDescription = questionDescription;
		this.questionExamType = questionExamType;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
		this.questionOptions = questionOptions;
		this.level = level;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionId", unique = true, nullable = false)
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionCategoryId")
	public QuestionCategory getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(QuestionCategory questionCategory) {
		this.questionCategory = questionCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionDescriptionId")
	public QuestionDescription getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(QuestionDescription questionDescription) {
		this.questionDescription = questionDescription;
	}

	@Lob
	@Column(name = "questionStatment", columnDefinition = "BLOB")
	public byte[] getQuestionStatment() {
		return questionStatment;
	}

	public void setQuestionStatment(byte[] questionStatment) {
		this.questionStatment = questionStatment;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "correctAnswer", nullable = false)
	public AnswerType getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(AnswerType correctAnswer) {
		this.correctAnswer = correctAnswer;
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

	@Column(name = "questionExamType")
	public Integer getQuestionExamType() {
		return questionExamType;
	}

	public void setQuestionExamType(Integer questionExamType) {
		this.questionExamType = questionExamType;
	}

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "level", nullable = false)
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "questionBank")
	public Set<QuestionOption> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(Set<QuestionOption> questionOptions) {
		this.questionOptions = questionOptions;
	}

}
