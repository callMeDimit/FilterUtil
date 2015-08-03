package com.dimit.test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

import com.dimit.builder.ChainBuilder;
import com.dimit.builder.FilterBuilder;
import com.dimit.context.FilterChainContext;
import com.dimit.intereface.CallBack;
import com.dimit.intereface.ChainFactory;
import com.dimit.intereface.Filte;
import com.dimit.intereface.FilterChain;
import com.dimit.util.ClassScanHelper;

public class FilterChainTest {

	@Test
	public void test() {
		FilterChain chain = ChainBuilder.build();
		Predicate<Integer> p1 = (u) -> u < 7;
		Predicate<Integer> p2 = (u) -> u < 5;
		CallBack<String> c1 = (t) -> {
			return "ok";
		};
		CallBack<String> c2 = (t) -> {
			return "not ok";
		};
		chain.addFilterByLast(p1, c1);
		chain.addFilterByLast(p2, c2);
		System.out.println(chain.doFilter(5));
	}

	@Test
	public void classScanHelperTest() {
		ClassScanHelper.init();
		ChainFactory context = FilterChainContext.getInstance();
		FilterChain ageChain = context.getChainByName("ageChain");
		System.out.println(ageChain.doFilter(10));
	}

	@Test
	public void contextTest() {
		ClassScanHelper.init();
		ChainFactory context = FilterChainContext.getInstance();
		FilterChain ageChain = context.getChainByName("ageChain");
		Supplier<Map<String, String>> s = () -> {
			Map<String, String> map = new HashMap<String, String>();
			map.put("zhangsan", "张三");
			return map;
		};
		System.out.println(ageChain.doFilter(70, s));
	}

	/**
	 * 动态增加过滤器测试
	 */
	@Test
	public void dymicAddFilterTest() {
		ClassScanHelper.init();
		ChainFactory context = FilterChainContext.getInstance();
		Predicate<Integer> p1 = (u) -> u < 7;
		Predicate<Integer> p2 = (u) -> u < 5;
		CallBack<String> c1 = (t) -> {
			System.out.println("ok");
			return "ok";
		};
		CallBack<String> c2 = (t) -> {
			System.out.println("not OK");
			return "not ok";
		};
		Filte<Integer, String> filter = FilterBuilder.builder(p1, c1);
		Filte<Integer, String> filter2 = FilterBuilder.builder(p2, c2);
		context.addFilterForChain("test", 0, filter, "test1", false);
		context.addFilterInHead("test", filter2, false);
		context.getChainByName("test").doFilter(4);
		System.out.println(context.getChainNames());
		System.out.println(context.getFilternames("ageChain"));
	}
}
