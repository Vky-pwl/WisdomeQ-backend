package com.icat.quest.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EAIResultVo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 867865156439818097L;
	private String designation;
	private String level;
	private String jobEligibility;
	private static List<EAIResultVo> between40To55 = new ArrayList<>();
	private static List<EAIResultVo> between55To70 = new ArrayList<>();
	private static List<EAIResultVo> between70To100 = new ArrayList<>();
	private static List<EAIResultVo> below40 = new ArrayList<>();
	
	static {
		below40.add(new EAIResultVo("Design Engineer","Poor","Not eligible for core companies"));
		below40.add(new EAIResultVo("IT Services","Poor","Not eligible for IT Service based companies"));
		below40.add(new EAIResultVo("IT product","Poor","Not eligible for IT Product based companies"));
	
		between40To55.add(new EAIResultVo("Design Engineer","Satisfaction","You are eligible for few corejobs but your academicpercentage plays a major Role"));
		between40To55.add(new EAIResultVo("IT Services","Satisfaction","You are eligible for few CMMI level 1 CMMI level 2 IT companies"));
		between40To55.add(new EAIResultVo("IT product","Satisfaction","You are eligible for few ITproduct companies that is CMMIevel 1 and 2 certified"));
	
		between55To70.add(new EAIResultVo("Design Engineer","Good","You are eligible for only somecore jobs"));
		between55To70.add(new EAIResultVo("IT Services","Good","You are eligible for some ITcompanies that is CMMI level 1,2and 3 certified"));
		between55To70.add(new EAIResultVo("IT product","Good","You are eligible for some ITproduct companies that is CMMIlevel 1,2 and 3 certified"));
	
		between70To100.add(new EAIResultVo("Design Engineer","Best","You are eligible for all Corejobs including MNC's"));
		between70To100.add(new EAIResultVo("IT Services","Best","You are eligible for all ITcompanies that is CMMI level1,2,3,4and 5 certified includingthe top MNC's"));
		between70To100.add(new EAIResultVo("IT product","Best","You are eligible for all ITproduct companies that is CMMIlevel 1,2,3,4and 5 certifiedincluding the top MNC's"));
	
	}

	public EAIResultVo(String designation, String level, String jobEligibility) {
		super();
		this.designation = designation;
		this.level = level;
		this.jobEligibility = jobEligibility;
	}

	public static List<EAIResultVo> getEAIResult(Float percentile, Boolean belowAverage){
		if(belowAverage) {
			return below40;
		}
		if(percentile>=40 && percentile<55) {
			return between40To55;
		} else if(percentile>=55 && percentile<70) {
			return between55To70;
		}else if(percentile>=70 && percentile<100) {
			return between70To100;
		} else {
			return below40;
		}
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getJobEligibility() {
		return jobEligibility;
	}

	public void setJobEligibility(String jobEligibility) {
		this.jobEligibility = jobEligibility;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
