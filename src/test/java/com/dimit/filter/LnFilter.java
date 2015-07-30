package com.dimit.filter;

import java.util.Map;

import com.dimit.anno.Filter;
import com.dimit.intereface.AbstractFilter;

/**
 * 老年检查
 * @author dimit
 */
@Filter(chain = "ageChain", name = "ln", index = -6)
public class LnFilter extends AbstractFilter<Integer, String> {
	@SuppressWarnings("unchecked")
	public LnFilter() {
		super.predicate = (t) -> {
			return t > 50;
		};
		super.callBack = (c) -> {
			if (c != null) {
				Map<String, String> map = (Map<String, String>) c.get();
				String name = map.get("zhangsan");
				return "老年" + name;
			}
			return "老年";
		};
	}
}
