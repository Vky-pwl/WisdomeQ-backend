package com.icat.quest.dao.user.service;

import java.util.List;

import com.icat.quest.common.vo.ExamSectionHasQuestionVo;
import com.icat.quest.common.vo.ExamStatusVo;

public interface ExamQuestionCacheService {

	ExamStatusVo getExamStatus(Integer examSectionHasQuestionId);

	void updateExamStatusBatchUpdate();

	void clearQuestionCacheJob();

	List<ExamSectionHasQuestionVo> getQuestionListByExamId(Integer examId);

	void updateExamStatus(ExamStatusVo examStatusVo);

	void removeExamStatus(Integer TCHTCId);

	void removeSectionQuestionIds(Integer TCHTCId);

	ExamSectionHasQuestionVo getQuestionAndUpdateStatus(Integer examId, Integer userId, Integer questionId,
			Long examRemainingTime, Long sectionRemainingTime, Integer TCHTCId);

	ExamSectionHasQuestionVo getQuestion(Integer userId, Integer examId, Long examRemainingTime,
			Long sectionRemainingTime, Boolean flag, Integer examSectionHasQuestionId, Integer TCHTCId);

	}
