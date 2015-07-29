package com.dimit.intereface;

import java.util.function.Predicate;

/**
 * 责任链接口
 * @author dimit
 */
public interface FilterChain {

	/**
	 * 在链尾增加过滤
	 * @param filter
	 */
	<T, R> void addFilterByLast(Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 在头部增加过滤器
	 * @param filter
	 */
	<T, R> void addFilterByHead(Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 在指定下标处增加过滤器
	 * @param idx
	 * @param filter
	 */
	<T, R> void addFilterByIndex(int idx, Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 执行责任链
	 * @return R
	 */
	<T, R> R doFilter(T t);
}
