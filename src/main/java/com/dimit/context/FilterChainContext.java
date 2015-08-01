package com.dimit.context;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dimit.anno.Filter;
import com.dimit.builder.ChainBuilder;
import com.dimit.builder.NameBuilder;
import com.dimit.exception.ChainNotFoundException;
import com.dimit.intereface.ChainFactory;
import com.dimit.intereface.Filte;
import com.dimit.intereface.FilterChain;
import com.dimit.intereface.LifeCycle;
import com.dimit.wrap.FilterChainWarp;
import com.dimit.wrap.FilterWarp;

/**
 * 过滤器链容器
 * @author dimit
 */
@SuppressWarnings("rawtypes")
public class FilterChainContext implements ChainFactory, LifeCycle {
	private static final FilterChainContext INSTANCE = new FilterChainContext();
	private Map<String, FilterChainWarp> chainWarps;
	private Map<String, FilterChain> chains;

	public FilterChainContext() {
		chainWarps = new ConcurrentHashMap<String, FilterChainWarp>();
	}

	/**
	 * 初始化容器
	 */
	@SuppressWarnings("unchecked")
	public void init() {
		chains = new ConcurrentHashMap<String, FilterChain>();
		for (Entry<String, FilterChainWarp> entry : chainWarps.entrySet()) {
			FilterChain chain = chains.get(entry.getKey());
			if (chain == null) {
				chain = ChainBuilder.build();
			}
			FilterChainWarp chainWarp = entry.getValue();
			chain.addFilters(chainWarp.getFilters());
			chains.put(entry.getKey(), chain);
		}
	}

	/**
	 * 增加filter
	 */
	@SuppressWarnings("unchecked")
	public <T, R> void addFilter(Class<Filte<T, R>> clz) {
		Filter filter = clz.getAnnotation(Filter.class);
		if (null == filter) {
			return;
		}
		String chain = filter.chain();
		String name = filter.name();
		int idx = filter.index();
		FilterChainWarp chainWarp = chainWarps.get(chain);
		if (null == chainWarp) {
			chainWarp = FilterChainWarp.valueOf(chain);
			FilterChainWarp pre = chainWarps.putIfAbsent(chain, chainWarp);
			chainWarp = pre == null ? chainWarp : pre;
		}
		chainWarp.addFilterWrap(FilterWarp.valueOf(name, idx, clz));
	}

	/**
	 * 获取责任链
	 * @param name
	 * @return
	 */
	public FilterChain getChainByName(String name) {
		return chains.get(name);
	}

	/**
	 * 获取容器实例
	 * @return
	 */
	public static ChainFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * 获取生命周期对象
	 * @return
	 */
	public static LifeCycle getLifeCycle() {
		return INSTANCE;
	}

	@Override
	public Set<String> getChainNames() {
		return chains.keySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getFilternames(String chainName) {
		FilterChainWarp chainWrap = chainWarps.get(chainName);
		if (chainWrap == null) {
			return Collections.EMPTY_SET;
		}
		return chainWrap.getFilterNames();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, R> void addFilterForChain(String chainName, int idx, Filte<T, R> filter, boolean isThorw) {
		FilterChain chain = chains.get(chainName);
		FilterChainWarp chainWrap = chainWarps.get(chainName);
		if (chain == null) {
			if (isThorw) {
				throw new ChainNotFoundException(String.format("名称为%s的链未找到", chainName));
			} else {
				chain = ChainBuilder.build();
				chains.put(chainName, chain);
				chainWrap = FilterChainWarp.valueOf(chainName);
				chainWarps.put(chainName, chainWrap);
			}
		}
		String filterName = NameBuilder.getName(chainName);
		FilterWarp warp = FilterWarp.valueOf(filterName, idx, (Class<Filte<T, R>>) filter.getClass());
		chainWrap.addFilterWrap(warp);
		chain.addFilterByIndex(idx, filter);
	}

	@Override
	public <T, R> void addFilterInTail(String chainName, Filte<T, R> filter) {

	}

	@Override
	public <T, R> void addFilterInHead(String chainName, Filte<T, R> filter) {

	}
}
