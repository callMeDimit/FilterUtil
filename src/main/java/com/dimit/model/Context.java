package com.dimit.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 运算参数上下文
 * @author dimit
 */
public class Context {
	/**参数集合[参数名:参数内容]*/
	private Map<String, Object> arguments = new HashMap<>();
	
	/**
	 * 增加参数
	 * @param name 参数名
	 * @param obj 参数对象
	 * @return
	 */
	public Context addArg(String name, Object obj) {
		this.arguments.put(name, obj);
		return this;
	}
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public Object getArg(String name) {
		return arguments.get(name);
	}

	/**
	 * 空构造函数
	 * @return
	 */
	public static Context empty() {
		return new Context();
	}

	/**
	 * 构造函数
	 * @param name
	 * @param obj
	 * @return
	 */
	public static Context valueOf(String name, Object obj) {
		Context ctx = new Context();
		ctx.addArg(name, obj);
		return ctx;
	}
}
