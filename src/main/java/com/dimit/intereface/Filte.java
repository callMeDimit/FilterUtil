package com.dimit.intereface;

import java.util.function.Supplier;



/**
 * 过滤器<验证值,返回值>
 * @author dimit
 */
public interface Filte <T, R>{
	/**
	 * 检查拦截是否生效
	 * @return true:进入拦截器 false:不进入拦截
	 */
	boolean check(T t);

	/**
	 * 进行拦截过滤
	 * @param callBack
	 * @return
	 */
	R doFilter();
	
	/**
	 * 进行拦截过滤(带执行上下文)
	 * @param c
	 * @return
	 */
	R doFilter(Supplier<?> c);
}
