package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.CandidateExamSummary;

public interface CandidateExamSummaryDao extends GenericDao<CandidateExamSummary, Long>{
	
	public static String findCompositeDetailByQuestionId = "select distinct ES.examId, ES.examSectionId, ESHQ.questionNumber,ESHQ.marks, ESHQ.negativeMark, QB.questionCategoryId, QB.correctAnswer from ExamSectionHasQuestion ESHQ\n" + 
			"inner join QuestionBank QB on QB.questionId = ESHQ.questionId\n" + 
			"inner join ExamSection ES on ES.examSectionId = ESHQ.examSectionId\n" + 
			"where ESHQ.examSectionHasQuestionId =:_1_examSectionHasQuestionId";

	public static String findByTCHTCIdAndQuestionId = "from com.icat.quest.model.CandidateExamSummary candidateExamSummary where candidateExamSummary.testConductorHasTestCodeId=:_1_TCHTCId and candidateExamSummary.examSectionHasQuestionId=:_2_questionId ";
	public static String findByTCHTCID = "from com.icat.quest.model.CandidateExamSummary candidateExamSummary where candidateExamSummary.testConductorHasTestCodeId=:_1_TCHTCId and active = true";
	
	public static String findAllByExamId = "from com.icat.quest.model.CandidateExamSummary candidateExamSummary where candidateExamSummary.examId=:_2_examId and active = true";
	
	public static String findAllByExamIdAndTCIds = "from com.icat.quest.model.CandidateExamSummary candidateExamSummary where candidateExamSummary.examId=:_2_examId and testConductorHasTestCodeId in(:_1_TCHTCId) and active = true";
		
	public  String findExamResultDescByExamIdAndCanId = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName, \n" + 
			"E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId,  \n" + 
			"ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion, \n" + 
			"EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName, C.contactEmail, C.contactNumber , ES.sectionName, TCHTC.testConductorHasTestCodeId  \n" + 
			"from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC\n" + 
			"where TCHTC.testConductorHasTestCodeId=:_1_TCHTCId and E.examId = TCHTC.examId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId\n" + 
			"and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId\n" + 
			"and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and TCHTC.status='COMPLETED'";
	
	public String findExamResultDescByExamIDAndTCHTCIds = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName,\n" + 
			"E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId,\n" + 
			"ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion,\n" + 
			"EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName, C.contactEmail, C.contactNumber , ES.sectionName, TCHTC.testConductorHasTestCodeId \n" + 
			"from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC\n" + 
			"where TCHTC.examId = :_2_examId and TCHTC.testConductorHasTestCodeId in(:_1_TCHTCId) and E.examId = TCHTC.examId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId\n" + 
			"and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId\n" + 
			"and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and TCHTC.status='COMPLETED'";

	public  String findExamResultDescByExamId = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName,\n" + 
			"			E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId,\n" + 
			"			ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion,\n" + 
			"            EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName, C.contactEmail, C.contactNumber , ES.sectionName, TCHTC.testConductorHasTestCodeId \n" + 
			"			from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC\n" + 
			"			where TCHTC.examId = :_2_examId and E.examId = TCHTC.examId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId\n" + 
			"			and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId\n" + 
			"            and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and TCHTC.status='COMPLETED'";
	
	public  String findExamResultDescByExamIdAndCollegeId = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName, \n" + 
			"						E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId, \n" + 
			"						ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion, \n" + 
			"			            EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName , C.contactEmail, C.contactNumber , ES.sectionName, TCHTC.testConductorHasTestCodeId \n" + 
			"						from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC, College Col \n" + 
			"						where TCHTC.examId = :_2_examId and E.examId = TCHTC.examId and Col.collegeId = :_3_collegeId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId \n" + 
			"						and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId \n" + 
			"			            and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and Col.collegeId = C.collegeId and TCHTC.status='COMPLETED'";
	public  String findExamResultDescByExamIdAndCollegeIdAndSpec = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName,  \n" + 
			"									E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId,  \n" + 
			"									ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion,  \n" + 
			"						            EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName, C.contactEmail, C.contactNumber , ES.sectionName , TCHTC.testConductorHasTestCodeId  \n" + 
			"									from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC, College Col, Specialization SPL  \n" + 
			"									where TCHTC.examId = :_2_examId and E.examId = TCHTC.examId and Col.collegeId = :_3_collegeId and SPL.specializationId=:_4_specializationId and SPL.specializationId=C.specializationId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId  \n" + 
			"									and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId  \n" + 
			"						            and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and Col.collegeId = C.collegeId and TCHTC.status='COMPLETED'";

