package com.dimit.builder;

import com.dimit.impl.FilterChainImpl;
import com.dimit.intereface.FilterChain;

/**
 * 责任链生成器
 * @author dimit
 */
public class ChainBuilder {
	/**
	 * 构建链
	 * @param t
	 * @param r
	 * @return
	 */
	public static FilterChain build() {
		return FilterChainImpl.valueOf();
	}
}
