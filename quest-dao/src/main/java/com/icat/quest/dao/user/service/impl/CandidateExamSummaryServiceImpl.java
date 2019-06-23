package com.icat.quest.dao.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icat.quest.common.vo.CandidateExamSummaryCompositeVo;
import com.icat.quest.common.vo.CandidateExamSummaryVo;
import com.icat.quest.common.vo.CandidateExamVo;
import com.icat.quest.dao.CandidateExamSummaryDao;
import com.icat.quest.dao.SicoSectionMarksDao;
import com.icat.quest.dao.user.service.CandidateExamSummaryService;
import com.icat.quest.model.CandidateExamSummary;
import com.icat.quest.model.SicoSectionMarks;

@Service
public class CandidateExamSummaryServiceImpl implements CandidateExamSummaryService {

	@Autowired
	private CandidateExamSummaryDao candidateExamSummaryDao;
	@Autowired
	private SicoSectionMarksDao sicoSectionMarksDao;
	
	public enum AnswerType {
		A,
		B,
		C,
		D,
		E
	}


	@Override
	public void saveUpdateCandidateExamSummary(CandidateExamVo candidateExamVo) {
		if (candidateExamVo.getExamSectionHasQuestionId() != null && candidateExamVo.getCandidateId() != null
				&& candidateExamVo.getUserAnswer() != null) {
			Map<String, Object> paramsKayAndValues = new HashMap<String, Object>();
			paramsKayAndValues.put("_1_TCHTCId", candidateExamVo.getTestConductorHasTestCodeId());
			paramsKayAndValues.put("_2_questionId", candidateExamVo.getExamSectionHasQuestionId());

			CandidateExamSummary candidateExamSummary = candidateExamSummaryDao
					.findEntityByParameter(CandidateExamSummaryDao.findByTCHTCIdAndQuestionId, paramsKayAndValues);
			CandidateExamSummaryVo candidateExamSummaryVo = getCandidateExamSummaryVo(candidateExamVo);
			
			if (candidateExamSummary == null) {
				if (candidateExamSummaryVo != null) {
					createCandidateExamSummary(candidateExamSummaryVo);
				}
			} else {

				candidateExamSummary.setLastModified(new Date());
				candidateExamSummary.setLastModifiedBy(new Long(candidateExamSummaryVo.getCandidateId()));
				candidateExamSummary.setAnswerFlag(candidateExamSummaryVo.getAnswerFlag());
				candidateExamSummary.setActive(candidateExamVo.getActive());
				candidateExamSummaryDao.update(candidateExamSummary);
			}
		
		}
	}

	public Long createCandidateExamSummary(CandidateExamSummaryVo candidateExamSummaryVo) {
		CandidateExamSummary candidateExamSummary = new CandidateExamSummary();
		candidateExamSummary.setActive(true);
		candidateExamSummary.setLastModified(new Date());
		candidateExamSummary.setCreated(new Date());
		candidateExamSummary.setLastModifiedBy(new Long(candidateExamSummaryVo.getCandidateId()));
		candidateExamSummary.setCreatedBy(new Long(candidateExamSummaryVo.getCandidateId()));
		candidateExamSummary.setAnswerFlag(candidateExamSummaryVo.getAnswerFlag());
		candidateExamSummary.setCandidateId(candidateExamSummaryVo.getCandidateId());
		candidateExamSummary.setExamId(candidateExamSummaryVo.getExamId());
		candidateExamSummary.setExamSectionId(candidateExamSummaryVo.getExamSectionId());
		candidateExamSummary.setExamSectionHasQuestionId(candidateExamSummaryVo.getExamSectionHasQuestionId());
		candidateExamSummary.setQuestionMarks(candidateExamSummaryVo.getQuestionMarks());
		candidateExamSummary.setQuestionNumber(candidateExamSummaryVo.getQuestionNumber());
		candidateExamSummary.setQuestionNegativeMarks(candidateExamSummaryVo.getQuestionNegativeMarks() != null ? candidateExamSummaryVo.getQuestionNegativeMarks() : 0f);
		candidateExamSummary.setQuestionCategoryId(candidateExamSummaryVo.getQuestionCategoryId());
		candidateExamSummary.setSicoFlag(candidateExamSummaryVo.getSicoFlag());
		candidateExamSummary.setTestConductorHasTestCodeId(candidateExamSummaryVo.getTestConductorHasTestCodeId());

		return candidateExamSummaryDao.create(candidateExamSummary);
	}

