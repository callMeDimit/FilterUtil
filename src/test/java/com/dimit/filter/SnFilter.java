package com.dimit.filter;

import java.util.Map;
import java.util.function.Supplier;

import com.dimit.anno.Filter;
import com.dimit.intereface.Filte;
/**
 * 年龄过滤器
 * @author dimit
 */
@Filter(chain = "ageChain", name ="saonian", index = -2)
public class SnFilter implements Filte<Integer, String> {
	@Override
	public boolean check(Integer t) {
		return t < 20 && t>0;
	}
	
	@Override
	public String doFilter() {
		return "骚年";
	}

	@Override
	@SuppressWarnings("unchecked")
	public String doFilter(Supplier<?> c) {
		Map<String,String> map = (Map<String, String>) c.get();
		String name = map.get("zhangsan");
		return "骚年" + name;
	}
}
