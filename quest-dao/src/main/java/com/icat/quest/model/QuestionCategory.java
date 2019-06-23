package com.icat.quest.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "QuestionCategory")
public class QuestionCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer questionCategoryId;
	private String questionCategoryName;
	private Integer parentQuestionCategoryId;
	private String parentQuestionCategoryName;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	public QuestionCategory() {
		super();
	}
	public QuestionCategory(String questionCategoryName, Integer parentQuestionCategoryId,
			String parentQuestionCategoryName, Boolean active, Date created, Long createdBy, Date lastModified,
			Long lastModifiedBy) {
		super();
		this.questionCategoryName = questionCategoryName;
		this.parentQuestionCategoryId = parentQuestionCategoryId;
		this.parentQuestionCategoryName = parentQuestionCategoryName;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionCategoryId", unique = true, nullable = false)
	public Integer getQuestionCategoryId() {
		return questionCategoryId;
	}
	public void setQuestionCategoryId(Integer questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}
	@Column(name = "questionCategoryName")
	public String getQuestionCategoryName() {
		return questionCategoryName;
	}
	public void setQuestionCategoryName(String questionCategoryName) {
		this.questionCategoryName = questionCategoryName;
	}
	@Column(name = "parentQuestionCategoryId")
	public Integer getParentQuestionCategoryId() {
		return parentQuestionCategoryId;
	}
	public void setParentQuestionCategoryId(Integer parentQuestionCategoryId) {
		this.parentQuestionCategoryId = parentQuestionCategoryId;
	}
	@Column(name = "parentQuestionCategoryName")
	public String getParentQuestionCategoryName() {
		return parentQuestionCategoryName;
	}
	public void setParentQuestionCategoryName(String parentQuestionCategoryName) {
		this.parentQuestionCategoryName = parentQuestionCategoryName;
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

	@Column(name = "createdBy", nullable = false)
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

	@Column(name = "lastModifiedBy", nullable = false)
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	
	
}
