/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExamSection;

public interface ExamSectionDao extends GenericDao<ExamSection,Integer>{

	
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.ExamSection examSection where "
			+ "examSection.active =:_1_active and examSection.examSectionDescription like :_2_examSectionName order by examSection.examSectionId desc";
	public String findAllByFilter="from com.icat.quest.model.ExamSection examSection where examSection.active =:_1_active order by examSection.examSectionId desc";
	public String findAllBySearchKey="from com.icat.quest.model.ExamSection examSection where examSection.examSectionDescription like :_2_examSectionName order by examSection.examSectionId desc";
	public String findAll = "from com.icat.quest.model.ExamSection examSection order by examSection.examSectionId desc";
	public String findAllByExamId = "from com.icat.quest.model.ExamSection examSection where examSection.exam.examId=:_1_examId and examSection.active = true order by examSection.sequence";
	public String findAllSubSection = "select QC.parentQuestionCategoryId questionCategoryId, QC.parentQuestionCategoryName questionCategoryName, \n" + 
			"QC.questionCategoryId questionSubCategoryId, QC.questionCategoryName questionSubCategoryName , count(QC.questionCategoryId), SUM(ESHQ.marks) totalmarks \n" + 
			"from QuestionBank QB   \n" + 
			"inner join QuestionCategory QC on QC.questionCategoryId = QB.questionCategoryId  \n" + 
			"inner join ExamSectionHasQuestion ESHQ on ESHQ.questionId = QB.questionId \n" + 
			"where ESHQ.examSectionId = :_1_examSectionId and ESHQ.active = true  group by QC.questionCategoryId";
	
	
	public String findExamSectionIdsByTcId = "select distinct ES.examSectionId from TestConductorLicense TCL\n" + 
			"	inner join ExamSection ES on ES.examId = TCL.examId\n" + 
			"	 where TCL.testConductorId=:_1_testConductorId and TCL.active = true";
	
	public String findAllBySearchKeyWithFilterAndESIdList="from com.icat.quest.model.ExamSection examSection where "
			+ "examSection.active =:_1_active and examSection.examSectionDescription like :_2_examSectionName and examSection.examSectionId in(:_3_examSectionIdList) order by examSection.examSectionId desc";
	public String findAllByFilterAndESIdList="from com.icat.quest.model.ExamSection examSection where examSection.active =:_1_active and examSection.examSectionId in(:_3_examSectionIdList) order by examSection.examSectionId desc";
	public String findAllBySearchKeyAndESIdList="from com.icat.quest.model.ExamSection examSection where examSection.examSectionDescription like :_2_examSectionName and examSection.examSectionId in(:_3_examSectionIdList) order by examSection.examSectionId desc";
	public String findAllAndESIdList = "from com.icat.quest.model.ExamSection examSection where examSection.examSectionId in(:_3_examSectionIdList) order by examSection.examSectionId desc";
	
}
