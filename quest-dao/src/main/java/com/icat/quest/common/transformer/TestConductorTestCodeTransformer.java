package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.TestConductorHasTestCodeVo;
import com.icat.quest.model.TestConductorHasTestCode;

public class TestConductorTestCodeTransformer
		implements Transformer<TestConductorHasTestCode, TestConductorHasTestCodeVo> {

	@Override
	public TestConductorHasTestCodeVo transform(TestConductorHasTestCode testConductorHasTestCode) {
		if (testConductorHasTestCode == null) {
			return null;
		}
		TestConductorHasTestCodeVo testConductorHasTestCodeVo = new TestConductorHasTestCodeVo();
		testConductorHasTestCodeVo
				.setTestConductorHasTestCodeId(testConductorHasTestCode.getTestConductorHasTestCodeId());
		testConductorHasTestCodeVo.setTestConductorLicenseId(
				testConductorHasTestCode.getTestConductorLicense().getTestConductorLicenseId());
		testConductorHasTestCodeVo
				.setUserVo(Transformer.CANDIDATE_TRANSFORMER.transform(testConductorHasTestCode.getUser()));
		testConductorHasTestCodeVo.setExamStartDate(testConductorHasTestCode.getExamStartDate() != null
				? testConductorHasTestCode.getExamStartDate().getTime()
				: null);
		testConductorHasTestCodeVo.setExamVo(Transformer.EXAM_TRANSFORMER.transform(testConductorHasTestCode.getExam()));
		testConductorHasTestCodeVo.setAttended(testConductorHasTestCode.getAttended());
		testConductorHasTestCodeVo.setExamId(testConductorHasTestCode.getExam().getExamId());
		testConductorHasTestCodeVo.setActive(testConductorHasTestCode.getActive());
		testConductorHasTestCodeVo.setTestCode(testConductorHasTestCode.getTestCode());
		testConductorHasTestCodeVo.setTotalAttempts(testConductorHasTestCode.getTotalAttempts());
		testConductorHasTestCodeVo.setTotalMarks(testConductorHasTestCode.getTotalMarks());
		testConductorHasTestCodeVo.setTotalQuestion(testConductorHasTestCode.getTotalQuestion());
		testConductorHasTestCodeVo.setUserTotalMarks(testConductorHasTestCode.getUserTotalMarks());
		testConductorHasTestCodeVo.setStatus(testConductorHasTestCode.getStatus());
		return testConductorHasTestCodeVo;
	}

}
