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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.icat.quest.admin.service.ExamSectionService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.common.vo.SectionName;
import com.icat.quest.dao.ExamDao;
import com.icat.quest.dao.ExamSectionDao;
import com.icat.quest.dao.QuestionCategoryDao;
import com.icat.quest.model.Exam;
import com.icat.quest.model.ExamSection;
import com.icat.quest.model.QuestionCategory;

@Service
public class ExamSectionServiceImpl implements ExamSectionService {

	@Autowired
	private ExamSectionDao examSectionDao;
	@Autowired
	private ExamDao examDao;
	@Autowired
	private QuestionCategoryDao questionCategoryDao;

	@Override
	public Integer createExamSection(ExamSectionVo examSectionVo, Integer userId) throws Exception {

		ExamSection examSection = new ExamSection();
		examSection.setActive(true);
		examSection.setCreated(new Date());
		examSection.setCreatedBy(new Long(userId));
		examSection.setLastModified(new Date());
		examSection.setLastModifiedBy(new Long(userId));
		// Field value Valiadtion
		inputValidation(examSectionVo);
		Exam exam = examDao.read(examSectionVo.getExamVo().getExamId());
		examSection.setExam(exam);
		// Data Validation
		if (examSectionVo.getSicoFlag() != null && !examSectionVo.getSicoFlag()) {
			dataValidation(exam, examSectionVo, false);
		}
		if (examSectionVo.getQuestionCategoryVo() == null
				|| examSectionVo.getQuestionCategoryVo().getQuestionCategoryId() == null) {
			throw new Exception("QuestionCategory data is not valid");
		}

		QuestionCategory questionCategory = questionCategoryDao
				.read(examSectionVo.getQuestionCategoryVo().getQuestionCategoryId());
		if (questionCategory == null) {
			throw new Exception("QuestionCategory data is not valid");
		}
		examSection.setSicoFlag(examSectionVo.getSicoFlag() != null ? examSectionVo.getSicoFlag() : false);
		examSection.setQuestionCategory(questionCategory);
		examSection.setExamSectionDescription(examSectionVo.getExamSectionDescription() != null ? examSectionVo.getExamSectionDescription().trim().getBytes() : new byte[0]);
		examSection.setExamSectionInstructions(examSectionVo.getExamSectionInstructions() != null ? examSectionVo.getExamSectionInstructions().trim().getBytes() : new byte[0]);
		examSection.setDurationInSeconds(examSectionVo.getDurationInSeconds());
		examSection.setSequence(examSectionVo.getSequence());
		examSection.setTotalMarks(examSectionVo.getTotalMarks());
		examSection.setTotalQuestion(examSectionVo.getTotalQuestion());
		examSection.setSectionName(SectionName.valueOf(examSectionVo.getSectionName()));
		Integer id = examSectionDao.create(examSection);
		return id;
	}

