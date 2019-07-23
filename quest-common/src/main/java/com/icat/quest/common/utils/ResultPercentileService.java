package com.icat.quest.common.utils;

import java.util.HashMap;
import java.util.Map;

import com.icat.quest.common.vo.Level;
import com.icat.quest.common.vo.SectionName;

public class ResultPercentileService {


	enum Grade {
		A, B, C
	}
	
	
	public static Map<String, Object> getPercentile(String sectionName, float totalMarks) {
		Map<String, Object> result = new HashMap<>();
		float percentile = 0;
		if (SectionName.Quantitative.name().equals(sectionName)) {
			percentile = getAptiSectionPercentile(totalMarks);
		} else {
			percentile = getOtherSectionPercentile(totalMarks);
		}
		result.put("percentile", percentile);
		if (percentile >= 65) {
			result.put("grade", Grade.A);
			result.put("level", Level.HIGH);
		} else if (percentile < 65 && percentile >= 40) {
			result.put("grade", Grade.B);
			result.put("level", Level.MEDIUM);
		} else {
			result.put("grade", Grade.C);
			result.put("level", Level.LOW);
		}
		return result;
	}

	private static float getAptiSectionPercentile(float totalMarks) {
		float percentile = 0;

		// 1-300 btw 1 and 10
		if (totalMarks >= 1 && totalMarks < 300) {
			// 10/300
			return percentile = 1 + (totalMarks - 1) * ((float) 10 / 300);
		} // 300-350 between 10 to 20
		else if (totalMarks >= 300 && totalMarks < 350) {
			// 10/50
			return percentile = 10 + (totalMarks - 300) * ((float) 10 / 50);
		} // 350-400 between 20 and 25
		else if (totalMarks >= 350 && totalMarks < 400) {
			// 5/50
			return percentile = 20 + (totalMarks - 350) * ((float) 5 / 50);
		} // 400-450 between 25 to 40
		else if (totalMarks >= 400 && totalMarks < 450) {
			// 15/50
			return percentile = 25 + (totalMarks - 400) * ((float) 15 / 50);
		} // 450-500 between 40 to 50
		else if (totalMarks >= 450 && totalMarks < 500) {
			// 10/50
			return percentile = 40 + (totalMarks - 450) * ((float) 10 / 50);
		} // 500-520 between 50 to 60
		else if (totalMarks >= 500 && totalMarks < 520) {
			// 10/20
			return percentile = 50 + (totalMarks - 500) * ((float) 10 / 20);
		} // 520- 550 between 60 to 70
		else if (totalMarks >= 520 && totalMarks < 550) {
			// 10/30
			return percentile = 60 + (totalMarks - 520) * ((float) 10 / 30);
		} // 550-600 between 70 to 80
		else if (totalMarks >= 550 && totalMarks < 600) {
			// 10/50
			return percentile = 70 + ((totalMarks - 550) * ((float) 10 / 50));
		} // 600- 650 between 80 to 90
		else if (totalMarks >= 600 && totalMarks < 650) {
			// 10/50
			return percentile = 80 + (totalMarks - 600) * ((float) 10 / 50);
		} // 650- 670 between 90 to 92
		else if (totalMarks >= 650 && totalMarks < 670) {
			// 10/20
			return percentile = 90 + (totalMarks - 650) * ((float) 10 / 20);
		} // 670- 700 between 92 to 95
		else if (totalMarks >= 670 && totalMarks < 700) {
			// 3/30
			return percentile = 92 + (totalMarks - 670) * ((float) 3 / 30);
		} // 700-750 between 95 to 99
		else if (totalMarks >= 700 && totalMarks < 750) {
			// 4/50
			return percentile = 95 + (totalMarks - 700) * ((float) 4 / 50);
		} else if (totalMarks >= 750) {
			return 99.9f;
		}
		return percentile;
	}

	private static float getOtherSectionPercentile(float totalMarks) {
		float percentile = 0;

		// 1-300 btw 1 and 5
		if (totalMarks >= 1 && totalMarks < 300) {
			// 5/300
			return percentile = 1 + (totalMarks - 1) * ((float) 5 / 300);
		} // 300-350 between 5 to 15
		else if (totalMarks >= 300 && totalMarks < 350) {
			// 10/50
			return percentile = 5 + (totalMarks - 300) * ((float) 10 / 50);
		} // 350-400 between 15 and 20
		else if (totalMarks >= 350 && totalMarks < 400) {
			// 5/50
			return percentile = 15 + (totalMarks - 350) * ((float) 5 / 50);
		} // 400-440 between 20 to 30
		else if (totalMarks >= 400 && totalMarks < 440) {
			// 10/40
			return percentile = 20 + (totalMarks - 400) * ((float) 10 / 40);
		} // 440-450 between 30 to 40
		else if (totalMarks >= 440 && totalMarks < 450) {
			// 10/10
			return percentile = 30 + (totalMarks - 440) * ((float) 10 / 10);
		} // 450-470 between 45 to 50
		else if (totalMarks >= 450 && totalMarks < 470) {
			// 5/20
			return percentile = 45 + (totalMarks - 450) * ((float) 5 / 20);
		} // 470- 500 between 50 to 60
		else if (totalMarks >= 470 && totalMarks < 500) {
			// 10/30
			return percentile = 50 + (totalMarks - 470) * ((float) 10 / 30);
		} // 500-550 between 60 to 80
		else if (totalMarks >= 500 && totalMarks < 550) {
			// 20/50
			return percentile = 60 + (totalMarks - 500) * ((float) 20 / 50);
		} // 550- 600 between 80 to 90
		else if (totalMarks >= 550 && totalMarks < 600) {
			// 10/50
			return percentile = 80 + (totalMarks - 550) * ((float) 10 / 50);
		} // 600- 650 between 90 to 95
		else if (totalMarks >= 600 && totalMarks < 650) {
			// 5/50
			return percentile = 90 + (totalMarks - 600) * ((float) 5 / 20);
		} // 650- 690 between 95 to 99.99
		else if (totalMarks >= 650 && totalMarks < 690) {
			// 4.99/40
			return percentile = (float) (95 + (totalMarks - 650) * ((float) 4.99 / 40));
		} else if (totalMarks >= 690) {
			return 99.9f;
		}
		return percentile;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(getPercentile("Quantitative", 570));
	 * System.out.println(getPercentile("Reasoning", 660));
	 * 
	 * 
	 * }
	 */
}
