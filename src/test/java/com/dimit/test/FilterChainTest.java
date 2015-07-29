package com.dimit.test;

import java.util.function.Predicate;

import org.junit.Test;

import com.dimit.builder.ChainBuilder;
import com.dimit.intereface.CallBack;
import com.dimit.intereface.FilterChain;

public class FilterChainTest {

	@Test
	public void test() {
		FilterChain chain = ChainBuilder.build();
		Predicate<Integer> p1 = (u) -> u < 7;
		Predicate<Integer> p2 = (u) -> u < 5;
		CallBack<String> c1 = () -> "ok";
		CallBack<String> c2 = () -> "not ok";
		chain.addFilterByLast(p1, c1);
		chain.addFilterByLast(p2, c2);
		System.out.println(chain.doFilter(2));
	}

}
