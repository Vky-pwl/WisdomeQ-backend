package com.icat.quest.common.utils;

public class UniqueCodeGeneratorImpl {

	public static String getCollegeCode(String collegeName,String collegeId) {
		String collegeCode = "";
		if(collegeName.length()>6) {
			collegeCode = collegeName.substring(0, 6).toUpperCase();
		}else {
			collegeCode = collegeName;
		}
		for(int i =0 ;i < (16-(collegeCode.length()+collegeId.length()));i++) {
			collegeCode+="0";
		}
		return collegeCode+collegeId;
	}
	public static String getExamCode(String examName,String examId) {
		String examCode = "";
		if(examName.length()>6) {
			examCode = examName.substring(0, 6).toUpperCase();
		}else {
			examCode = examName;
		}
		for(int i =0 ;i < (16-(examCode.length()+examId.length()));i++) {
			examCode+="0";
		}
		return examCode+examId;
	}
	
	public static String getTestConductorCode(String testConductorName,String testConductorId, String examId) {
		String testConductorCode = testConductorId;
		if(testConductorName.length()>4) {
			testConductorCode = testConductorName.substring(0, 4).toUpperCase();
		}else {
		testConductorCode = testConductorName;	
		}
			int length = 20-(testConductorCode.length()+testConductorCode.length()+examId.length());
		for(int i =0 ;i < length;i++) {
			testConductorCode+="0";
		}
		return testConductorCode+examId;
	}
	
	public static String getExamCode(String testConductorHasLicenseId, String examId,String testConductorHasTestCodeId) {
		String examCode = testConductorHasLicenseId+"TC"+examId+"EC";
		int length = 25-(examCode.length()+testConductorHasTestCodeId.length());
		for(int i =0 ;i < length;i++) {
			examCode+="0";
		}
		return examCode+testConductorHasTestCodeId;
	}
}		