	private CandidateExamSummaryVo getCandidateExamSummaryVo(CandidateExamVo candidateExamVo) {
		CandidateExamSummaryVo candidateExamSummaryVo = new CandidateExamSummaryVo();
		List<CandidateExamSummaryCompositeVo> candidateExamSummaryCompositeVoList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		Gson gson = builder.create();
		Map<String, Object> paramsKeyAndValues = new HashMap<String, Object>();
		paramsKeyAndValues.put("_1_examSectionHasQuestionId", candidateExamVo.getExamSectionHasQuestionId());

		List<Object[]> candidateExamSummaryCompositeArrayList = candidateExamSummaryDao
				.listCompositeSqlQuery(CandidateExamSummaryDao.findCompositeDetailByQuestionId, paramsKeyAndValues);

		try {
			List<Object> candidateExamSummaryCompositeObjectList = candidateExamSummaryDao
					.convertToObjectList(candidateExamSummaryCompositeArrayList, new CandidateExamSummaryCompositeVo(), null, null);
			candidateExamSummaryCompositeVoList = mapper.readValue(gson.toJson(candidateExamSummaryCompositeObjectList),
					new TypeReference<List<CandidateExamSummaryCompositeVo>>() {
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (candidateExamSummaryCompositeVoList == null || candidateExamSummaryCompositeVoList.isEmpty())
			return null;
		CandidateExamSummaryCompositeVo candidateExamSummaryCompositeVo = candidateExamSummaryCompositeVoList.get(0);
		candidateExamSummaryVo.setExamId(candidateExamSummaryCompositeVo.getExamId());
		candidateExamSummaryVo.setExamSectionId(candidateExamSummaryCompositeVo.getExamSectionId());
		candidateExamSummaryVo.setQuestionCategoryId(candidateExamSummaryCompositeVo.getQuestionCategoryId());
		candidateExamSummaryVo.setQuestionNumber(candidateExamSummaryCompositeVo.getQuestionNumber());
		candidateExamSummaryVo.setTestConductorHasTestCodeId(candidateExamVo.getTestConductorHasTestCodeId());
		candidateExamSummaryVo.setSicoFlag(candidateExamVo.getSicoFlag());
		candidateExamSummaryVo.setQuestionNegativeMarks(candidateExamSummaryCompositeVo.getNegativeMark());
		
		if (candidateExamSummaryCompositeVo.getCorrectAnswer().equals(candidateExamVo.getUserAnswer())) {
			candidateExamSummaryVo.setAnswerFlag(true);
		} else {
			candidateExamSummaryVo.setAnswerFlag(false);
		}
		candidateExamSummaryVo.setCandidateId(candidateExamVo.getCandidateId());
		candidateExamSummaryVo.setExamSectionHasQuestionId(candidateExamVo.getExamSectionHasQuestionId());
		if(candidateExamVo.getSicoFlag()) {
			candidateExamSummaryVo.setQuestionNegativeMarks(0f);
			candidateExamSummaryVo.setAnswerFlag(true);
			SicoSectionMarks sicoSectionMarks = sicoSectionMarksDao.read(candidateExamVo.getExamSectionHasQuestionId());
			switch (AnswerType.valueOf(candidateExamVo.getUserAnswer())) {
			case A:
				candidateExamSummaryVo.setQuestionMarks(sicoSectionMarks.getMarksA());
				break;
			case B:
				candidateExamSummaryVo.setQuestionMarks(sicoSectionMarks.getMarksB());
				break;
			case C:
				candidateExamSummaryVo.setQuestionMarks(sicoSectionMarks.getMarksC());
				break;
			case D:
				candidateExamSummaryVo.setQuestionMarks(sicoSectionMarks.getMarksD());
				break;
			case E:
				candidateExamSummaryVo.setQuestionMarks(sicoSectionMarks.getMarksE());
				break;

			default:
				break;
			}
		} else {
			candidateExamSummaryVo.setQuestionMarks(candidateExamSummaryCompositeVo.getMarks());
		}
		return candidateExamSummaryVo;
	}

}
