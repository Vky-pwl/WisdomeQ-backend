package com.icat.quest.common.utils;

import com.icat.quest.common.vo.Level;

public class SkillAssessmentReport {
	
	private static final String hh = "Outstanding performance in both Aptitude and Technical areas\n" + 
			"and you did extremely well. Keep going the same way.";  
	
	private static final String hm = "Good performance in Aptitude but you have to concentrate &amp;\n" + 
			"improve in technical side.\n" + 
			"In technical section identify where you are weak and converge\n" + 
			"your energies on weaker areas rather than going on all areas.";  
	
	private static final String hl = "In aptitude you are good but you are poor technically. You\n" + 
			"have to put more efforts on technical skills rather than aptitude\n" + 
			"skills.\n" + 
			"In technical section identify your weaker areas through our\n" + 
			"report. To overcome it try to understand the concepts in each\n" + 
			"area and try to apply it to the problems and simultaneously\n" + 
			"practice as much as you can";  
	
	private static final String mh = "You are extremely good in Technical but only good in Aptitude.\n" + 
			"Identify where you are weak in aptitude and converge your\n" + 
			"energies on weaker areas rather than going on all areas.";  
	
	private static final String mm = "Good performance in both Aptitude &amp; Technical. But You have\n" + 
			"to work more in both areas. Identify where you are weak and\n" + 
			"converge your energies on weaker sections in both areas";  
	
	private static final String ml = "You are good in aptitude. Technically you are poor so you have\n" + 
			"to put more effort in technical area.\n" + 
			"In aptitude section identify where you are weak and converge\n" + 
			"your energies on weaker areas rather than going on all areas.\n" + 
			"In technical section identify your weaker areas through our\n" + 
			"report. To overcome it try to understand the concepts in each\n" + 
			"area and try to apply it to the problems and simultaneously\n" + 
			"practice as much as you can";  
	
	private static final String lh = "You are extremely good at Technical but your performance in\n" + 
			"aptitude is poor.\n" + 
			"Identify your weaker areas in technical section through our\n" + 
			"report and try to understand the concepts in each area and try\n" + 
			"to apply it to the problems and practice as much as you can";  
	
	private static final String lm = "Your hard work is not enough. You should improve your both\n" + 
			"skills. Aptitude wise you are poor but good technically.\n" + 
			"In aptitude identify your weaker areas through our report and\n" + 
			"try to understand the concepts in each area and how to apply it\n" + 
			"to the problems and practice as much as you can\n" + 
			"In technical front identify where you are weak and converge\n" + 
			"your energies on weaker areas rather than going on all areas."; 
	
	private static final String ll = "Both Aptitude &amp; Technical are very poor. You have to work a lot\n" + 
			"for better results. Plan, prepare and execute meticulously to\n" + 
			"get the desired result.\n" + 
			"For both areas identify your weaker areas through our report\n" + 
			"and try to understand the concepts in each area and try to\n" + 
			"apply it to the problems and also practice as much as you can";  
	
	public static String getSkillAssesstmentSkill(Level techLevel, Level aptiLevel) {
		if(techLevel == null || aptiLevel == null) {
			return null;
		}
		if(techLevel == Level.HIGH && aptiLevel == Level.HIGH) {
			return hh;
		}
		else if(techLevel == Level.HIGH && aptiLevel == Level.MEDIUM) {
			return hm;
		}
		else if(techLevel == Level.HIGH && aptiLevel == Level.LOW) {
			return hl;
		}
		else if(techLevel == Level.MEDIUM && aptiLevel == Level.HIGH) {
			return mh;
		}
		else if(techLevel == Level.MEDIUM && aptiLevel == Level.MEDIUM) {
			return mm;
		}
		else if(techLevel == Level.MEDIUM && aptiLevel == Level.LOW) {
			return ml;
		}
		else if(techLevel == Level.LOW && aptiLevel == Level.HIGH) {
			return lh;
		}
		else if(techLevel == Level.LOW && aptiLevel == Level.MEDIUM) {
			return lm;
		}
		else if(techLevel == Level.LOW && aptiLevel == Level.LOW) {
			return ll;
		}
		return "";
	}

	
}
