package com.dimit.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标识一个过滤器
 * @author dimit
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Filter {
	/** 所属链名称 */
	String chain();

	/** 过滤器名称 */
	String name() default "";

	/** 在链中的序号 (-1代表在末尾进行添加) */
	int index() default -1;
}
