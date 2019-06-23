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

import com.icat.quest.admin.service.ExamSectionHasQuestionService;
import com.icat.quest.admin.service.ExplanationDescriptionService;
import com.icat.quest.admin.service.QuestionBankService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.OptionVo;
import com.icat.quest.common.vo.QuestionDescriptionVo;
import com.icat.quest.dao.ExamSectionDao;
import com.icat.quest.dao.ExamSectionHasQuestionDao;
import com.icat.quest.dao.ExplanationDescriptionDao;
import com.icat.quest.dao.QuestionBankDao;
import com.icat.quest.dao.QuestionDescriptionDao;
import com.icat.quest.dao.SicoSectionMarksDao;
import com.icat.quest.model.ExamSection;
import com.icat.quest.model.ExamSectionHasQuestion;
import com.icat.quest.model.ExplanationDescription;
import com.icat.quest.model.QuestionBank;
import com.icat.quest.model.QuestionDescription;
import com.icat.quest.model.QuestionOption;
import com.icat.quest.model.SicoSectionMarks;

@Service
public class ExamSectionHasQuestionServiceImpl implements ExamSectionHasQuestionService {

	@Autowired
	private ExamSectionHasQuestionDao examSectionHasQuestionDao;
	@Autowired
	private QuestionBankService questionBankService;
	@Autowired
	private QuestionBankDao questionBankDao;
	@Autowired
	private ExamSectionDao examSectionDao;
	@Autowired
	private ExplanationDescriptionService explanationDescriptionService;
	@Autowired
	private SicoSectionMarksDao sicoSectionMarksDao;
	@Autowired
	private ExplanationDescriptionDao explanationDescriptionDao;
	@Autowired
	private QuestionDescriptionDao			questionDescriptionDao;
	public enum AnswerType {
		A, B, C, D, E
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Integer createExamSectionHasQuestion(ExamSectionHasQuestionVo examSectionHasQuestionVo, Integer userId)
			throws Exception {

		validationField(examSectionHasQuestionVo);
		ExamSection examSection = examSectionDao.read(examSectionHasQuestionVo.getExamSectionVo().getExamSectionId());
		if (examSection == null) {
			throw new Exception("ExamSection record not found");
		}
		if (examSectionHasQuestionVo.getExamSectionVo().getSicoFlag() == null
				|| !examSectionHasQuestionVo.getExamSectionVo().getSicoFlag()) {
			validationData(examSectionHasQuestionVo, examSection);
		}
		List createList = new ArrayList<>();
		
		QuestionBank questionBank = null;
		if (examSectionHasQuestionVo.getQuestionBankVo().getQuestionId() == null) {
			QuestionDescription questionDescription = null;
			questionBank = questionBankService.transformQuestionBank(examSectionHasQuestionVo.getQuestionBankVo(),
					userId);
			QuestionDescriptionVo questionDescriptionVo = examSectionHasQuestionVo.getQuestionBankVo().getQuestionDescriptionVo();
			if(questionDescriptionVo != null ) {
				if(questionDescriptionVo.getDescriptionId() == null) {
				byte[] description = new byte[1024*4];
				if(questionDescriptionVo.getDescriptionTextType()) {
					description = questionDescriptionVo.getDescriptionText().getBytes();
				} else {
					description = questionDescriptionVo.getDescriptionImage();
				}
					questionDescription = new QuestionDescription(description, questionDescriptionVo.getDescriptionTextType(), true, new Date(), new Long(userId),new Date(), new Long(userId));
				createList.add(questionDescription);
				} else {
				questionDescription = questionDescriptionDao.read(examSectionHasQuestionVo.getQuestionBankVo().getQuestionDescriptionVo().getDescriptionId());	
				}
				questionBank.setQuestionDescription(questionDescription);
			}
			createList.add(questionBank);
		} else {
			questionBank = questionBankDao.read(examSectionHasQuestionVo.getQuestionBankVo().getQuestionId());
		}
		ExamSectionHasQuestion examSectionHasQuestion = new ExamSectionHasQuestion();
		examSectionHasQuestion.setActive(true);
		examSectionHasQuestion.setCreated(new Date());
		examSectionHasQuestion.setCreatedBy(new Long(userId));
		examSectionHasQuestion.setLastModified(new Date());
		examSectionHasQuestion.setLastModifiedBy(new Long(userId));
		examSectionHasQuestion.setMarks(examSectionHasQuestionVo.getMarks());
		examSectionHasQuestion.setQuestionNumber(examSectionHasQuestionVo.getQuestionNumber());
		examSectionHasQuestion.setExamSection(examSection);
		examSectionHasQuestion.setQuestionBank(questionBank);
		examSectionHasQuestion.setNegativeMark(examSectionHasQuestionVo.getNegativeMark());
		createList.add(examSectionHasQuestion);
		
		List<OptionVo> optionVos = examSectionHasQuestionVo.getQuestionBankVo().getOptions();
		Float marksA = 0f;
		Float marksB = 0f;
		Float marksC = 0f;
		Float marksD = 0f;
		Float marksE = 0f;

		for (OptionVo optionVo : optionVos) {
			switch (optionVo.getOptionName()) {
			case A:
				marksA = optionVo.getMarks();
				break;
			case B:
				marksB = optionVo.getMarks();
				break;
			case C:
				marksC = optionVo.getMarks();
				break;
			case D:
				marksD = optionVo.getMarks();
				break;
			case E:
				marksE = optionVo.getMarks();
				break;

			default:
				break;
			}
			QuestionOption questionOption = new QuestionOption(questionBank, optionVo.getOptionValue().getBytes(),
					optionVo.getOptionName(), optionVo.getOptionType(), optionVo.getMarks(), true, new Date());
			createList.add(questionOption);
		}
			
			
			if(createList.size()>0) {
				questionBankDao.createBatch(createList);
				if (examSection.getSicoFlag()) {
					SicoSectionMarks sicoSectionMarks = new SicoSectionMarks(
							examSectionHasQuestion.getExamSectionHasQuestionId(), marksA, marksB, marksC, marksD, marksE,
							true, new Date(), new Long(userId), new Date(), new Long(userId));
					sicoSectionMarksDao.create(sicoSectionMarks);
				}
				if(examSectionHasQuestionVo.getQuestionBankVo().getExplanation() != null && examSectionHasQuestionVo.getQuestionBankVo().getExplanation().getExplanationId() == null) {
					ExplanationDescription explanationDescription =	explanationDescriptionService.transformExplanationDesc(examSectionHasQuestionVo.getQuestionBankVo().getExplanation(), userId);
					explanationDescription.setQuestionId(questionBank.getQuestionId());
				explanationDescriptionDao.create(explanationDescription);	
				}
				
			}
			
			

		return examSectionHasQuestion.getExamSectionHasQuestionId();
	}

