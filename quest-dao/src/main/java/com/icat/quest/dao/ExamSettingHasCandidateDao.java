/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.ExamSettingHasCandidate;


public interface ExamSettingHasCandidateDao extends GenericDao<ExamSettingHasCandidate,Integer>{

	public String findAllByExamIdAndUserId = "from com.icat.quest.model.ExamSettingHasCandidate examSettingHasCandidate where examSettingHasCandidate.examId=:_1_examId and examSettingHasCandidate.active=:_2_active and examSettingHasCandidate.candidateId = :_3_candidateId order by examSettingHasCandidateId desc ";
	
	public String findAllByExamIdUserIdAndSettingId = "from com.icat.quest.model.ExamSettingHasCandidate examSettingHasCandidate where examSettingHasCandidate.examId=:_1_examId and examSettingHasCandidate.settingId=:_2_settingId and examSettingHasCandidate.candidateId = :_3_candidateId";
	
}
