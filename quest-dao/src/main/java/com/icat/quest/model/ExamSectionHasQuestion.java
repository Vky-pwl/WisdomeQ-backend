package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "ExamSectionHasQuestion")
public class ExamSectionHasQuestion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer examSectionHasQuestionId;
	private ExamSection examSection;
	private QuestionBank questionBank;
	private Integer questionNumber;
	private Float marks;
	private Float negativeMark;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 
	public ExamSectionHasQuestion() {
	}

	

	public ExamSectionHasQuestion(Integer examSectionHasQuestionId, Integer questionNumber, Float marks,
			Boolean active) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.questionNumber = questionNumber;
		this.marks = marks;
		this.active = active;
	}
	

	public ExamSectionHasQuestion(ExamSection examSection, QuestionBank questionBank,
			Integer questionNumber, Float marks, Float negativeMark, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.examSection = examSection;
		this.questionBank = questionBank;
		this.questionNumber = questionNumber;
		this.marks = marks;
		this.negativeMark = negativeMark;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public ExamSectionHasQuestion(Integer examSectionHasQuestionId, QuestionBank questionBank,
			Integer questionNumber, Float marks, Float negativeMark) {
		super();
		this.examSectionHasQuestionId = examSectionHasQuestionId;
		this.questionBank = questionBank;
		this.questionNumber = questionNumber;
		this.marks = marks;
		this.negativeMark = negativeMark;
	}
	



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examSectionHasQuestionId", unique = true, nullable = false)
	public Integer getExamSectionHasQuestionId() {
		return examSectionHasQuestionId;
	}





	public void setExamSectionHasQuestionId(Integer examSectionHasQuestionId) {
		this.examSectionHasQuestionId = examSectionHasQuestionId;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examSectionId")	
	public ExamSection getExamSection() {
		return examSection;
	}


	public void setExamSection(ExamSection examSection) {
		this.examSection = examSection;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionId")	
	public QuestionBank getQuestionBank() {
		return questionBank;
	}




	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}




	@Column(name = "questionNumber")	
	public Integer getQuestionNumber() {
		return questionNumber;
	}





	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}





	@Column(name = "marks")	
	public Float getMarks() {
		return marks;
	}





	public void setMarks(Float marks) {
		this.marks = marks;
	}

	@Column(name = "negativeMark",nullable = false)
	public Float getNegativeMark() {
		return negativeMark;
	}



	public void setNegativeMark(Float negativeMark) {
		this.negativeMark = negativeMark;
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




	public Date getCreated() {
		return created;
	}




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
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
