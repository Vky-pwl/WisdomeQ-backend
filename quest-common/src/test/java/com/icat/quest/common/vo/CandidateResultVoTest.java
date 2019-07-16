package com.icat.quest.common.vo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CandidateResultVoTest{
    CandidateResultVo candidateResultVo = new CandidateResultVo();

    @Before
    public void setup(){
        List<SectionResultVo> sectionResultVoList = new ArrayList();

        SectionResultVo sectionResultVo = new SectionResultVo();
        sectionResultVo.setExamSectionId(10);
        sectionResultVo.setExamSectionName(SectionName.English.name());
        sectionResultVo.setSectionName(SectionName.Quantitative.name());
        sectionResultVo.setTotalMarks(900);
        sectionResultVo.setUserTotalMarks(910.0f);
        sectionResultVo.setTotalCorrectAnswer(17);
        sectionResultVo.setTotalAttemptQuestion(18);
        sectionResultVo.setTotalQuestion(18);
        sectionResultVo.setPercentile(99.9f);
        sectionResultVoList.add(sectionResultVo);

        SectionResultVo sectionResultVo1 = new SectionResultVo();
        sectionResultVo1.setExamSectionId(11);
        sectionResultVo1.setExamSectionName(SectionName.Quantitative.name());
        sectionResultVo1.setSectionName(SectionName.Quantitative.name());
        sectionResultVo1.setTotalMarks(900);
        sectionResultVo1.setUserTotalMarks(390.0f);
        sectionResultVo1.setTotalCorrectAnswer(7);
        sectionResultVo1.setTotalAttemptQuestion(16);
        sectionResultVo1.setTotalQuestion(16);
        sectionResultVo1.setPercentile(24.0f);
        sectionResultVoList.add(sectionResultVo1);

        SectionResultVo sectionResultVo2 = new SectionResultVo();
        sectionResultVo2.setExamSectionId(11);
        sectionResultVo2.setExamSectionName(SectionName.Reasoning.name());
        sectionResultVo2.setSectionName(SectionName.Reasoning.name());
        sectionResultVo2.setTotalMarks(900);
        sectionResultVo2.setUserTotalMarks(380.0f);
        sectionResultVo2.setTotalCorrectAnswer(5);
        sectionResultVo2.setTotalAttemptQuestion(16);
        sectionResultVo2.setTotalQuestion(16);
        sectionResultVo2.setPercentile(23.0f);
        sectionResultVoList.add(sectionResultVo2);

        candidateResultVo.setSectionResultList(sectionResultVoList);
    }

    @Test
    public void testCalculatePercentile(){
        candidateResultVo.calculatePercentile();
        Assert.assertEquals(48.96f,candidateResultVo.getQuantitativePercentile(), 0.01f);
    }
}