package com.dimit.intereface;

import java.util.Set;

/**
 * 链工厂（定义链工具对外提供的服务接口）
 * @author dimit
 */
public interface ChainFactory {
	/**
	 * 通过链名称获取责任链
	 * @param chainName 链名称
	 * @return
	 */
	FilterChain getChainByName(String chainName);

	/**
	 * 获取所有的链名称
	 * @return
	 */
	Set<String> getChainNames();

	/**
	 * 获取指定链的所有名称
	 * @param chainName
	 * @return
	 */
	Set<String> getFilternames(String chainName);

	/**
	 * 动态向指定链的指定序号中增加拦截器
	 * @param chainName 链名称
	 * @param idx 加入的位置 (取值范围 idx >=0 并且idx < filters.size())
	 * @param filter 拦截器对象
	 * @param isThorw 在未找到指定链时是否抛出异常
	 * @throws ArrayIndexOutOfBoundsException
	 */
	<T, R> void addFilterForChain(String chainName, int idx, Filte<T, R> filter, boolean isThorw);

	/**
	 * 向指定链尾部加入拦截器
	 * @param chainName
	 * @param filter
	 */
	<T, R> void addFilterInTail(String chainName, Filte<T, R> filter);
	
	/**
	 * 增加使用注解的filter
	 * @param clz
	 */
	<T, R> void addFilter(Class<Filte<T, R>> clz);
	
	/**
	 * 向指定链头部加入拦截器
	 * @param chainName
	 * @param filter
	 */
	<T, R> void addFilterInHead(String chainName, Filte<T, R> filter);
	
	
}
