package com.icat.quest.admin.service;

import java.util.Map;

import com.icat.quest.common.vo.ExplanationDescriptionVo;
import com.icat.quest.model.ExplanationDescription;

public interface ExplanationDescriptionService {

	Integer createExplanationDescription(ExplanationDescriptionVo explanationDescriptionVo,Integer userId);

	Integer updateExplanationDescription(ExplanationDescriptionVo explanationDescriptionVo,Integer userId) throws Exception;

	ExplanationDescriptionVo readExplanationDescription(Integer explanationDescriptionId,Integer userId) throws Exception;


	ExplanationDescriptionVo readExplanationDescriptionByQuestionId(Integer questionId,Integer userId) throws Exception;

	Map<String, Object> listExplanationDescription(Integer pageNo, Integer pageSize,Integer userId) throws Exception;

	ExplanationDescription transformExplanationDesc(ExplanationDescriptionVo explanationDescriptionVo, Integer userId);


}
