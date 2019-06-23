package com.icat.quest.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SicoSectionMarks")
public class SicoSectionMarks implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer sicoSectionMarksId;
	private Float marksA;
	private Float marksB;
	private Float marksC;
	private Float marksD;
	private Float marksE;
	private Boolean active;
	private Date created;
	private Long createdBy;
	private Date lastModified;
	private Long lastModifiedBy;

	
	public SicoSectionMarks(Integer sicoSectionMarksId, Float marksA, Float marksB, Float marksC, Float marksD,
			Float marksE, Boolean active, Date created, Long createdBy, Date lastModified, Long lastModifiedBy) {
		super();
		this.sicoSectionMarksId = sicoSectionMarksId;
		this.marksA = marksA;
		this.marksB = marksB;
		this.marksC = marksC;
		this.marksD = marksD;
		this.marksE = marksE;
		this.active = active;
		this.created = created;
		this.createdBy = createdBy;
		this.lastModified = lastModified;
		this.lastModifiedBy = lastModifiedBy;
	}

	@Id
	@Column(name = "sicoSectionMarksId", unique = true, nullable = false)
	public Integer getSicoSectionMarksId() {
		return sicoSectionMarksId;
	}

	public void setSicoSectionMarksId(Integer sicoSectionMarksId) {
		this.sicoSectionMarksId = sicoSectionMarksId;
	}

	@Column(name = "marksA", nullable = false)
	public Float getMarksA() {
		return marksA;
	}

	public void setMarksA(Float marksA) {
		this.marksA = marksA;
	}

	@Column(name = "marksB", nullable = false)
	public Float getMarksB() {
		return marksB;
	}

	public void setMarksB(Float marksB) {
		this.marksB = marksB;
	}

	@Column(name = "marksC", nullable = false)
	public Float getMarksC() {
		return marksC;
	}

	public void setMarksC(Float marksC) {
		this.marksC = marksC;
	}

	@Column(name = "marksD", nullable = false)
	public Float getMarksD() {
		return marksD;
	}

	public void setMarksD(Float marksD) {
		this.marksD = marksD;
	}

	@Column(name = "marksE", nullable = false)
	public Float getMarksE() {
		return marksE;
	}

	public void setMarksE(Float marksE) {
		this.marksE = marksE;
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
