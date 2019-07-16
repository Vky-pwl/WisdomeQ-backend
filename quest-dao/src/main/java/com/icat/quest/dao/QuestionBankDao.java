/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.QuestionBank;


public interface QuestionBankDao extends GenericDao<QuestionBank,Integer>{

	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.QuestionBank questionBank where "
			+ "questionBank.active =:_1_active and questionBank.questionStatment like :_2_questionBankName order by questionBank.questionId desc";
	public String findAllByFilter="from com.icat.quest.model.QuestionBank questionBank where questionBank.active =:_1_active order by questionBank.questionId desc";
	public String findAllBySearchKey="from com.icat.quest.model.QuestionBank questionBank where questionBank.questionStatment like :_2_questionBankName order by questionBank.questionId desc";
	public String findAll = "from com.icat.quest.model.QuestionBank questionBank order by questionBank.questionId desc";
	
	public String findByFilterCriteria = "SELECT DISTINCT questionId FROM QuestionBank Q, QuestionCategory QC" + 
			" WHERE Q.questionCategoryId = QC.questionCategoryId " + 
			"AND (CASE WHEN :_1_QuestionSubCategoryId IS NULL THEN 1=1 ELSE (QC.questionCategoryId=:_1_QuestionSubCategoryId AND QC.parentQuestionCategoryId IS NOT NULL) END) " + 
			"AND (CASE WHEN :_2_QuestionCategoryId IS NULL THEN 1=1 ELSE QC.parentQuestionCategoryId=:_2_QuestionCategoryId END) " +
			"AND (CASE WHEN :_4_questionExamType IS NULL THEN 1=1 ELSE Q.questionExamType=:_4_questionExamType END) " +
			"AND (CASE WHEN :_3_searchKey IS NULL THEN 1=1 ELSE UPPER(CAST(Q.questionStatment AS CHAR)) LIKE UPPER(:_3_searchKey) END) "+
			"ORDER BY Q.questionId DESC" ;
	
		
}
