package com.dimit.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dimit.constant.PropertiesConstant;
import com.dimit.context.FilterChainContext;
import com.dimit.intereface.Filte;

/**
 * 类扫描工具类
 * @author dimit
 */
public class ClassScanHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassScanHelper.class);
	private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
	private static final String BASE_PATH = baseName(CLASS_LOADER);
	private static final Properties CONFIG = loadProperties();

	/**
	 * 初始化
	 */
	public static void init() {
		try {
			String key = CONFIG.getProperty(PropertiesConstant.PACKAGE_KEY);
			if(StringUtils.isBlank(key)) {
				LOGGER.info("没有找到配置项[{}]。自动扫描annotation功能未开启",PropertiesConstant.PACKAGE_KEY);
				return;
			}
			String [] packages = CONFIG.getProperty(PropertiesConstant.PACKAGE_KEY).split(",");
			for (String packageStr : packages) {
				Enumeration<URL> em = CLASS_LOADER.getResources(getPath(packageStr));
				while (em.hasMoreElements()) {
					URL url = em.nextElement();
					// 不检查jar包中的注解
					URLConnection connection = url.openConnection();
					if ("jar".equals(url.getProtocol()) && connection instanceof JarURLConnection) {
						continue;
					}
					// 源代码中的
					File file = new File(url.toURI());
					File[] files = file.listFiles();
					for (File f : files) {
						scanPackage(f, BASE_PATH, CLASS_LOADER);
					}
				}
			}
			FilterChainContext.getInstance().init();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
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
			LOGGER.info("没有找到[{}]配置文件。自动扫描annotation功能未开启",PropertiesConstant.FILE_NAME);
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
	 * 扫描包
	 * @param dir
	 * @param baseName
	 * @param classloader
	 */
	private static void scanPackage(File dir, String baseName, ClassLoader classloader) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				scanPackage(file, baseName, classloader);
			}
			return;
		}
		scanFilter(dir, baseName, classloader);
	}

	/**
	 * 扫描filter
	 * @param dir
	 * @param baseName
	 * @param classloader
	 */
	@SuppressWarnings("unchecked")
	private static <T,R, C>void scanFilter(File dir, String baseName, ClassLoader classloader) {
		String className = dir.getAbsolutePath().substring(baseName.length() + 1);
		int dot = className.indexOf('.');
		if (dot > -1) {
			className = className.substring(0, dot);
		}
		if (className.indexOf('\\') > -1) {
			className = className.replace('\\', '.');
		}
		if (className.indexOf('/') > -1) {
			className = className.replace('/', '.');
		}
		Class<Filte<T,R>> clazz = null;
		try {
			clazz = (Class<Filte<T, R>>) classloader.loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		FilterChainContext context = FilterChainContext.getInstance();
		context.addFilter(clazz);
	}
	
	/**
	 * 将包路径替换为路径形式
	 * @param packageStr
	 * @return
	 */
	private static String getPath(String packageStr) {
		return packageStr.replace(".", File.separator);
	}
}
