package com.icat.quest.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomMap {
	

	public static Map<Integer, Integer> getMap(List<Integer> examSectionHasQuestionIdList) {
		Map<Integer, Integer> map = new HashMap<>();
		Collections.shuffle(examSectionHasQuestionIdList);
		int count = 0;
		for (Integer id : examSectionHasQuestionIdList) {
			map.put(++count, id);
		}

		return map;
	}

}
