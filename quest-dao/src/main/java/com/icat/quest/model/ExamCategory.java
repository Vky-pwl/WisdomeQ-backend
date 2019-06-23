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
@Table(name = "ExamCategory")
public class ExamCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examCategoryId;
	private String examCategoryName;
	private Integer parentExamCategoryId;
	private String parentExamCategoryName;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	public ExamCategory() {
		super();
	}
	
	

	public ExamCategory(String examCategoryName, Integer parentExamCategoryId, String parentExamCategoryName,
			Boolean active, Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.examCategoryName = examCategoryName;
		this.parentExamCategoryId = parentExamCategoryId;
		this.parentExamCategoryName = parentExamCategoryName;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examCategoryId", unique = true, nullable = false)
	public Integer getExamCategoryId() {
		return examCategoryId;
	}



	public void setExamCategoryId(Integer examCategoryId) {
		this.examCategoryId = examCategoryId;
	}


	@Column(name = "examCategoryName")
	public String getExamCategoryName() {
		return examCategoryName;
	}



	public void setExamCategoryName(String examCategoryName) {
		this.examCategoryName = examCategoryName;
	}



	@Column(name = "parentExamCategoryId")
	public Integer getParentExamCategoryId() {
		return parentExamCategoryId;
	}



	public void setParentExamCategoryId(Integer parentExamCategoryId) {
		this.parentExamCategoryId = parentExamCategoryId;
	}



	@Column(name = "parentExamCategoryName")
	public String getParentExamCategoryName() {
		return parentExamCategoryName;
	}



	public void setParentExamCategoryName(String parentExamCategoryName) {
		this.parentExamCategoryName = parentExamCategoryName;
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
