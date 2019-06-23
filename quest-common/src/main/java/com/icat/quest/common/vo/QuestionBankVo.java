package com.icat.quest.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionBankVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer questionId;
	private QuestionDescriptionVo questionDescriptionVo;
	private String questionStatment;
	private List<OptionVo> options = new ArrayList<>();
	private ExplanationDescriptionVo explanation;
	private QuestionCategoryVo questionCategoryVo;
	private OptionVo correctOption;
	private AnswerType correctAnswer;
	private Boolean active;
	private byte[] questionStatmentData;
	private Level level;
	

	public QuestionBankVo() {
	}

	public Integer getQuestionId() {
		return questionId;
	}

	@JsonProperty("questionId")
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}


	public QuestionDescriptionVo getQuestionDescriptionVo() {
		return questionDescriptionVo;
	}

	@JsonProperty("questionDescriptionVo")
	public void setQuestionDescriptionVo(QuestionDescriptionVo questionDescriptionVo) {
		this.questionDescriptionVo = questionDescriptionVo;
	}

	public AnswerType getCorrectAnswer() {
		return correctAnswer;
	}

	@JsonProperty("correctAnswer")
	public void setCorrectAnswer(AnswerType correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getQuestionStatment() {
		return questionStatment;
	}

	@JsonProperty("questionStatment")
	public void setQuestionStatment(String questionStatment) {
		this.questionStatment = questionStatment;
	}

	public List<OptionVo> getOptions() {
		return options;
	}

	@JsonProperty("options")
	public void setOptions(List<OptionVo> options) {
		this.options = options;
	}

	public ExplanationDescriptionVo getExplanation() {
		return explanation;
	}

	@JsonProperty("explanation")
	public void setExplanation(ExplanationDescriptionVo explanation) {
		this.explanation = explanation;
	}

	public QuestionCategoryVo getQuestionCategoryVo() {
		return questionCategoryVo;
	}

	@JsonProperty("questionCategoryVo")
	public void setQuestionCategoryVo(QuestionCategoryVo questionCategoryVo) {
		this.questionCategoryVo = questionCategoryVo;
	}

	public OptionVo getCorrectOption() {
		return correctOption;
	}

	@JsonProperty("correctOption")
	public void setCorrectOption(OptionVo correctOption) {
		this.correctOption = correctOption;
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

	
	
	public byte[] getQuestionStatmentData() {
		return questionStatmentData;
	}

	public void setQuestionStatmentData(byte[] questionStatmentData) {
		this.questionStatmentData = questionStatmentData;
	}
	
	

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "QuestionBankVo [questionId=" + questionId + ", questionDescriptionVo=" + questionDescriptionVo
				+ ", options=" + options + ", questionCategoryVo=" + questionCategoryVo + ", correctOption="
				+ correctOption + ", correctAnswer=" + correctAnswer + ", active=" + active + "]";
	}

	
}
