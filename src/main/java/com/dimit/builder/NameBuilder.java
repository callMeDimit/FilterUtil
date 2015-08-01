package com.dimit.builder;

import java.util.HashMap;
import java.util.Map;

import com.dimit.util.MapUtil;

/**
 * 过滤器名称生成器
 * @author dimit
 */
public class NameBuilder {
	public static final String SPLIT = "_";

	private static Map<String, Integer> record = new HashMap<>();

	/**
	 * 通过链名称获取自动filter名称
	 * @param chainName
	 * @return
	 */
	public static synchronized String getName(String chainName) {
		MapUtil.addValue(record, chainName, 1);
		int idx = MapUtil.getValue(record, chainName);
		return chainName + SPLIT + idx;
	}
}