	@Override
	public void attachBatchExamSectionHasQuestion(List<ExamSectionHasQuestionVo> examSectionHasQuestionVos, Integer userId)
			throws Exception {
		for(ExamSectionHasQuestionVo examSectionHasQuestionVo : examSectionHasQuestionVos) {
		validationField(examSectionHasQuestionVo);
		ExamSection examSection = examSectionDao.read(examSectionHasQuestionVo.getExamSectionVo().getExamSectionId());
		if (examSection == null) {
			throw new Exception("ExamSection record not found");
		}
		if (examSectionHasQuestionVo.getExamSectionVo().getSicoFlag() == null
				|| !examSectionHasQuestionVo.getExamSectionVo().getSicoFlag()) {
			validationData(examSectionHasQuestionVo, examSection);
		}
		
		QuestionBank questionBank = questionBankDao.read(examSectionHasQuestionVo.getQuestionBankVo().getQuestionId());
		ExamSectionHasQuestion examSectionHasQuestion = new ExamSectionHasQuestion();
		examSectionHasQuestion.setActive(true);
		examSectionHasQuestion.setCreated(new Date());
		examSectionHasQuestion.setCreatedBy(new Long(userId));
		examSectionHasQuestion.setLastModified(new Date());
		examSectionHasQuestion.setLastModifiedBy(new Long(userId));
		examSectionHasQuestion.setMarks(examSectionHasQuestionVo.getMarks());
		examSectionHasQuestion.setQuestionNumber(examSectionHasQuestionVo.getQuestionNumber());
		examSectionHasQuestion.setExamSection(examSection);
		examSectionHasQuestion.setQuestionBank(questionBank);
		examSectionHasQuestion.setNegativeMark(examSectionHasQuestionVo.getNegativeMark());
		examSectionHasQuestionDao.create(examSectionHasQuestion);
		}
	}
	
