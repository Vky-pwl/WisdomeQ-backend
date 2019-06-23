package com.icat.quest.admin.service;

import java.util.List;
import java.util.Map;

import com.icat.quest.common.vo.ExamCategoryVo;

public interface ExamCategoryService {

	Integer createExamCategory(ExamCategoryVo examCategoryVo,Integer userId);

	Integer updateExamCategory(ExamCategoryVo examCategoryVo,Integer userId) throws Exception;

	ExamCategoryVo readExamCategory(Integer examCategoryId,Integer userId) throws Exception;

	Map<String,Object> listExamCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId) throws Exception;

	Map<String, Object> listExamSubCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,Integer userId)
			throws Exception;

	List<ExamCategoryVo> listSubcategory(Integer parentExamCateogryId,Integer userId,Boolean active);

}
