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

import com.icat.quest.admin.service.ExamCategoryService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExamCategoryVo;
import com.icat.quest.dao.ExamCategoryDao;
import com.icat.quest.model.ExamCategory;

@Service
public class ExamCategoryServiceImpl implements ExamCategoryService {

	@Autowired
	private ExamCategoryDao examCategoryDao;

	@Override
	public Integer createExamCategory(ExamCategoryVo examCategoryVo, Integer userId) {

		ExamCategory examCategory = new ExamCategory();
		examCategory.setActive(true);
		examCategory.setCreated(new Date());
		examCategory.setCreatedBy(new Long(userId));
		examCategory.setLastModified(new Date());
		examCategory.setLastModifiedBy(new Long(userId));
		if (examCategoryVo.getExamSubCategoryName() != null && examCategoryVo.getExamCategoryId() != null) {
			examCategory.setExamCategoryName(examCategoryVo.getExamSubCategoryName());
			examCategory.setParentExamCategoryId(examCategoryVo.getExamCategoryId());
			examCategory.setParentExamCategoryName(examCategoryVo.getExamCategoryName());
		} else {
			examCategory.setExamCategoryName(examCategoryVo.getExamCategoryName());
		}

		Integer id = examCategoryDao.create(examCategory);
		return id;
	}

	@Override
	public Integer updateExamCategory(ExamCategoryVo examCategoryVo, Integer userId) throws Exception {
		ExamCategory examCategory = null;

		if (examCategoryVo.getExamSubCategoryId() != null) {
			examCategory = examCategoryDao.read(examCategoryVo.getExamSubCategoryId());
			if (examCategory == null)
				throw new Exception("Record not found");

			if (examCategoryVo.getExamSubCategoryName() != null) {
				examCategory.setExamCategoryName(examCategoryVo.getExamSubCategoryName());
			}
		} else {
			examCategory = examCategoryDao.read(examCategoryVo.getExamCategoryId());
			if (examCategory == null)
				throw new Exception("Record not found");

			if (examCategoryVo.getExamCategoryName() != null) {
				examCategory.setExamCategoryName(examCategoryVo.getExamCategoryName());
			}
		}

		if (examCategoryVo.getActive() != null) {
			examCategory.setActive(examCategoryVo.getActive());
		}
		examCategory.setLastModified(new Date());
		examCategory.setLastModifiedBy(new Long(userId));
		examCategoryDao.update(examCategory);
		return examCategoryVo.getExamCategoryId();
	}

	@Override
	public ExamCategoryVo readExamCategory(Integer examCategoryId, Integer userId) throws Exception {
		if (examCategoryId == null) {
			throw new Exception("examCategoryId should not be null");
		}

		ExamCategory examCategory = examCategoryDao.read(examCategoryId);
		if (examCategory == null) {
			throw new Exception("Record not found");
		}
		return Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory);
	}

	@Override
	public List<ExamCategoryVo> listSubcategory(Integer examCateogryId, Integer userId, Boolean active) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_parentExamCateogryId", examCateogryId);
		List<ExamCategory> examCategories = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllByParentId,
				paramsKayAndValues, null, null);
		if (active != null) {
			examCategories = examCategories.stream().filter(examCategorie -> examCategorie.getActive().equals(active))
					.collect(Collectors.toList());
		}
		List<ExamCategoryVo> examCategoryVos = new ArrayList<>();
		examCategories.forEach(examCategory -> {
			examCategoryVos.add(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory));
		});

		return examCategoryVos;

	}

	@Override
	public Map<String, Object> listExamCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<ExamCategory> examCategoryList = new ArrayList<>();
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
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllBySearchKeyWithFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllByFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllBySearchKey, parameters,
					startIndex, pageSize);
		} else {
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAll, parameters, startIndex,
					pageSize);
		}
		Map<String, Object> examCategoryResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			examCategoryResultSet.put("count", examCategoryList.size());
			List<ExamCategoryVo> examCategoryVos = new ArrayList<>();
			examCategoryList.stream().skip(startIndex).limit(pageSize).forEach(examCategory -> {
				examCategoryVos.add(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory));
			});
			examCategoryResultSet.put("examCategoryVoList", examCategoryVos);
		} else {
			List<ExamCategoryVo> examCategoryVos = new ArrayList<>();
			examCategoryList.forEach(examCategory -> {
				examCategoryVos.add(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory));
			});
			examCategoryResultSet.put("examCategoryVoList", examCategoryVos);
		}

		return examCategoryResultSet;
	}

	@Override
	public Map<String, Object> listExamSubCategory(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<ExamCategory> examCategoryList = new ArrayList<>();
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
			examCategoryList = examCategoryDao.listEntityByParameter(
					ExamCategoryDao.findAllSubCategoryBySearchKeyWithFilter, parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllSubCategoryByFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_categoryName", searchKey);
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllSubCategoryBySearchKey,
					parameters, startIndex, pageSize);
		} else {
			examCategoryList = examCategoryDao.listEntityByParameter(ExamCategoryDao.findAllSubCategory, parameters,
					startIndex, pageSize);
		}
		Map<String, Object> examCategoryResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			examCategoryResultSet.put("count", examCategoryList.size());
			List<ExamCategoryVo> examCategoryVos = new ArrayList<>();
			examCategoryList.stream().skip(startIndex).limit(pageSize).forEach(examCategory -> {
				examCategoryVos.add(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory));
			});
			examCategoryResultSet.put("examCategoryVoList", examCategoryVos);
		} else {
			List<ExamCategoryVo> examCategoryVos = new ArrayList<>();
			examCategoryList.forEach(examCategory -> {
				examCategoryVos.add(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(examCategory));
			});
			examCategoryResultSet.put("examCategoryVoList", examCategoryVos);
		}

		return examCategoryResultSet;
	}

}
