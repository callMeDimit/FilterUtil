package com.dimit.impl;

import java.util.function.Predicate;
import java.util.function.Supplier;

import com.dimit.intereface.CallBack;
import com.dimit.intereface.Filte;

public class FilterImpl<T, R> implements Filte<T, R> {
	private Predicate<T> predicate;
	private CallBack<R> callBack;

	@Override
	public boolean check(T t) {
		return predicate.test(t);
	}

	@Override
	public R doFilter() {
		return (R) callBack.callBack(null);
	}

	@Override
	public R doFilter(Supplier<?> c) {
		return null;
	}

	/**
	 * 构造器
	 * @param t
	 * @param r
	 * @return
	 */
	public static <T, R,C> Filte<T, R> valueOf(Predicate<T> predicate, CallBack<R> callBack) {
		FilterImpl<T, R> filter = new FilterImpl<T, R>();
		filter.callBack = callBack;
		filter.predicate = predicate;
		return filter;
	}
}
