/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.ExplanationDescriptionService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExplanationDescriptionVo;
import com.icat.quest.dao.ExplanationDescriptionDao;
import com.icat.quest.model.ExplanationDescription;

@Service
public class ExplanationDescriptionServiceImpl implements ExplanationDescriptionService {

	@Autowired
	private ExplanationDescriptionDao explanationDescriptionDao;

	@Override
	public Integer createExplanationDescription(ExplanationDescriptionVo explanationDescriptionVo, Integer userId) {

		return explanationDescriptionDao.create(transformExplanationDesc(explanationDescriptionVo, userId));
	}
	@Override
	public ExplanationDescription transformExplanationDesc(ExplanationDescriptionVo explanationDescriptionVo , Integer userId) {
		ExplanationDescription explanationDescription = new ExplanationDescription();
		explanationDescription.setActive(true);
		explanationDescription.setCreated(new Date());
		explanationDescription.setCreatedBy(new Long(userId));
		explanationDescription.setLastModified(new Date());
		explanationDescription.setLastModifiedBy(new Long(userId));
		if (explanationDescriptionVo.getExplanationTextType() != null
				&& explanationDescriptionVo.getExplanationTextType()) {
			explanationDescription.setExplanation(explanationDescriptionVo.getExplanationText() != null
					? explanationDescriptionVo.getExplanationText().getBytes()
					: new byte[0]);
		} else {
			explanationDescription.setExplanation(explanationDescriptionVo.getExplanationImage());
		}
		explanationDescription.setExplanationTextType(explanationDescriptionVo.getExplanationTextType());
		explanationDescription.setQuestionId(explanationDescriptionVo.getQuestionId());
		return explanationDescription;
	}

	@Override
	public Integer updateExplanationDescription(ExplanationDescriptionVo explanationDescriptionVo, Integer userId)
			throws Exception {
		if (explanationDescriptionVo.getExplanationId() == null)
			throw new Exception("explanationDescriptionId should not be null");

		ExplanationDescription explanationDescription = explanationDescriptionDao
				.read(explanationDescriptionVo.getExplanationId());
		if (explanationDescription == null)
			throw new Exception("Record not found");

		if (explanationDescriptionVo.getQuestionId() != null)
			explanationDescription.setQuestionId(explanationDescriptionVo.getQuestionId());
		if (explanationDescriptionVo.getExplanationTextType() != null && explanationDescriptionVo.getExplanationTextType() && explanationDescriptionVo.getExplanationText() != null) {
			explanationDescription.setExplanation(explanationDescriptionVo.getExplanationText().trim().getBytes());
		} else if (explanationDescriptionVo.getExplanationTextType() != null && explanationDescriptionVo.getExplanationTextType() && explanationDescriptionVo.getExplanationImage() != null) {
			explanationDescription.setExplanation(explanationDescriptionVo.getExplanationImage());
			}			
		if (explanationDescriptionVo.getExplanationTextType() != null) {
			explanationDescription.setExplanationTextType(explanationDescriptionVo.getExplanationTextType());
		}
		explanationDescription.setLastModified(new Date());
		explanationDescription.setLastModifiedBy(new Long(userId));
		explanationDescriptionDao.update(explanationDescription);
		return explanationDescriptionVo.getExplanationId();
	}

	@Override
	public ExplanationDescriptionVo readExplanationDescription(Integer explanationDescriptionId, Integer userId)
			throws Exception {
		if (explanationDescriptionId == null) {
			throw new Exception("explanationDescriptionId should not be null");
		}
		ExplanationDescription explanationDescription = explanationDescriptionDao.read(explanationDescriptionId);
		if (explanationDescription == null) {
			throw new Exception("Record not found");
		}
			return Transformer.EXPLANATION_TRANSFORMER.transform(explanationDescription);
	}

	@Override
	public ExplanationDescriptionVo readExplanationDescriptionByQuestionId(Integer questionId, Integer userId)
			throws Exception {
		if (questionId == null) {
			throw new Exception("questionId should not be null");
		}
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_questionId", questionId);
		ExplanationDescription explanationDescription = explanationDescriptionDao
				.listEntityByParameter(ExplanationDescriptionDao.findAllByQuestionId, paramsKayAndValues, null, null)
				.get(0);
		if (explanationDescription == null) {
			throw new Exception("Record not found");
		}
		return Transformer.EXPLANATION_TRANSFORMER.transform(explanationDescription);
	}

	@Override
	public Map<String, Object> listExplanationDescription(Integer pageNo, Integer pageSize, Integer userId)
			throws Exception {

		List<ExplanationDescription> explanationDescriptionList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;
		explanationDescriptionList = explanationDescriptionDao.listEntityByParameter(ExplanationDescriptionDao.findAll,
				parameters, startIndex, pageSize);

		Map<String, Object> explanationDescriptionResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			explanationDescriptionResultSet.put("count", explanationDescriptionList.size());
			List<ExplanationDescriptionVo> explanationDescriptionVos = new ArrayList<>();
			explanationDescriptionList.stream().skip(startIndex).limit(pageSize).forEach(explanationDescription -> {
				explanationDescriptionVos.add(Transformer.EXPLANATION_TRANSFORMER.transform(explanationDescription));
			});
			explanationDescriptionResultSet.put("explanationDescriptionVoList", explanationDescriptionVos);
		} else {
			List<ExplanationDescriptionVo> explanationDescriptionVos = new ArrayList<>();
			explanationDescriptionList.forEach(explanationDescription -> {
				explanationDescriptionVos.add(Transformer.EXPLANATION_TRANSFORMER.transform(explanationDescription));
			});
			explanationDescriptionResultSet.put("explanationDescriptionVoList", explanationDescriptionVos);
		}

		return explanationDescriptionResultSet;
	}

}
