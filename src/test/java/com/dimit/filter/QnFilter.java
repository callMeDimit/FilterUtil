package com.dimit.filter;

import java.util.Map;
import java.util.function.Supplier;

import com.dimit.anno.Filter;
import com.dimit.intereface.Filte;

/**
 * 青年拦截器
 * @author dimit
 */
@Filter(chain = "ageChain", name = "qn")
public class QnFilter implements Filte<Integer, String> {

	@Override
	public boolean check(Integer t) {
		return t < 30 && t >= 20;
	}

	@Override
	public String doFilter() {
		return "青年";
	}

	@Override
	@SuppressWarnings("unchecked")
	public String doFilter(Supplier<?> c) {
		Map<String,String> map = (Map<String, String>) c.get();
		String name = map.get("zhangsan");
		return "青年" + name;
	}
}
