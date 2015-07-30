package com.dimit.filter;

import com.dimit.anno.Filter;
import com.dimit.intereface.AbstractFilter;

/**
 * 中年过滤器
 * @author dimit
 */
@Filter(chain = "ageChain", name = "zn", index = 5)
public class ZnFilter extends AbstractFilter<Integer, String> {
	public ZnFilter() {
		super.predicate = (t) -> {
			return t >= 30 && t < 50;
		};
		super.callBack = (c) ->{
			return "中年";
		};
	}
}
