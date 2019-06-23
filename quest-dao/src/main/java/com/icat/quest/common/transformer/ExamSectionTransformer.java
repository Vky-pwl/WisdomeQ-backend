package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.ExamSectionVo;
import com.icat.quest.model.ExamSection;

public class ExamSectionTransformer implements Transformer<ExamSection, ExamSectionVo> {

	@Override
	public ExamSectionVo transform(ExamSection examSection) {
		if (examSection == null) {
			return null;
		}
		ExamSectionVo examSectionVo = new ExamSectionVo();
		examSectionVo.setExamSectionId(examSection.getExamSectionId());
		examSectionVo.setExamSectionDescriptionData(examSection.getExamSectionDescription());
		examSectionVo.setExamSectionInstructionsData(examSection.getExamSectionInstructions());

/*		try {
			examSectionVo.setExamSectionDescription(new String(examSection.getExamSectionDescription(), "UTF-8"));
			examSectionVo.setExamSectionInstructions(new String(examSection.getExamSectionInstructions(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		examSectionVo.setDurationInSeconds(examSection.getDurationInSeconds());
		examSectionVo.setSequence(examSection.getSequence());
		examSectionVo.setTotalMarks(examSection.getTotalMarks());
		examSectionVo.setTotalQuestion(examSection.getTotalQuestion());
		examSectionVo.setActive(examSection.getActive());
		examSectionVo.setSicoFlag(examSection.getSicoFlag());
		examSectionVo.setQuestionCategoryVo(
				Transformer.QUESTION_CATEGORY_TRANSFORMER.transform(examSection.getQuestionCategory()));
		examSectionVo.setExamVo(Transformer.EXAM_TRANSFORMER.transform(examSection.getExam()));
		examSectionVo.setSectionName(examSection.getSectionName().name());
		return examSectionVo;
	}

}