	public  String findExamResultDescByExamIdAndSpec = "select distinct C.userId candidateId, C.firstName candidateFirstName, C.lastName candidateLastName, \n" + 
			"						E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, ES.examSectionId, \n" + 
			"						ESQC.questionCategoryName examSectionName, ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion, \n" + 
			"			            EC.examCategoryName, TCHTC.examStartDate, TCHTC.testCode,QB.questionCategoryId, QC.questionCategoryName , C.contactEmail, C.contactNumber , ES.sectionName , TCHTC.testConductorHasTestCodeId\n" + 
			"						from Exam E, ExamCategory EC, ExamSection ES, QuestionCategory ESQC, TestConductorHasTestCode TCHTC, User C, ExamSectionHasQuestion ESHQ, QuestionBank QB, QuestionCategory QC,Specialization SPL \n" + 
			"						where TCHTC.examId = :_2_examId and E.examId = TCHTC.examId and SPL.specializationId=:_4_specializationId and SPL.specializationId=C.specializationId and C.userId = TCHTC.userId and EC.examCategoryId = E.examCategoryId \n" + 
			"						and ES.examId = E.examId and ES.questionCategoryId= ESQC.questionCategoryId and ESHQ.examSectionId = ES.examSectionId and QB.questionId = ESHQ.questionId \n" + 
			"			            and QC.questionCategoryId = QB.questionCategoryId and ES.active = true and ESHQ.active = true and TCHTC.status='COMPLETED'";

	
	
	public String findAllUnAttemptExamQuestionIdByTCHTCId = "select ESHQ.examSectionId, ESHQ.examSectionHasQuestionId, ES.durationInSeconds from ExamSectionHasQuestion ESHQ, ExamSection ES \n" + 
			"where ES.examId=:examId and ES.examSectionId = ESHQ.examSectionId and ESHQ.examSectionHasQuestionId not in\n" + 
			"(select examSectionHasQuestionId from CandidateExamSummary where testConductorHasTestCodeId = :TCHTCId)";
	
	
	public String findAllQuesIdAttendedByCandInLastHour = "select distinct ExamSectionHasQuestionId from ExamSectionHasQuestion ESHQ where examSectionId in\n" + 
			"(select distinct examSectionId from CandidateExamSummary where lastModified between :_1_prevDate and  :_2_curDate)";
	

	
	
	public String findAllResultByExamId = "SELECT DISTINCT C.userId candidateId,CES.answerFlag, CES.questionMarks, CES.questionNegativeMarks,\n" + 
			"TCHTC.testConductorHasTestCodeId, TCHTC.userTotalMarks,TCHTC.totalAttempts,TCHTC.examStartDate, TCHTC.testCode,\n" + 
			"E.examId, E.examName, E.questionCount totalQuestion, E.totalMarks, EC.examCategoryName,\n" + 
			"ES.examSectionId, ES.sectionName,ES.totalMarks sectionTotalMarks, ES.totalQuestion sectionTotalQuestion, CES.questionCategoryId, CES.examSectionHasQuestionId\n" + 
			"FROM CandidateExamSummary CES \n" + 
			"INNER JOIN TestConductorHasTestCode TCHTC ON TCHTC.testConductorHasTestCodeId = CES.testConductorHasTestCodeId\n" + 
			"INNER JOIN User C ON  TCHTC.userId = C.userId\n" + 
			"INNER JOIN Exam E ON E.examId = TCHTC.examId \n" + 
			"INNER JOIN ExamCategory EC ON EC.examCategoryId= E.examCategoryId\n" + 
			"INNER JOIN ExamSection ES ON CES.examSectionId = ES.examSectionId "+ 
			"where E.examId = :_1_examId AND TCHTC.status='COMPLETED' AND TCHTC.active = true AND CES.active = true  \n" + 
			"AND (CASE WHEN :_2_collegeId is NULL THEN 1=1 ELSE C.collegeId=:_2_collegeId END)\n" + 
			"AND (CASE WHEN :_3_specializationId is NULL THEN 1=1 ELSE C.specializationId=:_3_specializationId END) "+
			"AND (CASE WHEN :_4_startDate is NULL THEN 1=1 ELSE TCHTC.lastModified between :_4_startDate and :_5_endDate  END)";
	
}
