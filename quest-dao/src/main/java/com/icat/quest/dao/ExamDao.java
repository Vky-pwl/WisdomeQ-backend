/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.Exam;


public interface ExamDao extends GenericDao<Exam,Integer>{

	
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.Exam exam where "
			+ "exam.active in(:_1_active) and exam.publish in(:_3_publish) and exam.examName like :_2_examName order by exam.examId desc";
	public String findAllBySearchKeyWithFilterAndExamIdList ="from com.icat.quest.model.Exam exam where "
			+ "exam.active in(:_1_active) and exam.publish in(:_3_publish) and exam.examName like :_2_examName and exam.examId in(:_4_examIdList) order by exam.examId desc";
	
	public String findExamIdsByTcId = "select distinct examId from TestConductorLicense where testConductorId=:_1_testConductorId and active = true";
	
}
