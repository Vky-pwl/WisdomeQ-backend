package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.model.ExamSectionHasQuestion;

public class ExamSectionHasQuestionTransformer
		implements Transformer<ExamSectionHasQuestion, ExamSectionHasQuestionVo> {

	@Override
	public ExamSectionHasQuestionVo transform(ExamSectionHasQuestion examSectionHasQuestion) {
		if (examSectionHasQuestion == null) {
			return null;
		}
		ExamSectionHasQuestionVo examSectionHasQuestionVo = new ExamSectionHasQuestionVo();
		examSectionHasQuestionVo.setExamSectionHasQuestionId(examSectionHasQuestion.getExamSectionHasQuestionId());
		examSectionHasQuestionVo.setMarks(examSectionHasQuestion.getMarks());
		examSectionHasQuestionVo.setQuestionNumber(examSectionHasQuestion.getQuestionNumber());
		examSectionHasQuestionVo.setActive(examSectionHasQuestion.getActive());
		examSectionHasQuestionVo.setNegativeMark(examSectionHasQuestion.getNegativeMark());
		examSectionHasQuestionVo.setExamSectionVo(
				Transformer.EXAM_SECTION_TRANSFORMER.transform(examSectionHasQuestion.getExamSection()));
		examSectionHasQuestionVo.setQuestionBankVo(
				Transformer.QUESTION_BANK_TRANSFORMER.transform(examSectionHasQuestion.getQuestionBank()));
		return examSectionHasQuestionVo;
	}

}
