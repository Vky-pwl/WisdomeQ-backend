package com.icat.quest.dao.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.vo.AnswerType;
import com.icat.quest.common.vo.CandidateQuestionStatusVo;
import com.icat.quest.dao.CandidateQuestionStatusDao;
import com.icat.quest.dao.user.service.CandidateQuestionStatusService;
import com.icat.quest.model.CandidateQuestionStatus;

@Service
public class CandidateQuestionStatusServiceImpl implements CandidateQuestionStatusService {

	@Autowired
	private CandidateQuestionStatusDao candidateQuestionStatusDao;

	@Override
	public void update(CandidateQuestionStatusVo candidateQuestionStatusVo) {
		Map<String, Object> params = new HashMap<>();
		params.put("_2_questionId", candidateQuestionStatusVo.getExamSectionHasQuestionId());
		params.put("_1_testConductorHasTestCodeId", candidateQuestionStatusVo.getTestConductorHasTestCodeId());
		CandidateQuestionStatus candidateQuestionStatus = candidateQuestionStatusDao
				.findEntityByParameter(CandidateQuestionStatusDao.findByTCHTCIDAndQuesId, params);
		if (candidateQuestionStatus != null) {
			candidateQuestionStatus.setAnswered(candidateQuestionStatusVo.getAnswered());
			candidateQuestionStatus.setVisited(candidateQuestionStatusVo.getVisited());
			candidateQuestionStatus.setMarkedForReview(candidateQuestionStatusVo.getMarkedForReview());
			candidateQuestionStatus
					.setAnsweredAndMarkedForReview(candidateQuestionStatusVo.getAnsweredAndMarkedForReview());
			candidateQuestionStatus.setLastModified(new Date());
			candidateQuestionStatus.setLastModifiedBy(new Long(candidateQuestionStatusVo.getCandidateId()));
			candidateQuestionStatus.setUserAnswer(candidateQuestionStatusVo.getUserAnswer() != null ? AnswerType.valueOf(candidateQuestionStatusVo.getUserAnswer()): null);
			candidateQuestionStatusDao.update(candidateQuestionStatus);
		} else {
			candidateQuestionStatus = transfromVToP(candidateQuestionStatusVo);
			candidateQuestionStatusDao.create(candidateQuestionStatus);
		}
	}

	@Override
	public List<CandidateQuestionStatusVo> getList(List<Integer> idList, Integer candidateId, Integer testConductorHasTestCodeId) {
		List<CandidateQuestionStatusVo> questionStatusVos = new ArrayList<>();
		Map<String, Object> params = new HashMap<>();
		params.put("_1_testConductorHasTestCodeId", testConductorHasTestCodeId);
		params.put("_2_qIdList", idList);
		List<CandidateQuestionStatus> candidateQuestionStatusList = candidateQuestionStatusDao
				.listEntityByParameter(CandidateQuestionStatusDao.findAllByTCHTCIDAndQIdList, params, null, null);

		if (candidateQuestionStatusList != null) {
			Iterator<CandidateQuestionStatus> it = candidateQuestionStatusList.iterator();
			while (it.hasNext()) {
				CandidateQuestionStatus candidateQuestionStatus = it.next();
				questionStatusVos.add(transfromPToV(candidateQuestionStatus));
				idList.remove(candidateQuestionStatus.getExamSectionHasQuestionId());
			}
		}
		if (idList.size() > 0) {
			for (Integer id : idList) {
				questionStatusVos.add(new CandidateQuestionStatusVo(id));
			}
		}
		return questionStatusVos;
	}

	private CandidateQuestionStatus transfromVToP(CandidateQuestionStatusVo candidateQuestionStatusVo) {
		AnswerType userAnswer = candidateQuestionStatusVo.getUserAnswer() != null ? AnswerType.valueOf(candidateQuestionStatusVo.getUserAnswer()) : null;
		return new CandidateQuestionStatus(candidateQuestionStatusVo.getTestConductorHasTestCodeId(),candidateQuestionStatusVo.getExamSectionHasQuestionId(),
				candidateQuestionStatusVo.getCandidateId(), candidateQuestionStatusVo.getVisited(),
				candidateQuestionStatusVo.getMarkedForReview(),
				candidateQuestionStatusVo.getAnsweredAndMarkedForReview(), candidateQuestionStatusVo.getAnswered(),userAnswer,
				new Date(), new Long(candidateQuestionStatusVo.getCandidateId()), new Date(),
				new Long(candidateQuestionStatusVo.getCandidateId()));
	}

	private CandidateQuestionStatusVo transfromPToV(CandidateQuestionStatus candidateQuestionStatus) {

		return new CandidateQuestionStatusVo(candidateQuestionStatus.getTestConductorHasTestCodeId(),candidateQuestionStatus.getExamSectionHasQuestionId(),
				candidateQuestionStatus.getCandidateId(), candidateQuestionStatus.getVisited(),
				candidateQuestionStatus.getMarkedForReview(), candidateQuestionStatus.getAnsweredAndMarkedForReview(),
				candidateQuestionStatus.getAnswered(),candidateQuestionStatus.getUserAnswer() != null ? candidateQuestionStatus.getUserAnswer().name() : null);
	}

}
