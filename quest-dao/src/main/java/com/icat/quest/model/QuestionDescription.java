package com.icat.quest.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "QuestionDescription")
public class QuestionDescription implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer descriptionId;
	private byte[] description;
	private Boolean descriptionTextType;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 	
	public QuestionDescription() {
		super();
	}

	public QuestionDescription( byte[] description, Boolean descriptionTextType, Boolean active,
			Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.description = description;
		this.descriptionTextType = descriptionTextType;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}



	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "descriptionId", unique = true, nullable = false)
	public Integer getDescriptionId() {
		return descriptionId;
	}



	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}


	@Lob
	@Column(name = "description", columnDefinition="BLOB")
	public byte[] getDescription() {
		return description;
	}



	public void setDescription(byte[] description) {
		this.description = description;
	}



	@Column(name = "descriptionTextType")
	public Boolean getDescriptionTextType() {
		return descriptionTextType;
	}



	public void setDescriptionTextType(Boolean descriptionTextType) {
		this.descriptionTextType = descriptionTextType;
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
