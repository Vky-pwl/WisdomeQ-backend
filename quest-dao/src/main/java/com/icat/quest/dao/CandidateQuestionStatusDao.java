/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.CandidateQuestionStatus;


public interface CandidateQuestionStatusDao extends GenericDao<CandidateQuestionStatus,Long>{

	
	public String findByTCHTCIDAndQuesId="from com.icat.quest.model.CandidateQuestionStatus candidateQuestionStatus where candidateQuestionStatus.testConductorHasTestCodeId=:_1_testConductorHasTestCodeId and candidateQuestionStatus.examSectionHasQuestionId=:_2_questionId";

	public String findAllByTCHTCIDAndQIdList="from com.icat.quest.model.CandidateQuestionStatus candidateQuestionStatus where candidateQuestionStatus.testConductorHasTestCodeId=:_1_testConductorHasTestCodeId and candidateQuestionStatus.examSectionHasQuestionId in(:_2_qIdList)";
}
