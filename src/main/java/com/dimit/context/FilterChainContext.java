package com.dimit.context;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.dimit.anno.Filter;
import com.dimit.builder.ChainBuilder;
import com.dimit.intereface.Filte;
import com.dimit.intereface.FilterChain;
import com.dimit.wrap.FilterChainWarp;
import com.dimit.wrap.FilterWarp;

/**
 * 过滤器链容器
 * @author dimit
 */
@SuppressWarnings("rawtypes")
public class FilterChainContext {
	private static final FilterChainContext INSTANCE = new FilterChainContext();
	private Map<String , FilterChainWarp> chainWarps;
	private Map<String , FilterChain> chains;

	public FilterChainContext() {
		chainWarps = new ConcurrentHashMap<String , FilterChainWarp>();
	}
	
	/**
	 * 初始化容器
	 */
	@SuppressWarnings("unchecked")
	public void init () {
		chains = new ConcurrentHashMap<String, FilterChain>();
		for(Entry <String , FilterChainWarp> entry : chainWarps.entrySet()) {
			FilterChain chain = chains.get(entry.getKey());
			if(chain == null) {
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
	public <T,R>void addFilter(Class<Filte<T,R>> clz) {
		Filter filter = clz.getAnnotation(Filter.class);
		if(null == filter) {
			return;
		}
		String chain = filter.chain();
		String name = filter.name();
		int idx = filter.index();
		FilterChainWarp chainWarp = chainWarps.get(chain);
		if(null == chainWarp) {
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
	public FilterChain getChain(String name){
		return chains.get(name);
	}

	/**
	 * 获取容器实例
	 * @return
	 */
	public static FilterChainContext getInstance() {
		return INSTANCE;
	}
}
