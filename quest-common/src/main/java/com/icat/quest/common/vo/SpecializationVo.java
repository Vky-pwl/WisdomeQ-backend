package com.icat.quest.common.vo;

public class SpecializationVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer specializationId;
	private String specializationName;
	private String specializationCode;
	private Boolean active;
	
	public SpecializationVo() {
	}

	public SpecializationVo(Integer specializationId, String specializationName, String specializationCode,
			Boolean active) {
		super();
		this.specializationId = specializationId;
		this.specializationName = specializationName;
		this.specializationCode = specializationCode;
		this.active = active;
			}

	public Integer getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Integer specializationId) {
		this.specializationId = specializationId;
	}

	public String getSpecializationName() {
		return specializationName;
	}

	public void setSpecializationName(String specializationName) {
		this.specializationName = specializationName;
	}

	public String getSpecializationCode() {
		return specializationCode;
	}

	public void setSpecializationCode(String specializationCode) {
		this.specializationCode = specializationCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "SpecializationVo [specializationId=" + specializationId + ", specializationName=" + specializationName
				+ ", specializationCode=" + specializationCode + ", active=" + active + "]";
	}
	
}
