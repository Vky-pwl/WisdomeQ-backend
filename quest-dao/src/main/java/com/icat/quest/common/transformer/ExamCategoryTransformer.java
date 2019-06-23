package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.ExamCategoryVo;
import com.icat.quest.model.ExamCategory;

public class ExamCategoryTransformer implements Transformer<ExamCategory, ExamCategoryVo> {

	@Override
	public ExamCategoryVo transform(ExamCategory examCategory) {
		if (examCategory == null) {
			return null;
		}
		ExamCategoryVo examCategoryVo = new ExamCategoryVo();
		if (examCategory.getParentExamCategoryId() != null) {
			examCategoryVo.setExamCategoryId(examCategory.getParentExamCategoryId());
			examCategoryVo.setExamCategoryName(examCategory.getParentExamCategoryName());
			examCategoryVo.setExamSubCategoryId(examCategory.getExamCategoryId());
			examCategoryVo.setExamSubCategoryName(examCategory.getExamCategoryName());
			examCategoryVo.setActive(examCategory.getActive());
		} else {
			examCategoryVo.setExamCategoryId(examCategory.getExamCategoryId());
			examCategoryVo.setExamCategoryName(examCategory.getExamCategoryName());
			examCategoryVo.setActive(examCategory.getActive());
		}
		return examCategoryVo;
	}

}
