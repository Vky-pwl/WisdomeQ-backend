package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.QuestionDescriptionVo;
import com.icat.quest.model.QuestionDescription;

public class QuestionDescriptionTransformer implements Transformer<QuestionDescription, QuestionDescriptionVo> {

	@Override
	public QuestionDescriptionVo transform(QuestionDescription questionDescription) {
		if (questionDescription == null) {
			return null;
		}
		QuestionDescriptionVo questionDescriptionVo = new QuestionDescriptionVo();
		questionDescriptionVo.setDescriptionId(questionDescription.getDescriptionId());
		questionDescriptionVo.setDescriptionTextType(questionDescription.getDescriptionTextType());
		questionDescriptionVo.setDescriptionTextData(questionDescription.getDescription());
//		if (questionDescription.getDescriptionTextType() != null
//				&& questionDescription.getDescriptionTextType()) {
//			try {
//				questionDescriptionVo
//						.setDescriptionText(new String(questionDescription.getDescription(), "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			questionDescriptionVo.setDescriptionImage(questionDescription.getDescription());
//		}
		return questionDescriptionVo;
	}

}
