package com.dimit.impl;

import java.util.function.Predicate;

import com.dimit.intereface.CallBack;
import com.dimit.intereface.Filter;

public class FilterImpl <T, R> implements Filter<T, R>{
	private Predicate<T> predicate;
	private CallBack<R> callBack;

	@Override
	public boolean check(T t) {
		return predicate.test(t);
	}

	@Override
	public R doFilter() {
		return (R) callBack.callBack();
	}

	/**
	 * 构造器
	 * @param t
	 * @param r
	 * @return
	 */
	public static <T,R> Filter<T,R> valueOf(Predicate<T> predicate, CallBack<R> callBack) {
		FilterImpl<T,R> filter = new FilterImpl<T,R>();
		filter.callBack = callBack;
		filter.predicate = predicate;
		return filter;
	}
}
