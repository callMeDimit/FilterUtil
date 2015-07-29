package com.dimit.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import com.dimit.builder.FilterBuilder;
import com.dimit.intereface.CallBack;
import com.dimit.intereface.Filter;
import com.dimit.intereface.FilterChain;

/**
 * 责任链实现
 * @author dimit
 */
@SuppressWarnings("rawtypes")
public class FilterChainImpl implements FilterChain {
	
	/** 过滤器链 */
	private List<Filter> filters = new LinkedList<Filter>();

	@Override
	public <T, R> void addFilterByLast(Predicate<T> predicate, CallBack<R> callBack) {
		filters.add(FilterBuilder.builder(predicate, callBack));
	}

	@Override
	public <T, R> void addFilterByHead(Predicate<T> predicate, CallBack<R> callBack) {
		filters.add(0, FilterBuilder.builder(predicate, callBack));
	}

	@Override
	public <T, R> void addFilterByIndex(int idx, Predicate<T> predicate, CallBack<R> callBack) {
		if (idx < 0 || idx > filters.size()) {
			String msg = String.format("过滤器插入位置[%s]超出过滤器长度[%s]", new Object[] { idx, filters.size() });
			throw new IndexOutOfBoundsException(msg);
		}
		filters.add(idx, FilterBuilder.builder(predicate, callBack));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, R> R doFilter(T t) {
		for(Filter<T,R> f: filters) {
			if (f.check(t)) {
				return f.doFilter();
			}
		}
		return null;
	}


	//valueof ...
	/**
	 * 
	 * @param t
	 * @param r
	 * @return
	 */
	public static FilterChainImpl valueOf() {
		FilterChainImpl chain = new FilterChainImpl();
		return chain;
	}
	
	 
}