	@Override
	public Integer updateExamSection(ExamSectionVo examSectionVo, Integer userId) throws Exception {
		if (examSectionVo.getExamSectionId() == null)
			throw new Exception("examSectionId should not be null");

		ExamSection examSection = examSectionDao.read(examSectionVo.getExamSectionId());
		if (examSection == null)
			throw new Exception("Record not found");
		if (examSectionVo.getQuestionCategoryVo() == null
				|| examSectionVo.getQuestionCategoryVo().getQuestionCategoryId() == null
				|| examSectionVo.getQuestionCategoryVo().getQuestionSubCategoryId() != null) {
			throw new Exception("QuestionCategory data is not valid");
		}

		// Field value Valiadtion
		inputValidation(examSectionVo);
		// Data Validation
		if (examSectionVo.getSicoFlag() != null && !examSectionVo.getSicoFlag()) {
			dataValidation(examSection.getExam(), examSectionVo, true);
		}

		if (examSectionVo.getExamSectionDescription() != null) {
			examSection.setExamSectionDescription(examSectionVo.getExamSectionDescription().trim().getBytes());
		}
		if (examSectionVo.getExamSectionInstructions() != null) {
			examSection.setExamSectionInstructions(examSectionVo.getExamSectionInstructions().trim().getBytes());
		}

		if (examSectionVo.getDurationInSeconds() != null) {
			examSection.setDurationInSeconds(examSectionVo.getDurationInSeconds());
		}
		if (examSectionVo.getSequence() != null) {
			examSection.setSequence(examSectionVo.getSequence());
		}
		if (examSectionVo.getTotalMarks() != null) {
			examSection.setTotalMarks(examSectionVo.getTotalMarks());
		}
		if (examSectionVo.getTotalQuestion() != null) {
			examSection.setTotalQuestion(examSectionVo.getTotalQuestion());
		}
		if (examSectionVo.getActive() != null) {
			examSection.setActive(examSectionVo.getActive());
		}
		if(examSectionVo.getSectionName() != null) {
			examSection.setSectionName(SectionName.valueOf(examSectionVo.getSectionName()));
		}
		if (examSection.getExam() == null
				|| (examSectionVo.getExamVo() != null && examSectionVo.getExamVo().getExamId() != null
						&& !examSection.getExam().getExamId().equals(examSectionVo.getExamVo().getExamId()))) {
			Exam exam = examDao.read(examSectionVo.getExamVo().getExamId());
			examSection.setExam(exam);
		}
		if (examSection.getQuestionCategory() == null || (examSectionVo.getQuestionCategoryVo() != null
				&& examSectionVo.getQuestionCategoryVo().getQuestionCategoryId() != null
				&& !examSection.getQuestionCategory().getQuestionCategoryId()
						.equals(examSectionVo.getQuestionCategoryVo().getQuestionCategoryId()))) {
			QuestionCategory questionCategory = questionCategoryDao
					.read(examSectionVo.getQuestionCategoryVo().getQuestionCategoryId());

			examSection.setQuestionCategory(questionCategory);
		}
		if (examSectionVo.getSicoFlag() != null) {
			examSection.setSicoFlag(examSectionVo.getSicoFlag());
		}
		examSection.setLastModified(new Date());
		examSection.setLastModifiedBy(new Long(userId));

		examSectionDao.update(examSection);
		return examSectionVo.getExamSectionId();
	}

