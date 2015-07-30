package com.dimit.wrap;

import com.dimit.intereface.Filte;

/**
 * 过滤器包装类
 * @author dimit
 */
public class FilterWarp<T, R> {
	/** filter名称 */
	private String name;
	/** filter序号 */
	private int index;
	/** 过滤器字节码对象 */
	private Class<Filte<T, R>> clz;
	
	// GETTER...
	public String getName() {
		return name;
	}
	public int getIndex() {
		return index;
	}
	public Class<Filte<T, R>> getClz() {
		return clz;
	}
	
	//valueOf ...
	/**
	 * @param name
	 * @param index
	 * @param clz
	 * @return
	 */
	public static <T,R>FilterWarp <T,R> valueOf(String name, int index, Class<Filte<T, R>> clz) {
		FilterWarp<T,R> warp = new FilterWarp<T, R>();
		warp.name = name;
		warp.index = index;
		warp.clz = clz;
		return warp;
	}
	
}
