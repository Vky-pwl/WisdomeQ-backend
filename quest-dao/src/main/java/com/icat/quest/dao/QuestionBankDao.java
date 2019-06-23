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
	
	public String findByFilterCriteria = "select distinct questionId from QuestionBank Q, QuestionCategory QC\n" + 
			" where\n" + 
			" (case when :_1_QuestionSubCategoryId is null then 1=1 else QC.QuestionCategoryId=:_1_QuestionSubCategoryId end) and\n" + 
			" (case when :_2_QuestionCategoryId is null then 1=1 else QC.parentQuestionCategoryId=:_2_QuestionCategoryId end) and \n" +
			" (case when :_4_questionExamType is null then 1=1 else Q.questionExamType=:_4_questionExamType end) and \n" +
			" (case when :_3_searchKey is null then 1=1 else UPPER(cast(Q.questionStatment as char)) like UPPER(:_3_searchKey) end)" ;
		
}
