package com.dimit.wrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dimit.intereface.Filte;

/**
 * 链封装类
 * @author dimit
 */
public class FilterChainWarp<T, R> {
	/** 链名称 */
	private String name;
	/** 过滤器封装类集合 */
	private List<FilterWarp<T, R>> filterWraps;

	// logical...
	/**
	 * 增加一个filter封装类
	 * @param warp
	 */
	public void addFilterWrap(FilterWarp<T, R> warp) {
		filterWraps.add(warp);
	}

	/**
	 * 获取拦截器集合
	 * @return
	 */
	public List<Filte<T, R>> getFilters() {
		List<Filte<T, R>> filters = new ArrayList<Filte<T, R>>();
		Collections.sort(filterWraps, (s1, s2) -> {
			return s1.getIndex() - s2.getIndex();
		});
		filterWraps.forEach(u -> {
			try {
				Filte<T, R> filter = u.getClz().newInstance();
				filters.add(filter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return filters;
	}

	// getter ...
	public String getName() {
		return name;
	}

	public List<FilterWarp<T, R>> getFilterWraps() {
		return filterWraps;
	}

	// valueOf ...
	/**
	 * @param name
	 */
	public static <T, R> FilterChainWarp<T, R> valueOf(String name) {
		FilterChainWarp<T, R> warp = new FilterChainWarp<T, R>();
		warp.name = name;
		warp.filterWraps = new ArrayList<FilterWarp<T, R>>();
		return warp;
	}
}
