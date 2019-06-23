/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExplanationDescription;

/**
 * @author satyendra 
 *
 */
public interface ExplanationDescriptionDao extends GenericDao<ExplanationDescription,Integer>{

	
	public String findAll = "from com.icat.quest.model.ExplanationDescription explanationDescription order by explanationDescription.explanationDescriptionId desc";
	public String findAllByQuestionId = "from com.icat.quest.model.ExplanationDescription explanationDescription where explanationDescription.questionId=:_1_questionId";
		
}
