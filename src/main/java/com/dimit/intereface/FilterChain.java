package com.dimit.intereface;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.dimit.exception.ChainNotFoundException;

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
	 * 在链尾添加过滤器
	 * @param filter 过滤器
	 * @param name 过滤器名称
	 * @param isThrow 是否抛出异常
	 * @throws ChainNotFoundException
	 */
	<T, R> void addFilterByLast(Filte<T,R> filter);

	/**
	 * 在头部增加过滤器
	 * @param filter
	 */
	<T, R> void addFilterByHead(Predicate<T> predicate, CallBack<R> callBack);
	
	/**
	 * 
	 * @param filter
	 */
	<T,R> void addFilterByHead(Filte<T,R> filter);

	/**
	 * 在指定下标处增加过滤器
	 * @param idx
	 * @param predicate
	 * @param callBack
	 */
	<T, R> void addFilterByIndex(int idx, Predicate<T> predicate, CallBack<R> callBack);

	/**
	 * 在指定位置加入过滤器
	 * @param idx
	 * @param filter
	 */
	<T, R> void addFilterByIndex(int idx, Filte<T, R> filter);

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
