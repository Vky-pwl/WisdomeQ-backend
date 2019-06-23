package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.ExamVo;
import com.icat.quest.model.Exam;

public class ExamTransformer implements Transformer<Exam, ExamVo> {
	

	@Override
	public ExamVo transform(Exam exam) {
		if (exam == null) {
			return null;
		}
		ExamVo examVo = new ExamVo();
		examVo.setExamId(exam.getExamId());
		examVo.setExamName(exam.getExamName());
		examVo.setTestCode(exam.getTestCode());
		examVo.setActive(exam.getActive());
		examVo.setExamDescriptionData(exam.getExamDescription());
		examVo.setExamInstructionsData(exam.getExamInstructions());
	/*	try {
			examVo.setExamDescription(new String(exam.getExamDescription(), "UTF-8"));
			examVo.setExamInstructions(new String(exam.getExamInstructions(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		examVo.setPublish(exam.getPublish());
		examVo.setTestCode(exam.getTestCode());
		examVo.setSectionCount(exam.getSectionCount());
		examVo.setAllowReattempts(exam.getAllowReattempts());
		examVo.setDurationInSeconds(exam.getDurationInSeconds());
		examVo.setNegativeMarking(exam.getNegativeMarking());
		examVo.setPassingPercentage(exam.getPassingPercentage());
		examVo.setQuestionCount(exam.getQuestionCount());
		examVo.setStatus(exam.getStatus());
		examVo.setTotalMarks(exam.getTotalMarks());
		examVo.setStartDate(exam.getStartDate() != null ? exam.getStartDate().getTime() : null);
		examVo.setEndDate(exam.getEndDate() != null ? exam.getEndDate().getTime() : null);
		examVo.setExamCategoryVo(Transformer.EXAM_CATEGORY_TRANSFORMER.transform(exam.getExamCategory()));
		examVo.setExamSettingsVo(Transformer.EXAM_HAS_SETTING_TRANSFORMER.transform(exam.getExamHasSettings()));	
		return examVo;
	}
	
}
