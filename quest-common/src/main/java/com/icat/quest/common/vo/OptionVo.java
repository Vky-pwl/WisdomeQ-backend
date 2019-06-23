/**
 * 
 */
package com.icat.quest.common.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8394239850205705450L;

	private Integer questionOptionId;
	private String optionValue;
	private byte[] optionValueData;
	private AnswerType optionName;
	private Boolean optionType;
	private Float marks;

	public OptionVo() {
		super();
	}

	

	public OptionVo(Integer questionOptionId, String optionValue, AnswerType optionName, Boolean optionType, Float marks) {
		super();
		this.questionOptionId = questionOptionId;
		this.optionValue = optionValue;
		this.optionName = optionName;
		this.optionType = optionType;
		this.marks = marks;
	}



	public Integer getQuestionOptionId() {
		return questionOptionId;
	}



	public void setQuestionOptionId(Integer questionOptionId) {
		this.questionOptionId = questionOptionId;
	}



	public String getOptionValue() {
		return optionValue;
	}

	@JsonProperty("optionValue")
	public void setOptionValue(String optionValue) {
			this.optionValue = optionValue;
	}


	public Boolean getOptionType() {
		return optionType;
	}

	@JsonProperty("optionType")
	public void setOptionType(Boolean optionType) {
		this.optionType = optionType;
	}



	public AnswerType getOptionName() {
		return optionName;
	}


	@JsonProperty("optionName")
	public void setOptionName(String optionName) {
		for(AnswerType answerType : AnswerType.values()) {
			if(answerType.name().equals(optionName)) {
				this.optionName = AnswerType.valueOf(optionName);	
				break;
			}
			}
		switch (optionName) {
		case "0":
			this.optionName = AnswerType.A;		
			break;
		case "1":
			this.optionName = AnswerType.B;		
			break;
		case "2":
			this.optionName = AnswerType.C;		
			break;
		case "3":
			this.optionName = AnswerType.D;		
			break;
		case "4":
			this.optionName = AnswerType.E;		
			break;

		default:
			break;
		}

	}


	public Float getMarks() {
		return marks;
	}

	@JsonProperty("marks")
	public void setMarks(Float marks) {
		this.marks = marks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public byte[] getOptionValueData() {
		return optionValueData;
	}


	public void setOptionValueData(byte[] optionValueData) {
		this.optionValueData = optionValueData;
	}



	@Override
	public String toString() {
		return "OptionVo [questionOptionId=" + questionOptionId + ", optionName=" + optionName + ", optionType="
				+ optionType + ", marks=" + marks + "]";
	}


	}
