/**
 * 
 */
package com.icat.quest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.common.vo.ExamSettingHasCandidateVo;
import com.icat.quest.dao.ExamSettingDao;
import com.icat.quest.dao.ExamSettingHasCandidateDao;
import com.icat.quest.model.ExamSettingHasCandidate;
import com.icat.quest.service.ExamSettingHasCandidateService;


@Service
public class ExamSettingHasCandidateServiceImpl implements ExamSettingHasCandidateService {

	@Autowired
	private ExamSettingHasCandidateDao examSettingHasCandidateDao;

	@Override
	public Integer craeteExamSettingHasCandidate(ExamSettingHasCandidateVo examSettingHasCandidateVo, Integer userId) throws Exception {

		if(examSettingHasCandidateVo.getSettingId() ==  null || examSettingHasCandidateVo.getExamId() == null || examSettingHasCandidateVo.getCandidateId() == null) {
			throw new Exception("Invalid Input");
		}
		
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		 paramsKayAndValues.put("_1_examId", examSettingHasCandidateVo.getExamId());
		 paramsKayAndValues.put("_2_settingId", examSettingHasCandidateVo.getSettingId());
		 paramsKayAndValues.put("_3_candidateId", examSettingHasCandidateVo.getCandidateId());
			
		ExamSettingHasCandidate examSettingHasCandidate =  examSettingHasCandidateDao.findEntityByParameter(ExamSettingDao.findAllByExamIdAndSettingId, paramsKayAndValues);
		if(examSettingHasCandidate != null) {
			if(!examSettingHasCandidate.getActive()) {
				examSettingHasCandidate.setActive(true);
				examSettingHasCandidate.setLastModified(new Date());
				examSettingHasCandidate.setLastModifiedBy(new Long(userId));
				examSettingHasCandidateDao.update(examSettingHasCandidate);
			}
			return examSettingHasCandidate.getExamSettingHasCandidateId();
		}
		
		 examSettingHasCandidate = new ExamSettingHasCandidate(examSettingHasCandidateVo.getSettingId(),examSettingHasCandidateVo.getExamId(),examSettingHasCandidateVo.getCandidateId(), true, new Date(), new Long(userId),
				new Date(), new Long(userId));
		Integer id = examSettingHasCandidateDao.create(examSettingHasCandidate);
		return id;
	}

	@Override
	public Integer updateExamSettingHasCandidate(ExamSettingHasCandidateVo examSettingHasCandidateVo, Integer userId) throws Exception {
		if (examSettingHasCandidateVo.getExamSettingHasCandidateId() == null)
			throw new Exception("ExamSettingHasCandidateId should not be null");

		ExamSettingHasCandidate examSettingHasCandidate = examSettingHasCandidateDao.read(examSettingHasCandidateVo.getExamSettingHasCandidateId());
		if (examSettingHasCandidate == null)
			throw new Exception("Record not found");

		if (examSettingHasCandidateVo.getActive() != null) {
			examSettingHasCandidate.setActive(examSettingHasCandidateVo.getActive());
		}
		examSettingHasCandidate.setLastModified(new Date());
		examSettingHasCandidate.setLastModifiedBy(new Long(userId));
		examSettingHasCandidateDao.update(examSettingHasCandidate);
		return examSettingHasCandidateVo.getExamSettingHasCandidateId();
	}

	@Override
	public ExamSettingHasCandidateVo readExamSettingHasCandidate(Integer examSettingHasCandidateId, Integer userId) throws Exception {
		ExamSettingHasCandidateVo examSettingHasCandidateVo = new ExamSettingHasCandidateVo();
		if (examSettingHasCandidateId == null)
			throw new Exception("ExamSettingHasCandidateId should not be null");

		ExamSettingHasCandidate examSettingHasCandidate = examSettingHasCandidateDao.read(examSettingHasCandidateId);
		if (examSettingHasCandidate == null)
			throw new Exception("Record not found");

		examSettingHasCandidateVo.setExamSettingHasCandidateId(examSettingHasCandidate.getExamSettingHasCandidateId());
		examSettingHasCandidateVo.setExamId(examSettingHasCandidate.getExamId());
		examSettingHasCandidateVo.setSettingId(examSettingHasCandidate.getSettingId());
		examSettingHasCandidateVo.setCandidateId(examSettingHasCandidate.getCandidateId());
		examSettingHasCandidateVo.setActive(examSettingHasCandidate.getActive());

		return examSettingHasCandidateVo;
	}
	
	@Override
	public Map<String, Object> getListByExamId(Integer examId, Integer userId, Boolean active, int pageNo, int pageSize){
		
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		paramsKayAndValues.put("_2_active", active);
		paramsKayAndValues.put("_3_candidateId", userId);
		int startIndex = (pageNo*pageSize)-pageSize;
		Map<String, Object> responseBody = new HashMap<>();
		if(pageNo != 1) {
		List<ExamSettingHasCandidate> examSettingHasCandidates = examSettingHasCandidateDao.listEntityByParameter(ExamSettingHasCandidateDao.findAllByExamIdAndUserId, paramsKayAndValues, startIndex, pageSize);
		responseBody.put("examSettingHasCandidateVoList", transformExamSettingHasCandidates(examSettingHasCandidates));
		
		} else {
			List<ExamSettingHasCandidate> examSettingHasCandidates = examSettingHasCandidateDao.listEntityByParameter(ExamSettingHasCandidateDao.findAllByExamIdAndUserId, paramsKayAndValues, null, null);
			responseBody.put("count", examSettingHasCandidates.size());
			responseBody.put("examSettingHasCandidateVoList", transformExamSettingHasCandidates(examSettingHasCandidates.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList())));
		}
		return responseBody;
	}
	
	private List<ExamSettingHasCandidateVo> transformExamSettingHasCandidates(List<ExamSettingHasCandidate> examSettingHasCandidates){
		List<ExamSettingHasCandidateVo> examSettingHasCandidateVos = new ArrayList<>();
		examSettingHasCandidates.forEach(examSettingHasCandidate->{
			ExamSettingHasCandidateVo examSettingHasCandidateVo = new ExamSettingHasCandidateVo(examSettingHasCandidate.getExamSettingHasCandidateId(), examSettingHasCandidate.getSettingId(), examSettingHasCandidate.getExamId(), examSettingHasCandidate.getCandidateId(), examSettingHasCandidate.getActive());
			examSettingHasCandidateVos.add(examSettingHasCandidateVo);
		});
		return examSettingHasCandidateVos;
	}
	
}
