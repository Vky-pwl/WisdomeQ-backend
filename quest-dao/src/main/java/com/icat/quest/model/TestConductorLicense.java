package com.icat.quest.model;

import java.io.Serializable;
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
@Table(name = "TestConductorLicense")
public class TestConductorLicense implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer testConductorLicenseId;
	private Exam exam;
	private TestConductor testConductor;
	private Integer licenseCount;
	private Integer remainingLicenseCount;
	private String testConductorCode;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;
	public TestConductorLicense() {
		super();
	}
	
	



	public TestConductorLicense(Exam exam, TestConductor testConductor, Integer licenseCount,
			Integer remainingLicenseCount, String testConductorCode, Boolean active, Date created, Long createdBy,
			Date lastModified, Long lastModifiedBy) {
		super();
		this.exam = exam;
		this.testConductor = testConductor;
		this.licenseCount = licenseCount;
		this.remainingLicenseCount = remainingLicenseCount;
		this.testConductorCode = testConductorCode;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}





	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testConductorLicenseId", unique = true, nullable = false)
	public Integer getTestConductorLicenseId() {
		return testConductorLicenseId;
	}



	public void setTestConductorLicenseId(Integer testConductorLicenseId) {
		this.testConductorLicenseId = testConductorLicenseId;
	}


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="examId")
	public Exam getExam() {
		return exam;
	}



	public void setExam(Exam exam) {
		this.exam = exam;
	}



	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="testConductorId")
	public TestConductor getTestConductor() {
		return testConductor;
	}



	public void setTestConductor(TestConductor testConductor) {
		this.testConductor = testConductor;
	}

	@Column(name = "testConductorCode")
	public String getTestConductorCode() {
		return testConductorCode;
	}





	public void setTestConductorCode(String testConductorCode) {
		this.testConductorCode = testConductorCode;
	}





	@Column(name = "licenseCount")
	public Integer getLicenseCount() {
		return licenseCount;
	}
	public void setLicenseCount(Integer licenseCount) {
		this.licenseCount = licenseCount;
	}
	
	@Column(name = "remainingLicenseCount")
	public Integer getRemainingLicenseCount() {
		return remainingLicenseCount;
	}
	public void setRemainingLicenseCount(Integer remainingLicenseCount) {
		this.remainingLicenseCount = remainingLicenseCount;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
