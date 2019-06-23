package com.icat.quest.admin.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.QuestionCategoryVo;

public interface QuestionCategoryService {

	Integer createQuestionCategory(QuestionCategoryVo questionCategoryVo,Integer userId);

	Integer updateQuestionCategory(QuestionCategoryVo questionCategoryVo,Integer userId) throws Exception;

	QuestionCategoryVo readQuestionCategory(Integer questionCategoryId,Integer userId) throws Exception;

	Map<String,Object> listQuestionCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId) throws Exception;

	List<QuestionCategoryVo> listSubcategory(Integer parentQuestionCateogryId,Integer userId,Boolean active);

	Map<String, Object> listQuestionSubCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId)
			throws Exception;

}
