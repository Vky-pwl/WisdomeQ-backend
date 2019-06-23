package com.icat.quest.common.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionDescriptionVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer descriptionId;
	private String descriptionText;
	private byte[] descriptionTextData;
	private byte[] descriptionImage;
	private Boolean descriptionTextType;
	private Boolean active;
	public QuestionDescriptionVo() {
		super();
	}
	public Integer getDescriptionId() {
		return descriptionId;
	}
	@JsonProperty("descriptionId")
	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}
	
	public String getDescriptionText() {
		return descriptionText;
	}
	@JsonProperty("descriptionText")
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}
	public byte[] getDescriptionImage() {
		return descriptionImage;
	}
	@JsonProperty("descriptionImage")
	public void setDescriptionImage(byte[] descriptionImage) {
		this.descriptionImage = descriptionImage;
	}
	public Boolean getDescriptionTextType() {
		return descriptionTextType;
	}
	@JsonProperty("descriptionTextType")
	public void setDescriptionTextType(Boolean descriptionTextType) {
		this.descriptionTextType = descriptionTextType;
	}
	public Boolean getActive() {
		return active;
	}
	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public byte[] getDescriptionTextData() {
		return descriptionTextData;
	}
	
	public void setDescriptionTextData(byte[] descriptionTextData) {
		this.descriptionTextData = descriptionTextData;
	}
	@Override
	public String toString() {
		return "QuestionDescriptionVo [descriptionId=" + descriptionId + ", descriptionTextType=" + descriptionTextType
				+ ", active=" + active + "]";
	}
	
	
}
