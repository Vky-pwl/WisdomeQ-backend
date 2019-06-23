package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.QuestionCategoryVo;
import com.icat.quest.model.QuestionCategory;

public class QuestionCategoryTransformer implements Transformer<QuestionCategory, QuestionCategoryVo> {

	@Override
	public QuestionCategoryVo transform(QuestionCategory questionCategory) {
		if (questionCategory == null) {
			return null;
		}
		QuestionCategoryVo questionCategoryVo = new QuestionCategoryVo();
		if (questionCategory.getParentQuestionCategoryId() != null) {
			questionCategoryVo.setQuestionCategoryId(questionCategory.getParentQuestionCategoryId());
			questionCategoryVo.setQuestionCategoryName(questionCategory.getParentQuestionCategoryName());
			questionCategoryVo.setQuestionSubCategoryId(questionCategory.getQuestionCategoryId());
			questionCategoryVo.setQuestionSubCategoryName(questionCategory.getQuestionCategoryName());
			questionCategoryVo.setActive(questionCategory.getActive());
		} else {
			questionCategoryVo.setQuestionCategoryId(questionCategory.getQuestionCategoryId());
			questionCategoryVo.setQuestionCategoryName(questionCategory.getQuestionCategoryName());
			questionCategoryVo.setActive(questionCategory.getActive());
		}

		return questionCategoryVo;
	}

}
