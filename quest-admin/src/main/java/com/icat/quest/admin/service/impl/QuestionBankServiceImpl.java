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

import com.icat.quest.admin.service.QuestionBankService;
import com.icat.quest.common.transformer.Transformer;
import com.icat.quest.common.vo.OptionVo;
import com.icat.quest.common.vo.QuestionBankVo;
import com.icat.quest.dao.QuestionBankDao;
import com.icat.quest.dao.QuestionCategoryDao;
import com.icat.quest.dao.QuestionDescriptionDao;
import com.icat.quest.dao.QuestionOptionDao;
import com.icat.quest.model.QuestionBank;
import com.icat.quest.model.QuestionDescription;
import com.icat.quest.model.QuestionOption;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {

	@Autowired
	private QuestionBankDao questionBankDao;
	@Autowired
	private QuestionCategoryDao questionCategoryDao;

	@Autowired
	private QuestionDescriptionDao questionDescriptionDao;
	@Autowired
	private QuestionOptionDao questionOptionDao;

	@Override
	public Integer createQuestionBank(QuestionBankVo questionBankVo, Integer userId) {
		return questionBankDao.create(transformQuestionBank(questionBankVo, userId));
	}

	@Override
	public QuestionBank transformQuestionBank(QuestionBankVo questionBankVo, Integer userId) {
		QuestionBank questionBank = new QuestionBank();
		questionBank.setActive(true);
		questionBank.setCreated(new Date());
		questionBank.setCreatedBy(new Long(userId));
		questionBank.setLastModified(new Date());
		questionBank.setLastModifiedBy(new Long(userId));
		questionBank.setLevel(questionBankVo.getLevel());
		questionBank.setQuestionStatment(
				questionBankVo.getQuestionStatment() != null ? questionBankVo.getQuestionStatment().getBytes()
						: new byte[0]);

		questionBank.setCorrectAnswer(questionBankVo.getCorrectAnswer());

		if (questionBankVo.getQuestionCategoryVo() != null
				&& questionBankVo.getQuestionCategoryVo().getQuestionSubCategoryId() != null) {
			questionBank.setQuestionCategory(
					questionCategoryDao.read(questionBankVo.getQuestionCategoryVo().getQuestionSubCategoryId()));
		}
		return questionBank;
	}

	@Override
	public Integer updateQuestionBank(QuestionBankVo questionBankVo, Integer userId) throws Exception {
		if (questionBankVo.getQuestionId() == null)
			throw new Exception("questionId should not be null");

		QuestionBank questionBank = questionBankDao.read(questionBankVo.getQuestionId());
		if (questionBank == null)
			throw new Exception("Record not found");

		if (questionBankVo.getQuestionStatment() != null) {
			questionBank.setQuestionStatment(questionBankVo.getQuestionStatment().trim().getBytes());
		}
		if (questionBankVo.getActive() != null && !questionBank.getActive().equals(questionBankVo.getActive())) {
			questionBank.setActive(questionBankVo.getActive());
		}

		if(questionBankVo.getLevel() != null) {
			questionBank.setLevel(questionBankVo.getLevel());
		}
		
		if (questionBankVo.getCorrectAnswer() != null) {
			questionBank.setCorrectAnswer(questionBankVo.getCorrectAnswer());
		}
		if (questionBankVo.getQuestionCategoryVo() != null
				&& questionBankVo.getQuestionCategoryVo().getQuestionSubCategoryId() != null
				&& (questionBank.getQuestionCategory() == null
						|| !questionBankVo.getQuestionCategoryVo().getQuestionSubCategoryId()
								.equals(questionBank.getQuestionCategory().getQuestionCategoryId()))) {
			questionBank.setQuestionCategory(
					questionCategoryDao.read(questionBankVo.getQuestionCategoryVo().getQuestionSubCategoryId()));
		}
		if (questionBankVo.getQuestionDescriptionVo() != null
				&& questionBankVo.getQuestionDescriptionVo().getDescriptionText() != null) {
			if (questionBank.getQuestionDescription() != null) {
				QuestionDescription questionDescription = questionBank.getQuestionDescription();
		
				questionDescription.setDescription(questionBankVo.getQuestionDescriptionVo().getDescriptionText().getBytes());
				questionDescription.setDescriptionTextType(true);
				questionDescriptionDao.update(questionDescription);
			} else {
				QuestionDescription questionDescription = new QuestionDescription(
						questionBankVo.getQuestionDescriptionVo().getDescriptionText().getBytes(), true, true,
						new Date(), new Long(userId), new Date(), new Long(userId));
				Integer id = questionDescriptionDao.create(questionDescription);
				if (id != null) {
					questionBank.setQuestionDescription(questionDescription);
				}
			}
		}
		
		if(questionBankVo.getOptions() != null && questionBankVo.getOptions().size()>0) {
			List<OptionVo> optionVos = questionBankVo.getOptions();
			if(optionVos != null) {
				List<QuestionOption> questionOptions = new ArrayList<>();
			for(OptionVo optionVo : optionVos) {
				if(optionVo.getQuestionOptionId() != null) {
			QuestionOption questionOption = questionOptionDao.read(optionVo.getQuestionOptionId());
			questionOption.setMarks(optionVo.getMarks());
			questionOption.setOptionName(optionVo.getOptionName());
			if(optionVo.getOptionValue() != null)
			questionOption.setOptionValue(optionVo.getOptionValue().getBytes());
			questionOption.setOptionType(true);
			questionOptions.add(questionOption);
				}
			}
			if(questionOptions.size()>0) {
				questionOptionDao.updateBatch(questionOptions);
			}
			}
			
		}
	
		questionBank.setLastModified(new Date());
		questionBank.setLastModifiedBy(new Long(userId));
		questionBankDao.update(questionBank);
		return questionBankVo.getQuestionId();
	}

	@Override
	public QuestionBankVo readQuestionBank(Integer questionId, Integer userId) throws Exception {
		if (questionId == null) {
			throw new Exception("questionId should not be null");
		}

		QuestionBank questionBank = questionBankDao.read(questionId);
		if (questionBank == null) {
			throw new Exception("Record not found");
		}
		return Transformer.QUESTION_BANK_TRANSFORMER.transform(questionBank);
	}

	@Override
	public Map<String, Object> listQuestionBank(Integer pageNo, Integer pageSize, String searchKey, Boolean active,
			Integer userId, Integer questionCategoryId, Integer questionSubCategoryId, Integer questionExamType) throws Exception {
		if (pageNo == null) {
			pageNo = 50;
		}
		if (pageSize == null) {
			pageSize = 1;
		}
		if (searchKey != null) {
			searchKey = "%" + searchKey + "%";
		}
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("_1_QuestionSubCategoryId", questionSubCategoryId);
		parameters.put("_2_QuestionCategoryId", questionCategoryId);
		parameters.put("_3_searchKey", searchKey);
		parameters.put("_4_questionExamType", questionExamType);
		@SuppressWarnings("unchecked")
		List<Integer> questionIdList = (List<Integer>) questionBankDao
				.listSingleRowResult(QuestionBankDao.findByFilterCriteria, parameters);

		Integer startIndex = (pageNo * pageSize) - pageSize;
		Map<String, Object> questionBankResultSet = new HashMap<>();
		if (pageNo == 1) {
			questionBankResultSet.put("count", questionIdList.size());
		}
		questionIdList = questionIdList.stream().skip(startIndex).limit(pageSize).collect(Collectors.toList());
		List<QuestionBank> questionBankList = questionBankDao.listEntityByIdList(questionIdList);
		List<QuestionBankVo> questionBankVos = new ArrayList<>();
		questionBankList.stream().forEach(questionBank -> {
			questionBankVos.add(Transformer.QUESTION_BANK_TRANSFORMER.transform(questionBank));
		});
		questionBankResultSet.put("questionBankVoList", questionBankVos);

		return questionBankResultSet;
	}

}
