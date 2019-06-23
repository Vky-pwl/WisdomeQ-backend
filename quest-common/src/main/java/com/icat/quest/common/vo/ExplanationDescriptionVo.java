package com.icat.quest.common.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExplanationDescriptionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer explanationId;
	private Integer questionId;
	private String explanationText;
	private byte[] explanationTextData;
	private byte[] explanationImage;
	private Boolean explanationTextType;
	private Boolean active;

	public ExplanationDescriptionVo() {
		super();
	}

	public ExplanationDescriptionVo(Integer explanationId, Integer questionId, String explanationText,
			byte[] explanationImage, Boolean explanationTextType, Boolean active) {
		super();
		this.explanationId = explanationId;
		this.questionId = questionId;
		this.explanationText = explanationText;
		this.explanationImage = explanationImage;
		this.explanationTextType = explanationTextType;
		this.active = active;
	}

	public Integer getExplanationId() {
		return explanationId;
	}

	@JsonProperty("explanationId")
	public void setExplanationId(Integer explanationId) {
		this.explanationId = explanationId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	@JsonProperty("questionId")
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getExplanationText() {
		return explanationText;
	}

	@JsonProperty("explanationText")
	public void setExplanationText(String explanationText) {
		this.explanationText = explanationText;
	}

	public byte[] getExplanationImage() {
		return explanationImage;
	}

	@JsonProperty("explanationImage")
	public void setExplanationImage(byte[] explanationImage) {
		this.explanationImage = explanationImage;
	}

	public Boolean getExplanationTextType() {
		return explanationTextType;
	}

	@JsonProperty("explanationTextType")
	public void setExplanationTextType(Boolean explanationTextType) {
		this.explanationTextType = explanationTextType;
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

	
	
	public byte[] getExplanationTextData() {
		return explanationTextData;
	}

	public void setExplanationTextData(byte[] explanationTextData) {
		this.explanationTextData = explanationTextData;
	}

	@Override
	public String toString() {
		return "ExplanationDescriptionVo [explanationId=" + explanationId + ", questionId=" + questionId
				+ ", explanationTextType=" + explanationTextType + ", active=" + active + "]";
	}

	
}
