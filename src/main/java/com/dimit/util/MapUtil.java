package com.dimit.util;

import java.util.Map;

public class MapUtil {

	public static <K> void addValue(Map<K, Integer> map, K key, Integer value) {
		Integer oldvalue = getValue(map, key);
		map.put(key, oldvalue + value);
	}

	public static <K> int getValue(Map<K, Integer> map, K key) {
		Integer value = map.get(key);
		if (value == null) {
			return 0;
		} else {
			return value;
		}
	}

}