	@Override
	public Integer updateExamSectionHasQuestion(ExamSectionHasQuestionVo examSectionHasQuestionVo, Integer userId)
			throws Exception {

		if (examSectionHasQuestionVo.getExamSectionHasQuestionId() == null) {
			throw new Exception("examSectionHasQuestionId should not be null");
		}
		ExamSectionHasQuestion examSectionHasQuestion = examSectionHasQuestionDao
				.read(examSectionHasQuestionVo.getExamSectionHasQuestionId());
		if (examSectionHasQuestion == null)
			throw new Exception("Record not found");
		validationField(examSectionHasQuestionVo);
		if (examSectionHasQuestionVo.getExamSectionVo().getSicoFlag() != null
				&& examSectionHasQuestionVo.getExamSectionVo().getSicoFlag()) {
			validationData(examSectionHasQuestionVo, examSectionHasQuestion.getExamSection());
		}

		if (examSectionHasQuestionVo.getQuestionNumber() != null) {
			examSectionHasQuestion.setQuestionNumber(examSectionHasQuestionVo.getQuestionNumber());
		}
		if (examSectionHasQuestionVo.getMarks() != null) {
			examSectionHasQuestion.setMarks(examSectionHasQuestionVo.getMarks());
		}
		if (examSectionHasQuestionVo.getActive() != null) {
			examSectionHasQuestion.setActive(examSectionHasQuestionVo.getActive());
		}
		if (examSectionHasQuestion.getExamSection() == null || (examSectionHasQuestionVo.getExamSectionVo() != null
				&& examSectionHasQuestionVo.getExamSectionVo().getExamSectionId() != null
				&& !examSectionHasQuestion.getExamSection().getExamSectionId()
						.equals(examSectionHasQuestionVo.getExamSectionVo().getExamSectionId()))) {
			examSectionHasQuestion.setExamSection(
					examSectionDao.read(examSectionHasQuestionVo.getExamSectionVo().getExamSectionId()));
		}
		if (examSectionHasQuestionVo.getNegativeMark() != null) {
			examSectionHasQuestion.setNegativeMark(examSectionHasQuestionVo.getNegativeMark());
		}
		examSectionHasQuestion.setLastModified(new Date());
		examSectionHasQuestion.setLastModifiedBy(new Long(userId));
		examSectionHasQuestionDao.update(examSectionHasQuestion);
		if(examSectionHasQuestionVo.getQuestionBankVo() != null) {
			questionBankService.updateQuestionBank(examSectionHasQuestionVo.getQuestionBankVo(), userId);
		}
		return examSectionHasQuestionVo.getExamSectionHasQuestionId();
	}

	@Override
	public ExamSectionHasQuestionVo readExamSectionHasQuestion(Integer examSectionHasQuestionId, Integer userId)
			throws Exception {
		if (examSectionHasQuestionId == null) {
			throw new Exception("examSectionHasQuestionId should not be null");
		}
		ExamSectionHasQuestion examSectionHasQuestion = examSectionHasQuestionDao.read(examSectionHasQuestionId);
		if (examSectionHasQuestion == null) {
			throw new Exception("Record not found");
		}
		return Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion);
	}

	@Override
	public Map<String, Object> listExamSectionHasQuestionVoBySectionId(Integer examSectionId, Integer pageNo,
			Integer pageSize, Integer userId) throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examSectionId", examSectionId);
		if (pageNo == null)
			pageNo = 50;
		if (pageSize == null)
			pageSize = 1;
		Integer startIndex = (pageNo * pageSize) - pageSize;
		List<ExamSectionHasQuestion> examSectionHasQuestionList = new ArrayList<>();
		Map<String, Object> examSectionHasQuestionResultSet = new HashMap<>();

