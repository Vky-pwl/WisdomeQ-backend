package com.icat.quest.common.transformer;

import com.icat.quest.common.vo.TestConductorLicenseVo;
import com.icat.quest.model.TestConductorLicense;

public class TestConductorLicenseTransformer implements Transformer<TestConductorLicense, TestConductorLicenseVo> {

	@Override
	public TestConductorLicenseVo transform(TestConductorLicense testConductorLicense) {
		if (testConductorLicense == null) {
			return null;
		}
		TestConductorLicenseVo testConductorLicenseVo = new TestConductorLicenseVo();
		testConductorLicenseVo.setTestConductorLicenseId(testConductorLicense.getTestConductorLicenseId());
		testConductorLicenseVo.setActive(testConductorLicense.getActive());
		testConductorLicenseVo.setLicenseCount(testConductorLicense.getLicenseCount());
		testConductorLicenseVo.setRemainingLicenseCount(testConductorLicense.getRemainingLicenseCount());
		testConductorLicenseVo.setExamVo(Transformer.EXAM_TRANSFORMER.transform(testConductorLicense.getExam()));
		testConductorLicenseVo.setTestConductorVo(
				Transformer.TEST_CONDUCTOR_TRANSFORMER.transform(testConductorLicense.getTestConductor()));
		testConductorLicenseVo.setParentTestConductorId(testConductorLicenseVo.getTestConductorVo().getParentTestConductorId());
		return testConductorLicenseVo;
	}

}
