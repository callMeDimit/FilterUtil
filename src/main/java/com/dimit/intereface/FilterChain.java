package com.dimit.intereface;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 责任链接口
 * @author dimit
 */
public interface FilterChain {

	/**
	 * 在链尾增加过滤
	 * @param filter
	 */
	<T, R, C> void addFilterByLast(Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 在头部增加过滤器
	 * @param filter
	 */
	<T, R, C> void addFilterByHead(Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 在指定下标处增加过滤器
	 * @param idx
	 * @param filter
	 */
	<T, R, C> void addFilterByIndex(int idx, Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 增加过滤器集合
	 * @param filters
	 */
	<T, R> void addFilters(List<Filte<T, R>> filters);

	/**
	 * 执行责任链(带执行上下文)
	 * @param t
	 * @param ctx
	 * @return R
	 */
	<T, R> R doFilter(T t, Supplier<?> ctx);

	/**
	 * 执行责任链
	 * @param t
	 * @return
	 */
	<T, R> R doFilter(T t);
}
