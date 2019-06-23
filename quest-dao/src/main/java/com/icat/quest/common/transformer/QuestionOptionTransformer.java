package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.OptionVo;
import com.icat.quest.model.QuestionOption;

public class QuestionOptionTransformer implements Transformer<QuestionOption, OptionVo> {

	@Override
	public OptionVo transform(QuestionOption questionOption) {
		if (questionOption == null) {
			return null;
		}
		OptionVo optionVo = new OptionVo();
		optionVo.setMarks(questionOption.getMarks());
		optionVo.setOptionName(questionOption.getOptionName().name());
		optionVo.setOptionType(questionOption.getOptionType());
		optionVo.setOptionValueData(questionOption.getOptionValue());
		/*try {
			optionVo.setOptionValue(new String(questionOption.getOptionValue(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		optionVo.setQuestionOptionId(questionOption.getQuestionOptionId());
		return optionVo;
	}

}
