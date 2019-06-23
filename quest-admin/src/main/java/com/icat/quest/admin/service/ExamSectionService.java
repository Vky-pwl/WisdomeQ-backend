package com.icat.quest.admin.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.common.vo.QuestionCategoryVo;

public interface ExamSectionService {

	Integer createExamSection(ExamSectionVo examSectionVo,Integer userId) throws Exception;

	Integer updateExamSection(ExamSectionVo examSectionVo,Integer userId) throws Exception;

	ExamSectionVo readExamSection(Integer examSectionId,Integer userId) throws Exception;

	Map<String,Object> listExamSection(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId) throws Exception;

	List<ExamSectionVo> getExamSectionVoListByExamId(Integer examId,Integer userId);

	List<QuestionCategoryVo> questionSubCategoryVos(Integer examSectionId);

	Map<String, Object> listExamSectionByExamSectionIdList(Integer pageNo, Integer pageSize, String searchKey,
			Boolean active, Integer userId, List<Integer> examSectionIdList) throws Exception;

}
