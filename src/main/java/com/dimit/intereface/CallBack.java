package com.dimit.intereface;

import java.util.function.Supplier;

/**
 * 过滤器回调
 * @author dimit
 */
@FunctionalInterface
public interface CallBack<R> {
	/**
	 * 执行回调
	 * @return
	 */
	R callBack(Supplier<?> c);
}
