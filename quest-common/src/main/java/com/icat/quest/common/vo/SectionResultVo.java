package com.icat.quest.common.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.icat.quest.common.utils.ResultPercentileService;

public class SectionResultVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer examSectionId;
	private String examSectionName;
	private Integer totalMarks;
	private Float userTotalMarks=0f;
	private Float percentile;
	private String grade;
	private String sectionName;
	private Integer totalQuestion;
	private Integer totalAttemptQuestion=0;
	private Integer totalCorrectAnswer=0;
	private Level level;
	private Map<Integer, QuestionCategoryVo> questionCategoryVoList = new HashMap<>();
	
	public SectionResultVo() {
		super();
	}

	
	
	public SectionResultVo(Integer examSectionId) {
		super();
		this.examSectionId = examSectionId;
	}



	public Integer getExamSectionId() {
		return examSectionId;
	}

	public void setExamSectionId(Integer examSectionId) {
		this.examSectionId = examSectionId;
	}

	public String getExamSectionName() {
		return examSectionName;
	}

	public void setExamSectionName(String examSectionName) {
		this.examSectionName = examSectionName;
	}

	public Integer getTotalMarks() {
		return totalMarks;
	}

	public void setTotalMarks(Integer totalMarks) {
		this.totalMarks = totalMarks;
	}

	public Float getUserTotalMarks() {
		return userTotalMarks;
	}

	public void setUserTotalMarks(Float userTotalMarks) {
		this.userTotalMarks = userTotalMarks;
	}
	
	public void addUserTotalMarks(Float questionMarks) {
		this.userTotalMarks = this.userTotalMarks+ questionMarks;
	}
	
	public void subUserTotalMarks(Float negativeMarks) {
		this.userTotalMarks = this.userTotalMarks- negativeMarks;
	}

	public Float getPercentile() {
		return percentile;
	}

	public void setPercentile(Float percentile) {
		this.percentile = percentile;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Integer totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public Integer getTotalAttemptQuestion() {
		return totalAttemptQuestion;
	}

	public void setTotalAttemptQuestion(Integer totalAttemptQuestion) {
		this.totalAttemptQuestion = totalAttemptQuestion;
	}

	public void incrTotalAttemptQuestion() {
		this.totalAttemptQuestion = this.totalAttemptQuestion+1;
	}
	
	public Integer getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}

	public void setTotalCorrectAnswer(Integer totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
	}

	public void incrTotalCorrectAnswer() {
		this.totalCorrectAnswer = this.totalCorrectAnswer+1;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Map<Integer, QuestionCategoryVo> getQuestionCategoryVoList() {
		return questionCategoryVoList;
	}



	public void setQuestionCategoryVoList(Map<Integer, QuestionCategoryVo> questionCategoryVoList) {
		this.questionCategoryVoList = questionCategoryVoList;
	}



	public String getSectionName() {
		return sectionName;
	}



	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}



	public Level getLevel() {
		return level;
	}



	public void setLevel(Level level) {
		this.level = level;
	}

		
	public void calculateSectionResult() {
		Integer totalAttempt = 0;
		Integer totalCorrectAnswer = 0;
		Float userMarks = 0f;
		for(QuestionCategoryVo questionCategoryVo : getQuestionCategoryVoList().values()) {
			totalAttempt+=questionCategoryVo.getTotalAttempt();
			totalCorrectAnswer+= questionCategoryVo.getTotalCorrectAnswer();
			userMarks+= questionCategoryVo.getUserTotalMarks();
		}
		this.totalAttemptQuestion = totalAttempt;
		this.totalCorrectAnswer = totalCorrectAnswer;
		this.userTotalMarks = userMarks;
		Map<String, Object> percMap = ResultPercentileService.getPercentile(this.sectionName, this.userTotalMarks);
		this.grade = percMap.get("grade").toString();
		this.percentile = (Float) percMap.get("percentile");
		this.level = (Level) percMap.get("level");	
	}
	
}
