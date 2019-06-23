package com.icat.quest.model;


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
@Table(name = "Specialization")
public class Specialization implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer specializationId;
	private String specializationName;
	private String specializationCode;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	public Specialization() {
	}


	

	public Specialization(String specializationName, String specializationCode, Boolean active, Date created,
			Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.specializationName = specializationName;
		this.specializationCode = specializationCode;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "specializationId", unique = true, nullable = false)
	public Integer getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Integer specializationId) {
		this.specializationId = specializationId;
	}


	
	@Column(name = "specializationName", nullable = false, length = 45)
	public String getSpecializationName() {
		return specializationName;
	}




	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}




	@Column(name = "specializationCode",/* unique = true,*/  length = 45)
	public String getSpecializationCode() {
		return specializationCode;
	}




	public void setSpecializationCode(String specializationCode) {
		this.specializationCode = specializationCode;
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
