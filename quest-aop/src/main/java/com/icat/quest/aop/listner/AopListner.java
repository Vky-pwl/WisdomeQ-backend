package com.icat.quest.aop.listner;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icat.quest.common.vo.CandidateExamVo;
import com.icat.quest.dao.user.service.CandidateExamSummaryService;

@Component
@Aspect
public class AopListner {

	@Autowired
	private CandidateExamSummaryService candidateExamSummaryService;
	
	@After("(execution(* com.icat.quest.dao.user.service.CandidateExamService.createCandidateExam(..)) && args(candidateExamVo,..))")
	 public void runAfterCreateCandidateExam( CandidateExamVo candidateExamVo) throws Throwable {
	
		candidateExamSummaryService.saveUpdateCandidateExamSummary(candidateExamVo);
	
	     }
		


}
