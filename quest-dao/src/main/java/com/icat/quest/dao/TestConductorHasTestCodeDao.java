package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.TestConductorHasTestCode;

public interface TestConductorHasTestCodeDao extends GenericDao<TestConductorHasTestCode, Integer>{

	public  String listByTestConductorLicenseId = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode   where testConductorHasTestCode.testConductorLicense.testConductorLicenseId =:_1_testConductorLicenseId and testConductorHasTestCode.active= true order by testConductorHasTestCode.testConductorHasTestCodeId desc";
	public  String listUserIdByExamIdAndUserList = "select distinct userId from TestConductorHasTestCode where examId =:_1_examId and userId in(:_2_userIds) and status != 'COMPLETED'";
	
	public String findByUserIdAndExamId = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode   where testConductorHasTestCode.user.userId = :_1_candidateId and testConductorHasTestCode.exam.examId = :_2_examId  and testConductorHasTestCode.attended = false";

	public  String findById = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode   where testConductorHasTestCode.testConductorHasTestCodeId =:_1_TCHTCID";
	
	public  String findAllByUserIdAndExamId = "select questionMarks, answerFlag, questionNegativeMarks from CandidateExamSummary where candidateId = :_1_candidateId and examId=:_2_examId";

	public  String listResultByExamId = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode   where testConductorHasTestCode.exam.examId =:_1_examId and testConductorHasTestCode.active= true and testConductorHasTestCode.attended=true order by testConductorHasTestCode.testConductorHasTestCodeId desc";
	
	public  String listExamByCandidateId = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode where testConductorHasTestCode.user.userId =:_1_userId and testConductorHasTestCode.active =true and testConductorHasTestCode.exam.active = true and testConductorHasTestCode.exam.publish=true order by testConductorHasTestCode.exam.examId desc";
	
	public  String listExamByCandidateIdByAttended = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode where testConductorHasTestCode.user.userId =:_1_userId and testConductorHasTestCode.attended = :_2_attended and testConductorHasTestCode.active =true and testConductorHasTestCode.exam.active = true and testConductorHasTestCode.exam.publish=true order by testConductorHasTestCode.exam.examId desc";

	public String findAll = "from com.icat.quest.model.TestConductorHasTestCode  testConductorHasTestCode where testConductorHasTestCode.attended = true and testConductorHasTestCode.active =true and testConductorHasTestCode.exam.active = true ";
	
	public String findAllByUrlKey = "from com.icat.quest.model.TestConductorHasTestCode  testConductorHasTestCode where testConductorHasTestCode.tinyKey = :_1_urlKey and testConductorHasTestCode.active =true ";
	
	public String findAllIdAndAttendeByCandidateId = "select distinct testConductorHasTestCodeId, attended  from TestConductorHasTestCode where userId =:_1_candidateId and active = true";

	public String updateRemainingTimeByUserIdAndExamId = "update TestConductorHasTestCode set remainingTime = :_1_remainingTime and sectionRemainingTime = :_4_sectionRemainingTime and currentSectionId=:_5_currentSectionId and currentQuestionId=:_6_questionId and currentQuestionStatus = 'COMPLETED' where examId = :_2_examId and userId = :_3_userId";
	
	public  String findByUserId = "from com.icat.quest.model.TestConductorHasTestCode testConductorHasTestCode   where testConductorHasTestCode.user.userId =:_1_userId and testConductorHasTestCode.testConductorLicense.testConductor.testConductorId=:_2_testConductorId and testConductorHasTestCode.active= true";

	public String findCurrentStatus = "select ES.examSectionId currentSectionId, ESHQ.examSectionHasQuestionId currentQuestionId, ES.durationInSeconds currentSectionRemaingTime\n" + 
			"			from ExamSection ES, ExamSectionHasQuestion ESHQ where ES.examId=:_1_examId and ES.sequence=1 and ESHQ.questionNumber=1 and ESHQ.examSectionId = ES.examSectionId and ES.active = true and ESHQ.active = true";

	public String findAllExamAttempCount = "select E.examId, E.examName, count(TCHTC.userId) from TestConductorHasTestCode TCHTC, Exam E\n" + 
			"where TCHTC.active = true and TCHTC.attended = true and TCHTC.examId = E.examId group by E.examId";
	public String findAllExamAttempCountBySpeId = "select E.examId, E.examName, count(TCHTC.userId) from TestConductorHasTestCode TCHTC, Exam E,User C, Specialization S \n" + 
			"where TCHTC.active = true and TCHTC.attended = true and TCHTC.examId = E.examId and TCHTC.userId = C.userId and\n" + 
			"C.specializationId = S.specializationId and S.specializationId=:_2_specializationId group by E.examId";
	public String findAllExamAttempCountByCollId = "select E.examId, E.examName, count(TCHTC.userId) from TestConductorHasTestCode TCHTC, Exam E,User C, College Col\n" + 
			"where TCHTC.active = true and TCHTC.attended = true and TCHTC.examId = E.examId and TCHTC.userId = C.userId and\n" + 
			"Col.collegeId = C.collegeId and Col.collegeId = :_1_collegeId group by E.examId";
	public String findAllExamAttempCountBySpcAndColl = "select E.examId, E.examName, count(TCHTC.userId) from TestConductorHasTestCode TCHTC, Exam E,User C, College Col, Specialization S \n" + 
			"where TCHTC.active = true and TCHTC.attended = true and TCHTC.examId = E.examId and TCHTC.userId = C.userId and\n" + 
			"Col.collegeId = C.collegeId and C.specializationId = S.specializationId and Col.collegeId = :_1_collegeId and S.specializationId=:_2_specializationId group by E.examId";

	public String findAllIdByCandId= "Select distinct testConductorHasTestCodeId from TestConductorHasTestCode TC where TC.userId = :_1_userId";
	public String findAllIdByCandIdCollIdAndSpecId= "Select distinct testConductorHasTestCodeId from TestConductorHasTestCode TC, User C, College Col, Specialization Spl where TC.userId = :_candidateId and C.userId = TC.userId and C.collegeId = Col.collegeId and C.specializationId = Spl.specializationId and Spl.specializationId=:_2_specializationId and Col.collegeId = :_3_collegeId";
	public String findAllIdByCandIdCollId= "Select distinct testConductorHasTestCodeId from TestConductorHasTestCode TC, User C, College Col where TC.userId = :_candidateId and C.userId = TC.userId and C.collegeId = Col.collegeId and Col.collegeId = :_3_collegeId";
	public String findAllIdByCandIdAndSpecId= "Select distinct testConductorHasTestCodeId from TestConductorHasTestCode TC, User C, Specialization Spl where TC.userId = :_candidateId and C.userId = TC.userId and C.specializationId = Spl.specializationId and Spl.specializationId=:_2_specializationId";

	public String findUserMarks = "select distinct testConductorHasTestCodeId, userTotalMarks, 0 rank from TestConductorHasTestCode TC, User U, Specialization SP\n" + 
			" where examId=:_2_examId AND  status='COMPLETED'\n" + 
			" AND (CASE WHEN :_1_collegeId is null THEN 1=1 ELSE U.collegeId=:_1_collegeId and U.userId = TC.userId END)\n" + 
			" AND (CASE WHEN :_3_specializationId is null THEN 1=1 ELSE SP.specializationId=:_3_specializationId AND U.userId = TC.userId\n" + 
			" AND SP.specializationId=U.specializationId END) "+
			"AND (CASE WHEN :_4_startDate is NULL THEN 1=1 ELSE TC.lastModified between :_4_startDate and :_5_endDate  END) order by userTotalMarks desc";

}
