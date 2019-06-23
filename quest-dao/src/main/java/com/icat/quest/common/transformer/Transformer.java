package com.icat.quest.common.transformer;

public interface Transformer<S,T> {
	
	T transform(S source);

	CollegeTransformer COLLEGE_TRANSFORMER = new CollegeTransformer();
	ExamCategoryTransformer EXAM_CATEGORY_TRANSFORMER = new ExamCategoryTransformer();
	SpecializationTransformer SPECIALIZATION_TRANSFORMER = new SpecializationTransformer();
	QuestionCategoryTransformer QUESTION_CATEGORY_TRANSFORMER = new QuestionCategoryTransformer();
	PermissionTransformer PERMISSION_TRANSFORMER = new PermissionTransformer();
	CandidateTransformer CANDIDATE_TRANSFORMER = new CandidateTransformer();
	ExamTransformer EXAM_TRANSFORMER = new ExamTransformer();
	ExamSectionTransformer EXAM_SECTION_TRANSFORMER = new ExamSectionTransformer();
	UserHasPermissionTransformer USER_PERMISSION_TRANSFORMER = new UserHasPermissionTransformer();
	QuestionBankTransformer QUESTION_BANK_TRANSFORMER = new QuestionBankTransformer();
	ExplanationTransformer EXPLANATION_TRANSFORMER = new ExplanationTransformer();
	ExamSectionHasQuestionTransformer EXAMSECTION_QUESTION_TRANSFORMER = new ExamSectionHasQuestionTransformer();
	TestConductorTransformer TEST_CONDUCTOR_TRANSFORMER = new TestConductorTransformer();
	TestConductorLicenseTransformer TESTCONDUCTOR_LICENSE_TRANSFORMER = new TestConductorLicenseTransformer();
	TestConductorTestCodeTransformer TESTCONDUCTOR_TESTCODE_TRANSFORMER = new TestConductorTestCodeTransformer();
	QuestionOptionTransformer QUESTION_OPTION_TRANSFORMER = new QuestionOptionTransformer();
	QuestionDescriptionTransformer QUESTION_DESCRP_TRANSFORMER = new QuestionDescriptionTransformer();
	ExamHasSettingsTransformer EXAM_HAS_SETTING_TRANSFORMER = new ExamHasSettingsTransformer();
}
