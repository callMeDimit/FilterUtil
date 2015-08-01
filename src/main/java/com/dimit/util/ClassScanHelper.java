package com.dimit.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dimit.constant.PropertiesConstant;
import com.dimit.context.FilterChainContext;
import com.dimit.intereface.ChainFactory;
import com.dimit.intereface.Filte;
import com.dimit.search.resolver.PathMatchingResourcePatternResolver;
import com.dimit.search.resolver.ResourcePatternResolver;
import com.dimit.search.resource.Resource;
import com.dimit.search.util.ClassUtils;
import com.dimit.search.util.StringUtils;
import com.dimit.search.util.SystemPropertyUtils;

/**
 * 类扫描工具类
 * @author dimit
 */
public class ClassScanHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassScanHelper.class);
	private static final ClassLoader CLASS_LOADER = ClassUtils.getDefaultClassLoader();
	private static final String BASE_PATH = baseName(CLASS_LOADER);
	private static final Properties CONFIG = loadProperties();
	/** 资源搜索分析器 */
	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	/** 默认资源匹配符 */
	protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	/**
	 * 初始化
	 */
	public static void init() {
		try {
			String key = CONFIG.getProperty(PropertiesConstant.PACKAGE_KEY);
			if (!StringUtils.hasText(key)) {
				LOGGER.info("没有找到配置项[{}]。自动扫描annotation功能未开启", PropertiesConstant.PACKAGE_KEY);
				return;
			}
			String[] packages = CONFIG.getProperty(PropertiesConstant.PACKAGE_KEY).split(",");
			for (String packageStr : packages) {
				String path = resourcePatternResolver.resolveBasePackage(packageStr);
				Resource[] resources = resourcePatternResolver.getResources(path);
				for (Resource resource : resources) {
					scanFilter(resource.getFile());
				}
			}
			FilterChainContext.getLifeCycle().init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 加载配置文件
	 * @return
	 */
	private static Properties loadProperties() {
		InputStream stream = null;
		if (CLASS_LOADER != null) {
			stream = CLASS_LOADER.getResourceAsStream(PropertiesConstant.FILE_NAME);
		}
		if (stream == null) {
			stream = ClassScanHelper.class.getResourceAsStream(PropertiesConstant.FILE_NAME);
		}
		if (stream == null) {
			stream = ClassScanHelper.class.getClassLoader().getResourceAsStream(PropertiesConstant.FILE_NAME);
		}
		if (stream == null) {
			LOGGER.info("没有找到[{}]配置文件。自动扫描annotation功能未开启", PropertiesConstant.FILE_NAME);
			return null;
		}
		Properties properties = new Properties();
		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 获取classpath绝对路径
	 * @param classloader
	 * @return
	 */
	private static String baseName(ClassLoader classloader) {
		URI uri = null;
		try {
			uri = classloader.getResource("").toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new File(uri).getAbsolutePath();
	}

	/**
	 * 扫描filter
	 * @param dir
	 * @param baseName
	 * @param classloader
	 */
	@SuppressWarnings("unchecked")
	private static <T, R> void scanFilter(File dir) {
		String className = StringUtils.resolverpackage(dir.getAbsolutePath());
		/*String className = dir.getAbsolutePath().substring(BASE_PATH.length() + 1);
		int dot = className.indexOf('.');
		if (dot > -1) {
			className = className.substring(0, dot);
		}
		if (className.indexOf('\\') > -1) {
			className = className.replace('\\', '.');
		}
		if (className.indexOf('/') > -1) {
			className = className.replace('/', '.');
		}*/
		Class<Filte<T, R>> clazz = null;
		try {
			clazz = (Class<Filte<T, R>>) CLASS_LOADER.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ChainFactory context = FilterChainContext.getInstance();
		context.addFilter(clazz);
	}

	protected static String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}
}
