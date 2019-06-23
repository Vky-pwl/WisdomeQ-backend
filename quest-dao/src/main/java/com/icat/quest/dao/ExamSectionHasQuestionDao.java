/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExamSectionHasQuestion;


public interface ExamSectionHasQuestionDao extends GenericDao<ExamSectionHasQuestion,Integer>{

	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where "
			+ "examSectionHasQuestion.active =:_1_active and examSectionHasQuestion.examSectionHasQuestionName like :_2_examSectionHasQuestionName order by examSectionHasQuestion.examSectionHasQuestionId desc";
	public String findAllByFilter="from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where examSectionHasQuestion.active =:_1_active order by examSectionHasQuestion.examSectionHasQuestionId desc";
	public String findAllBySearchKey="from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where examSectionHasQuestion.examSectionHasQuestionName like :_2_examSectionHasQuestionName order by examSectionHasQuestion.examSectionHasQuestionId desc";
	public String findAll = "from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion order by examSectionHasQuestion.examSectionHasQuestionId desc";
	public String findAllBySectionId = "from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where examSectionHasQuestion.examSection.examSectionId= :_1_examSectionId and examSectionHasQuestion.active = true";
	public String findAllByExamId = "select new com.icat.quest.model.ExamSectionHasQuestion(examSectionHasQuestion.examSectionHasQuestionId, examSectionHasQuestion.questionBank, examSectionHasQuestion.questionNumber,examSectionHasQuestion.marks, examSectionHasQuestion.negativeMark) from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where examSectionHasQuestion.examSection.exam.examId= :_1_examId and examSectionHasQuestion.active = true and examSectionHasQuestion.examSection.active=true";
	
	public String findAllShortResultBySectionId = "select new com.icat.quest.model.ExamSectionHasQuestion(examSectionHasQuestion.examSectionHasQuestionId,examSectionHasQuestion.questionNumber, examSectionHasQuestion.marks, examSectionHasQuestion.active) from com.icat.quest.model.ExamSectionHasQuestion examSectionHasQuestion where examSectionHasQuestion.examSection.examSectionId= :_1_examSectionId and examSectionHasQuestion.active = true";
	
	public String findESHIDSByUserId = "select distinct ESHQ.examSectionHasQuestionId from ExamSectionHasQuestion ESHQ\n" + 
			"inner join ExamSection ES on ES.examSectionId = ESHQ.examSectionId\n" + 
			"inner join TestConductorLicense TCL on TCL.examId = ES.examId\n" + 
			"where TCL.testConductorId=:_1_testConductorId and ESHQ.active = true";
	public String findAllIdByExamSectionId = "select distinct examSectionHasQuestionId from ExamSectionHasQuestion where examSectionId=:_1_examSectionId and active=true";
		
}
