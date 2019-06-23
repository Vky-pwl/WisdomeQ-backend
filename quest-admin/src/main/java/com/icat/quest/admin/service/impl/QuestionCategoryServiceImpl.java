/**
 * 
 */
package com.icat.quest.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.quest.admin.service.QuestionCategoryService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.dao.QuestionCategoryDao;
import com.icat.quest.model.QuestionCategory;

@Service
public class QuestionCategoryServiceImpl implements QuestionCategoryService {

	@Autowired
	private QuestionCategoryDao questionCategoryDao;

	@Override
	public Integer createQuestionCategory(QuestionCategoryVo questionCategoryVo, Integer userId) {

		QuestionCategory questionCategory = new QuestionCategory();
		questionCategory.setActive(true);
		questionCategory.setCreated(new Date());
		questionCategory.setCreatedBy(new Long(userId));
		questionCategory.setLastModified(new Date());
		questionCategory.setLastModifiedBy(new Long(userId));
		if (questionCategoryVo.getQuestionCategoryName() != null
				&& questionCategoryVo.getQuestionCategoryId() != null) {
			questionCategory.setQuestionCategoryName(questionCategoryVo.getQuestionSubCategoryName());
			questionCategory.setParentQuestionCategoryId(questionCategoryVo.getQuestionCategoryId());
			questionCategory.setParentQuestionCategoryName(questionCategoryVo.getQuestionCategoryName());
		} else {
			questionCategory.setQuestionCategoryName(questionCategoryVo.getQuestionCategoryName());
		}
		Integer id = questionCategoryDao.create(questionCategory);
		return id;
	}

	@Override
	public Integer updateQuestionCategory(QuestionCategoryVo questionCategoryVo, Integer userId) throws Exception {
		QuestionCategory questionCategory = null;

		if (questionCategoryVo.getQuestionSubCategoryId() != null) {
			questionCategory = questionCategoryDao.read(questionCategoryVo.getQuestionSubCategoryId());
			if (questionCategory == null)
				throw new Exception("Record not found");

			if (questionCategoryVo.getQuestionSubCategoryName() != null) {
				questionCategory.setQuestionCategoryName(questionCategoryVo.getQuestionSubCategoryName());
			}
		} else {
			questionCategory = questionCategoryDao.read(questionCategoryVo.getQuestionCategoryId());
			if (questionCategory == null)
				throw new Exception("Record not found");

			if (questionCategoryVo.getQuestionCategoryName() != null) {
				questionCategory.setQuestionCategoryName(questionCategoryVo.getQuestionCategoryName());
			}
		}

		if (questionCategoryVo.getActive() != null) {
			questionCategory.setActive(questionCategoryVo.getActive());
		}
		questionCategory.setLastModified(new Date());
		questionCategory.setLastModifiedBy(new Long(userId));
		questionCategoryDao.update(questionCategory);
		return questionCategoryVo.getQuestionCategoryId();
	}

	@Override
	public QuestionCategoryVo readQuestionCategory(Integer questionCategoryId, Integer userId) throws Exception {
		if (questionCategoryId == null) {
			throw new Exception("questionCategoryId should not be null");
		}
		QuestionCategory questionCategory = questionCategoryDao.read(questionCategoryId);
		if (questionCategory == null) {
			throw new Exception("Record not found");
		}
		return Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory);
	}

	@Override
	public List<QuestionCategoryVo> listSubcategory(Integer questionCateogryId, Integer userId, Boolean active) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_parentQuestionCateogryId", questionCateogryId);
		List<QuestionCategory> questionCategoryList = questionCategoryDao
				.listEntityByParameter(QuestionCategoryDao.findAllByParentId, paramsKayAndValues, null, null);
		if (active != null) {
			questionCategoryList = questionCategoryList.stream()
					.filter(questionCategorie -> questionCategorie.getActive().equals(active))
					.collect(Collectors.toList());
		}
		List<QuestionCategoryVo> questionCategoryVos = new ArrayList<>();
		questionCategoryList.forEach(questionCategory -> {
			questionCategoryVos.add(Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory));
		});
		return questionCategoryVos;
	}

	@Override
	public Map<String, Object> listQuestionCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<QuestionCategory> questionCategoryList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;
		if (searchKey != null && active != null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			parameters.put("_1_active", active);
			questionCategoryList = questionCategoryDao.listEntityByParameter(
					QuestionCategoryDao.findAllBySearchKeyWithFilter, parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			questionCategoryList = questionCategoryDao.listEntityByParameter(QuestionCategoryDao.findAllByFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			questionCategoryList = questionCategoryDao.listEntityByParameter(QuestionCategoryDao.findAllBySearchKey,
					parameters, startIndex, pageSize);
		} else {
			questionCategoryList = questionCategoryDao.listEntityByParameter(QuestionCategoryDao.findAll, parameters,
					startIndex, pageSize);
		}
		Map<String, Object> questionCategoryResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			questionCategoryResultSet.put("count", questionCategoryList.size());
			List<QuestionCategoryVo> questionCategoryVos = new ArrayList<>();
			questionCategoryList.stream().skip(startIndex).limit(pageSize).forEach(questionCategory -> {
				questionCategoryVos.add(Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory));
			});
			questionCategoryResultSet.put("questionCategoryVoList", questionCategoryVos);
		} else {
			List<QuestionCategoryVo> questionCategoryVos = new ArrayList<>();
			questionCategoryList.forEach(questionCategory -> {
				questionCategoryVos.add(Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory));
			});

			questionCategoryResultSet.put("questionCategoryVoList", questionCategoryVos);
		}

		return questionCategoryResultSet;
	}

	@Override
	public Map<String, Object> listQuestionSubCategory(Integer pageNo, Integer pageSize, String searchKey,
			Boolean active, Integer userId) throws Exception {
		List<QuestionCategory> questionCategoryList = new ArrayList<>();
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Map<String, Object> parameters = new HashMap<>();
		Integer startIndex = null;
		if (pageNo != 1)
			startIndex = (pageNo * pageSize) - pageSize;
		if (searchKey != null && active != null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			parameters.put("_1_active", active);
			questionCategoryList = questionCategoryDao.listEntityByParameter(
					QuestionCategoryDao.findAllSubCategoryBySearchKeyWithFilter, parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			questionCategoryList = questionCategoryDao.listEntityByParameter(
					QuestionCategoryDao.findAllSubCategoryByFilter, parameters, startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			questionCategoryList = questionCategoryDao.listEntityByParameter(
					QuestionCategoryDao.findAllSubCategoryBySearchKey, parameters, startIndex, pageSize);
		} else {
			questionCategoryList = questionCategoryDao.listEntityByParameter(QuestionCategoryDao.findAllSubCategory,
					parameters, startIndex, pageSize);
		}
		Map<String, Object> questionCategoryResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			questionCategoryResultSet.put("count", questionCategoryList.size());
			List<QuestionCategoryVo> questionCategoryVos = new ArrayList<>();
			questionCategoryList.stream().skip(startIndex).limit(pageSize).forEach(questionCategory -> {
				questionCategoryVos.add(Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory));
			});
			questionCategoryResultSet.put("questionCategoryVoList", questionCategoryVos);
		} else {
			List<QuestionCategoryVo> questionCategoryVos = new ArrayList<>();
			questionCategoryList.forEach(questionCategory -> {
				questionCategoryVos.add(Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionCategory));
			});

			questionCategoryResultSet.put("questionCategoryVoList", questionCategoryVos);
		}

		return questionCategoryResultSet;
	}

}
