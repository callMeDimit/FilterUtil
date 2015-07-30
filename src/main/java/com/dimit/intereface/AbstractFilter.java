package com.dimit.intereface;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 抽象过滤器
 * @author dimit
 */
public abstract class AbstractFilter<T, R> implements Filte<T, R> {
	/** 检查代码块*/
	protected Predicate<T> predicate;
	/** 回调代码块*/
	protected CallBack<R> callBack;

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
		return callBack.callBack(c);
	}
}
