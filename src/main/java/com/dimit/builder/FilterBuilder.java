package com.dimit.builder;

import java.util.function.Predicate;

import com.dimit.impl.FilterImpl;
import com.dimit.intereface.CallBack;
import com.dimit.intereface.Filte;

/**
 * 过滤器生成器
 * @author dimit
 */
public class FilterBuilder {
	/**
	 * 生成filter
	 * @param t
	 * @param r
	 * @return
	 */
	public static <T, R, C> Filte<T, R> builder(Predicate<T> predicate, CallBack<R> callBack) {
		return FilterImpl.valueOf(predicate, callBack);
	}
}
