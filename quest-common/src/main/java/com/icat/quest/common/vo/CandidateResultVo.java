package com.icat.quest.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CandidateResultVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer candidateId;
	private String candidateFirstName;
	private String candidateLastName;
	private Integer examId;
	private String examName;
	private Integer totalQuestion =0;
	private Integer totalAttemptQuestion =0 ;
	private Integer totalCorrectAnswer =0;
	private Integer totalMarks = 0;
	private Float userTotalMarks = 0f;
	private List<SectionResultVo> sectionResultList = new ArrayList<>();
	private String examCategoryName;
	private Long examDate;
	private String certificateCode;
	private Float percentile=0f;
	private Boolean belowAverage = false;
	private String contactEmail;
	private String contactNumber;
	private Integer rank;
	private Level technicalLevel;
	private Level quantitativeLevel;
	private Float technicalPercentile;
	private float quantitativePercentile;
	private ArrayList<String> aptitudeSections = new ArrayList<>();

	public CandidateResultVo() {
		super();
		aptitudeSections.add(SectionName.Quantitative.name());
		aptitudeSections.add(SectionName.Reasoning.name());
		aptitudeSections.add(SectionName.English.name());
	}

	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getContactNumber() {
		return contactNumber;
	}


	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateFirstName() {
		return candidateFirstName;
	}

	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}

	public String getCandidateLastName() {
		return candidateLastName;
	}

	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
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

	public Integer getTotalCorrectAnswer() {
		return totalCorrectAnswer;
	}

	public void setTotalCorrectAnswer(Integer totalCorrectAnswer) {
		this.totalCorrectAnswer = totalCorrectAnswer;
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

	public List<SectionResultVo> getSectionResultList() {
		return sectionResultList;
	}

	public void setSectionResultList(List<SectionResultVo> sectionResultList) {
		this.sectionResultList = sectionResultList;
	}

	public String getExamCategoryName() {
		return examCategoryName;
	}



	public void setExamCategoryName(String examCategoryName) {
		this.examCategoryName = examCategoryName;
	}



	public Long getExamDate() {
		return examDate;
	}



	public void setExamDate(Long examDate) {
		this.examDate = examDate;
	}





	public String getCertificateCode() {
		return certificateCode;
	}



	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Float getPercentile() {
		return percentile;
	}


	public void setPercentile(Float percentile) {
		this.percentile = percentile;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public void calculatePercentile() {
		if(sectionResultList != null) {
			Iterator<SectionResultVo> it = sectionResultList.iterator();
			float technicalPercentile = 0f;
			float aptitudePercentile = 0f;
			int technicalCount =0;
			int aptittudeCount=0;
			while(it.hasNext()) {
				SectionResultVo sectionResultVo = it.next();
				if(sectionResultVo.getGrade() != null && sectionResultVo.getGrade().equals("C")) {
					setBelowAverage(true);
				}
				if(sectionResultVo.getPercentile() != null &&
						!SectionName.Psychometric.name().equals(sectionResultVo.getSectionName()) &&
						!SectionName.Technical.name().equals(sectionResultVo.getSectionName())) {
					percentile= percentile+sectionResultVo.getPercentile();
				}
				if(aptitudeSections.contains(sectionResultVo.getSectionName())) {
					aptittudeCount++;
					aptitudePercentile+=sectionResultVo.getPercentile();
				} else if(SectionName.Technical.name().equals(sectionResultVo.getSectionName())){
					technicalCount++;
					technicalPercentile+=sectionResultVo.getPercentile();
				}
			}
			float aptitudePercentileAverage = aptitudePercentile/3;
			setQuantitativePercentile(aptitudePercentileAverage);
			if (aptitudePercentileAverage >= 65) {
				setQuantitativeLevel(Level.HIGH);
			} else if (aptitudePercentile/3 < 65 && aptitudePercentile/3 >= 40) {
				setQuantitativeLevel(Level.MEDIUM);
			} else {
				setQuantitativeLevel(Level.LOW);
			}
			technicalPercentile=technicalPercentile/technicalCount;
			setTechnicalPercentile(technicalPercentile);
			if (technicalPercentile >= 65) {
				setTechnicalLevel(Level.HIGH);
			} else if (technicalPercentile < 65 && technicalPercentile >= 40) {
				setTechnicalLevel(Level.MEDIUM);
			} else {
				setTechnicalLevel(Level.LOW);
			}
			setPercentile(technicalPercentile > 0f ?
					technicalPercentile + aptitudePercentileAverage / 2
					: aptitudePercentileAverage
			);
		}
	}


	public Level getTechnicalLevel() {
		return technicalLevel;
	}

	public void setTechnicalLevel(Level technicalLevel) {
		this.technicalLevel = technicalLevel;
	}

	public Level getQuantitativeLevel() {
		return quantitativeLevel;
	}

	public void setQuantitativeLevel(Level quantitativeLevel) {
		this.quantitativeLevel = quantitativeLevel;
	}

	public Boolean getBelowAverage() {
		return belowAverage;
	}

	public void setBelowAverage(Boolean belowAverage) {
		this.belowAverage = belowAverage;
	}

	public Float getTechnicalPercentile() {
		return technicalPercentile;
	}

	public void setTechnicalPercentile(Float technicalPercentile) {
		this.technicalPercentile = technicalPercentile;
	}

	public float getQuantitativePercentile() {
		return quantitativePercentile;
	}

	public void setQuantitativePercentile(float quantitativePercentile) {
		this.quantitativePercentile = quantitativePercentile;
	}

	public void calculateCandidateResult() {
		if (sectionResultList != null) {
			Iterator<SectionResultVo> it = sectionResultList.iterator();
			float technicalPercentile = 0f;
			float aptitudePercentile = 0f;
			int sectionCount = 0;
			int technicalCount = 0;
			int aptitudeCount = 0;
			Float percentile = 0f;
			Integer totalAttemptQuestion = 0;
			Integer totalCorrectAnswer = 0;
			Float totalUserMarks = 0f;
			while (it.hasNext()) {
				SectionResultVo sectionResultVo = it.next();
				if (SectionName.Psychometric.name().equals(sectionResultVo.getSectionName())
						|| SectionName.Other.name().equals(sectionResultVo.getSectionName())) {
					continue;
				}

				percentile += sectionResultVo.getPercentile();
				sectionCount++;
				totalAttemptQuestion+=sectionResultVo.getTotalAttemptQuestion();
				totalCorrectAnswer+=sectionResultVo.getTotalCorrectAnswer();
				totalUserMarks+=sectionResultVo.getUserTotalMarks();
				if (sectionResultVo.getGrade() != null && sectionResultVo.getGrade().equals("C")) {
					this.belowAverage = true;
				}
				if (aptitudeSections.contains(sectionResultVo.getSectionName())) {
					aptitudeCount++;
					aptitudePercentile += sectionResultVo.getPercentile();
				} else {
					technicalCount++;
					technicalPercentile += sectionResultVo.getPercentile();
				}
			}
			this.totalAttemptQuestion = totalAttemptQuestion;
			this.totalCorrectAnswer = totalCorrectAnswer;
			this.userTotalMarks = totalUserMarks;
			this.quantitativePercentile = aptitudePercentile / aptitudeCount;
			this.technicalPercentile = technicalPercentile / technicalCount;
			this.setTechnicalPercentile(this.technicalPercentile);
			float totalPercentile = (this.technicalPercentile > 0f)
					? (this.technicalPercentile + this.quantitativePercentile) / 2
					: this.quantitativePercentile;
			this.setPercentile(totalPercentile);
			if (this.technicalPercentile >= 65) {
				this.technicalLevel = Level.HIGH;
			} else if (this.technicalPercentile < 65 && this.technicalPercentile >= 40) {
				this.technicalLevel = Level.MEDIUM;
			} else {
				this.technicalLevel = Level.LOW;
			}
		}
	}


}