	@Override
	public ExamSectionVo readExamSection(Integer examSectionId, Integer userId) throws Exception {
		if (examSectionId == null) {
			throw new Exception("examSectionId should not be null");
		}
		ExamSection examSection = examSectionDao.read(examSectionId);
		if (examSection == null) {
			throw new Exception("Record not found");
		}
		return Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection);
	}

	@Override
	public Map<String, Object> listExamSectionByExamSectionIdList(Integer pageNo, Integer pageSize, String searchKey,
			Boolean active, Integer userId, List<Integer> examSectionIdList) throws Exception {

		List<ExamSection> examSectionList = new ArrayList<>();
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
			parameters.put("_2_examSectionName", searchKey);
			parameters.put("_1_active", active);
			parameters.put("_3_examSectionIdList", examSectionIdList);
			examSectionList = examSectionDao.listEntityByParameter(
					ExamSectionDao.findAllBySearchKeyWithFilterAndESIdList, parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			parameters.put("_3_examSectionIdList", examSectionIdList);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllByFilterAndESIdList,
					parameters, startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_examSectionName", searchKey);
			parameters.put("_3_examSectionIdList", examSectionIdList);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllBySearchKeyAndESIdList,
					parameters, startIndex, pageSize);
		} else {
			parameters.put("_3_examSectionIdList", examSectionIdList);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllAndESIdList, parameters,
					startIndex, pageSize);
		}
		Map<String, Object> examSectionResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			examSectionResultSet.put("count", examSectionList.size());
			List<ExamSectionVo> examSectionVos = new ArrayList<>();
			examSectionList.stream().skip(startIndex).limit(pageSize).forEach(examSection -> {
				examSectionVos.add(Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection));
			});
			examSectionResultSet.put("examSectionVoList", examSectionVos);

		} else {
			List<ExamSectionVo> examSectionVos = new ArrayList<>();
			examSectionList.forEach(examSection -> {
				examSectionVos.add(Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection));
			});
			examSectionResultSet.put("examSectionVoList", examSectionVos);
		}

		return examSectionResultSet;
	}

	@Override
	public Map<String, Object> listExamSection(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId) throws Exception {

		List<ExamSection> examSectionList = new ArrayList<>();
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
			parameters.put("_2_examSectionName", searchKey);
			parameters.put("_1_active", active);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllBySearchKeyWithFilter,
					parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllByFilter, parameters,
					startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_examSectionName", searchKey);
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllBySearchKey, parameters,
					startIndex, pageSize);
		} else {
			examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAll, parameters, startIndex,
					pageSize);
		}
		Map<String, Object> examSectionResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			examSectionResultSet.put("count", examSectionList.size());
			List<ExamSectionVo> examSectionVos = new ArrayList<>();
			examSectionList.stream().skip(startIndex).limit(pageSize).forEach(examSection -> {
				examSectionVos.add(Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection));
			});
			examSectionResultSet.put("examSectionVoList", examSectionVos);

		} else {
			List<ExamSectionVo> examSectionVos = new ArrayList<>();
			examSectionList.forEach(examSection -> {
				examSectionVos.add(Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection));
			});
			examSectionResultSet.put("examSectionVoList", examSectionVos);
		}

		return examSectionResultSet;
	}

	@Override
	public List<ExamSectionVo> getExamSectionVoListByExamId(Integer examId, Integer userId) {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", examId);
		List<ExamSection> examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllByExamId,
				paramsKayAndValues, null, null);
		List<ExamSectionVo> examSectionVos = new ArrayList<>();
		examSectionList.forEach(examSection -> {
			examSectionVos.add(Transformer.EXAM_SECTION_TRANSFORMER.transform(examSection));
		});
		return examSectionVos;
	}

	@Override
	public List<QuestionCategoryVo> questionSubCategoryVos(Integer examSectionId) {
		List<QuestionCategoryVo> questionCategoryVoList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		GsonBuilder builder = new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		Gson gson = builder.create();
		Map<String, Object> paramsKeyAndValues = new HashMap<String, Object>();
		paramsKeyAndValues.put("_1_examSectionId", examSectionId);
		List<String> excludedFieldNames = new ArrayList<>();
		excludedFieldNames.add("active");excludedFieldNames.add("totalAttempt");
		excludedFieldNames.add("userTotalMarks");excludedFieldNames.add("totalCorrectAnswer");
		
		List<Object[]> questionCategoryArrayList = examSectionDao
				.listCompositeSqlQuery(ExamSectionDao.findAllSubSection, paramsKeyAndValues);

		try {
			List<Object> questionCategoryObjectList = examSectionDao.convertToObjectList(questionCategoryArrayList,
					new QuestionCategoryVo(), excludedFieldNames, null);
			questionCategoryVoList = mapper.readValue(gson.toJson(questionCategoryObjectList),
					new TypeReference<List<QuestionCategoryVo>>() {
					});

		} catch (Exception e) {
			e.printStackTrace();
		}

		return questionCategoryVoList;
	}

	private void dataValidation(Exam exam, ExamSectionVo examSectionVo, boolean isUpdate) throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examId", exam.getExamId());
		List<ExamSection> examSectionList = examSectionDao.listEntityByParameter(ExamSectionDao.findAllByExamId,
				paramsKayAndValues, null, null);

		if (!isUpdate && exam.getSectionCount() == examSectionList.size()) {
			throw new Exception("Section count exceed");
		}
		Integer totalMarks = exam.getTotalMarks() - examSectionVo.getTotalMarks();
		Integer totalQuestionCount = exam.getQuestionCount() - examSectionVo.getTotalQuestion();
		Long duration = exam.getDurationInSeconds() - examSectionVo.getDurationInSeconds();

		for (ExamSection examSection : examSectionList) {
			if(isUpdate) {
				if(examSection.getExamSectionId().equals(examSectionVo.getExamSectionId())) {
					if (!examSection.getSequence().equals(examSectionVo.getSequence())) {
						throw new Exception("Sequence already exist");
					}
					continue;
				}
				
			}else if (examSection.getSequence().equals(examSectionVo.getSequence())) {
				throw new Exception("Sequence already exist");
			}
			duration -= examSection.getDurationInSeconds();
			totalQuestionCount -= examSection.getTotalQuestion();
			totalMarks -= examSection.getTotalMarks();
		}
		if (duration < 0) {
			throw new Exception("Duration exceed");
		}
		if (totalMarks < 0) {
			throw new Exception("Total Marks exceed");
		}
		if (totalQuestionCount < 0) {
			throw new Exception("Question count exceed");
		}
	}

	private void inputValidation(ExamSectionVo examSectionVo) throws Exception {
		if (examSectionVo.getDurationInSeconds() == null) {
			throw new Exception("DurationInSeconds should not be null");
		}
		if (examSectionVo.getTotalMarks() == null) {
			throw new Exception("TotalMarks should not be null");
		}
		if (examSectionVo.getTotalQuestion() == null) {
			throw new Exception("TotalQuestion should not be null");
		}
		if (examSectionVo.getSequence() == null) {
			throw new Exception("Sequence should not be null");
		}
		if (examSectionVo.getExamVo() == null || examSectionVo.getExamVo().getExamId() == null) {
			throw new Exception("ExamId should not be null");
		}

	}
}
