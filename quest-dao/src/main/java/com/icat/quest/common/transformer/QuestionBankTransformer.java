package com.icat.quest.common.transformer;

import java.util.Set;

import com.icat.quest.common.vo.OptionVo;
import com.icat.quest.common.vo.QuestionBankVo;
import com.icat.quest.model.QuestionBank;
import com.icat.quest.model.QuestionOption;

public class QuestionBankTransformer implements Transformer<QuestionBank, QuestionBankVo> {

	@Override
	public QuestionBankVo transform(QuestionBank questionBank) {
		if (questionBank == null) {
			return null;
		}
		QuestionBankVo questionBankVo = new QuestionBankVo();
		questionBankVo.setQuestionId(questionBank.getQuestionId());
		if(questionBank.getQuestionDescription() != null) {
			questionBankVo.setQuestionDescriptionVo(Transformer.QUESTION_DESCRP_TRANSFORMER.transform(questionBank.getQuestionDescription()));
		}
		questionBankVo.setQuestionStatmentData(questionBank.getQuestionStatment());
		questionBankVo.setActive(questionBank.getActive());
		questionBankVo.setLevel(questionBank.getLevel());
		/*try {
		questionBankVo.setQuestionStatment(new String(questionBank.getQuestionStatment(),"UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
		questionBankVo.setCorrectAnswer(questionBank.getCorrectAnswer());
				questionBankVo.setQuestionCategoryVo(
				Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(questionBank.getQuestionCategory()));
		Set<QuestionOption> questionOptions = questionBank.getQuestionOptions();
		questionOptions.forEach(questionOption->{
			OptionVo optionVo = Transformer.QUESTION_OPTION_TRANSFORMER.transform(questionOption);
			if(questionOption.getOptionName().equals(questionBank.getCorrectAnswer())) {
				questionBankVo.setCorrectOption(optionVo);
			}
			questionBankVo.getOptions().add(optionVo);
		});
		return questionBankVo;
	}

}
