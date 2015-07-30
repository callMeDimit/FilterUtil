package com.dimit.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.dimit.builder.FilterBuilder;
import com.dimit.intereface.CallBack;
import com.dimit.intereface.Filte;
import com.dimit.intereface.FilterChain;

/**
 * 责任链实现
 * @author dimit
 */
@SuppressWarnings("rawtypes")
public class FilterChainImpl implements FilterChain {
	
	/** 过滤器链 */
	private List<Filte> filters = new LinkedList<Filte>();

	@Override
	public <T, R,C> void addFilterByLast(Predicate<T> predicate, CallBack<R> callBack) {
		filters.add(FilterBuilder.builder(predicate, callBack));
	}

	@Override
	public <T, R,C> void addFilterByHead(Predicate<T> predicate, CallBack<R> callBack) {
		filters.add(0, FilterBuilder.builder(predicate, callBack));
	}

	@Override
	public <T, R,C> void addFilterByIndex(int idx, Predicate<T> predicate, CallBack<R> callBack) {
		if (idx < 0 || idx > filters.size()) {
			String msg = String.format("过滤器插入位置[%s]超出过滤器长度[%s]", new Object[] { idx, filters.size() });
			throw new IndexOutOfBoundsException(msg);
		}
		filters.add(idx, FilterBuilder.builder(predicate, callBack));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, R> R doFilter(T t, Supplier<?> ctx) {
		for(Filte<T,R> f: filters) {
			if (f.check(t)) {
				return f.doFilter(ctx);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, R> R doFilter(T t) {
		for(Filte<T,R> f: filters) {
			if (f.check(t)) {
				return f.doFilter();
			}
		}
		return null;
	}
	
	@Override
	public <T, R> void addFilters(List<Filte<T, R>> filters) {
		this.filters.addAll(filters);
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
