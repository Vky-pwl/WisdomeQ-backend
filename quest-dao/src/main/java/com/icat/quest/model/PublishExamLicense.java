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
@Table(name = "PublishExamLicense")
public class PublishExamLicense implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private TestConductorLicense testConductorLicense;
	private String testConductorLicenseCode;
	private Date startTime;
	private Date endTime;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	
	
	 
	public PublishExamLicense() {
	}
	

	public PublishExamLicense(TestConductorLicense testConductorLicense, String testConductorLicenseCode,
			Date startTime, Date endTime, Boolean active, Date created, Long createdBy, Date lastModified,
			Long lastModifiedBy) {
		super();
		this.testConductorLicense = testConductorLicense;
		this.testConductorLicenseCode = testConductorLicenseCode;
		this.startTime = startTime;
		this.endTime = endTime;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "testConductorLicenseId")	
	public TestConductorLicense getTestConductorLicense() {
		return testConductorLicense;
	}


	public void setTestConductorLicense(TestConductorLicense testConductorLicense) {
		this.testConductorLicense = testConductorLicense;
	}

	@Column(name = "testConductorLicenseCode")
	public String getTestConductorLicenseCode() {
		return testConductorLicenseCode;
	}


	public void setTestConductorLicenseCode(String testConductorLicenseCode) {
		this.testConductorLicenseCode = testConductorLicenseCode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime", nullable = false, length = 19)
	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endTime", nullable = false, length = 19)
	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
