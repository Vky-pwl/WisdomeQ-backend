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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.icat.quest.common.vo.AnswerType;

@Entity
@Table(name = "QuestionOption")
public class QuestionOption implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer questionOptionId;
	private QuestionBank questionBank;
	private byte[] optionValue;
	private AnswerType optionName;
	private Boolean optionType;
	private Float marks;
	private Boolean active;
	private Date created;
	
	
	
	public QuestionOption() {
		super();
	}
	public QuestionOption(QuestionBank questionBank, byte[] optionValue, AnswerType optionName, Boolean optionType, Float marks, Boolean active,
			Date created) {
		super();
		this.questionBank = questionBank;
		this.optionValue = optionValue;
		this.optionName = optionName;
		this.optionType = optionType;
		this.marks = marks;
		this.active = active;
		this.created = created;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionOptionId", unique = true, nullable = false)
	public Integer getQuestionOptionId() {
		return questionOptionId;
	}

	public void setQuestionOptionId(Integer questionOptionId) {
		this.questionOptionId = questionOptionId;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questionId")	
	public QuestionBank getQuestionBank() {
		return questionBank;
	}
	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}
	
	@Lob
	@Column(name = "optionValue", columnDefinition="BLOB")
	public byte[] getOptionValue() {
		return optionValue;
	}
	
	public void setOptionValue(byte[] optionValue) {
		this.optionValue = optionValue;
	}
	
	@Enumerated(EnumType.STRING)
	 @Column(columnDefinition = "optionName", nullable = false)
	public AnswerType getOptionName() {
		return optionName;
	}

	public void setOptionName(AnswerType optionName) {
		this.optionName = optionName;
	}

	@Column(name = "optionType")
	public Boolean getOptionType() {
		return optionType;
	}

	public void setOptionType(Boolean optionType) {
		this.optionType = optionType;
	}

	@Column(name = "marks")
	public Float getMarks() {
		return marks;
	}

	public void setMarks(Float marks) {
		this.marks = marks;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
