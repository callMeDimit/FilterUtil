package com.dimit.intereface;


/**
 * 过滤器
 * @author dimit
 */
public interface Filter <T, R>{
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
}