		if (pageNo == 1) {
			examSectionHasQuestionList = examSectionHasQuestionDao.listEntityByParameter(
					ExamSectionHasQuestionDao.findAllBySectionId, paramsKayAndValues, null, null);
			examSectionHasQuestionResultSet.put("count", examSectionHasQuestionList.size());

			List<ExamSectionHasQuestionVo> examSectionHasQuestionVos = new ArrayList<>();
			examSectionHasQuestionList.stream().skip(startIndex).limit(pageSize).forEach(examSectionHasQuestion -> {
				examSectionHasQuestionVos
						.add(Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion));
			});
			examSectionHasQuestionResultSet.put("examSectionHasQuestionVoList", examSectionHasQuestionVos);

		} else {
			examSectionHasQuestionList = examSectionHasQuestionDao.listEntityByParameter(
					ExamSectionHasQuestionDao.findAllBySectionId, paramsKayAndValues, startIndex, pageSize);
			List<ExamSectionHasQuestionVo> examSectionHasQuestionVos = new ArrayList<>();
			examSectionHasQuestionList.stream().skip(startIndex).limit(pageSize).forEach(examSectionHasQuestion -> {
				examSectionHasQuestionVos
						.add(Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion));
			});
			examSectionHasQuestionResultSet.put("examSectionHasQuestionVoList", examSectionHasQuestionVos);

		}

		return examSectionHasQuestionResultSet;
	}

	@Override
	public Map<String, Object> listExamSectionHasQuestion(Integer pageNo, Integer pageSize, String searchKey,
			Boolean active, Integer userId) throws Exception {

		List<ExamSectionHasQuestion> examSectionHasQuestionList = new ArrayList<>();
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
			parameters.put("_2_examSectionHasQuestionName", searchKey);
			parameters.put("_1_active", active);
			examSectionHasQuestionList = examSectionHasQuestionDao.listEntityByParameter(
					ExamSectionHasQuestionDao.findAllBySearchKeyWithFilter, parameters, startIndex, pageSize);
		} else if (searchKey == null && active != null) {
			parameters.put("_1_active", active);
			examSectionHasQuestionList = examSectionHasQuestionDao
					.listEntityByParameter(ExamSectionHasQuestionDao.findAllByFilter, parameters, startIndex, pageSize);
		} else if (searchKey != null && active == null) {
			searchKey = "%" + searchKey + "%";
			parameters.put("_2_examSectionHasQuestionName", searchKey);
			examSectionHasQuestionList = examSectionHasQuestionDao.listEntityByParameter(
					ExamSectionHasQuestionDao.findAllBySearchKey, parameters, startIndex, pageSize);
		} else {
			examSectionHasQuestionList = examSectionHasQuestionDao
					.listEntityByParameter(ExamSectionHasQuestionDao.findAll, parameters, startIndex, pageSize);
		}
		Map<String, Object> examSectionHasQuestionResultSet = new HashMap<>();
		if (pageNo == 1) {
			startIndex = (pageNo * pageSize) - pageSize;
			examSectionHasQuestionResultSet.put("count", examSectionHasQuestionList.size());
			List<ExamSectionHasQuestionVo> examSectionHasQuestionVos = new ArrayList<>();
			examSectionHasQuestionList.stream().skip(startIndex).limit(pageSize).forEach(examSectionHasQuestion -> {
				examSectionHasQuestionVos
						.add(Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion));
			});
			examSectionHasQuestionResultSet.put("examSectionHasQuestionVoList", examSectionHasQuestionVos);
		} else {
			List<ExamSectionHasQuestionVo> examSectionHasQuestionVos = new ArrayList<>();
			examSectionHasQuestionList.stream().forEach(examSectionHasQuestion -> {
				examSectionHasQuestionVos
						.add(Transformer.EXAMSECTION_QUESTION_TRANSFORMER.transform(examSectionHasQuestion));
			});
			examSectionHasQuestionResultSet.put("examSectionHasQuestionVoList", examSectionHasQuestionVos);
		}

		return examSectionHasQuestionResultSet;
	}

	private void validationData(ExamSectionHasQuestionVo examSectionHasQuestionVo, ExamSection examSection)
			throws Exception {
		Map<String, Object> paramsKayAndValues = new HashMap<>();
		paramsKayAndValues.put("_1_examSectionId", examSectionHasQuestionVo.getExamSectionVo().getExamSectionId());
		List<ExamSectionHasQuestion> examSectionHasQuestions = examSectionHasQuestionDao.listEntityByParameter(
				ExamSectionHasQuestionDao.findAllShortResultBySectionId, paramsKayAndValues, null, null);
		if (examSection.getTotalQuestion() <= examSectionHasQuestions.size()) {
			throw new Exception("Total Question limit exceed");
		}
		Float totalMarks = examSection.getTotalMarks() - examSectionHasQuestionVo.getMarks();
		for (ExamSectionHasQuestion examSectionHasQuestion : examSectionHasQuestions) {
			totalMarks -= examSectionHasQuestion.getMarks();
			if (examSectionHasQuestionVo.getQuestionNumber().equals(examSectionHasQuestion.getQuestionNumber())) {
				throw new Exception("Question Number already exist");
			}
		}
		if (totalMarks < 0) {
			throw new Exception("Total marks exceed");
		}
	}

	private void validationField(ExamSectionHasQuestionVo examSectionHasQuestionVo) throws Exception {
		if (examSectionHasQuestionVo.getQuestionNumber() == null) {
			throw new Exception("QuestionNumber should not be null");
		}
		if (examSectionHasQuestionVo.getExamSectionVo() == null
				|| examSectionHasQuestionVo.getExamSectionVo().getExamSectionId() == null) {
			throw new Exception("ExamSectionId should not be null");
		}
		if (examSectionHasQuestionVo.getMarks() == null) {
			throw new Exception("Marks should not be null");
		}
	}

}
