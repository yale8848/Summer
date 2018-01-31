package ren.yale.java.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/***
 * 包扫描工具
 * 
 * @author 陈波
 *
 */
public abstract class PackageScanner {
	private static final String FILE_PROTOCL = "file";
	private static final String JAR_PROTOCL = "jar";
	private static final String CLASS_SUFFIX = ".class";

	public List<Class<?>> scan(String packagename) throws IOException, ClassNotFoundException {
		if (!StringUtils.isEmpty(packagename)) {
			// 获取资源
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
					.getResources(packagename.replaceAll("\\.", "/"));
			// 保存扫描所有的类
			List<Class<?>> classList = new ArrayList<Class<?>>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				// 获取协议是文件还是jar包
				String protocl = url.getProtocol();
				if (FILE_PROTOCL.equals(protocl)) {
					String packagePath = url.getPath().replaceAll("%20", " ");
					addClass(classList, packagePath, packagename);
				} else if (JAR_PROTOCL.equals(protocl)) {
					// 若是Jar包
					JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
					JarFile jarFile = jarURLConnection.getJarFile();
					Enumeration<JarEntry> jarEntrys = jarFile.entries();
					while (jarEntrys.hasMoreElements()) {
						// 进行对应的处理操作
						JarEntry jarEntry = jarEntrys.nextElement();
						String jarEntryName = jarEntry.getName();
						if (jarEntryName.equals(CLASS_SUFFIX)) {
							String className = jarEntryName.substring(0, jarEntryName.indexOf(".")).replaceAll("/",
									".");
							Class<?> cls = Class.forName(className, true,
									Thread.currentThread().getContextClassLoader());
							// 判断类是否需要加载
							if (checkAdd(cls)) {
								classList.add(cls);
							}
						}

					}
				}
			}

			// 继续进行处理操作
			return classList;
		}

		return null;
	}

	/**
	 * 添加类信息
	 * 
	 * @param classList
	 * @param packagePath
	 * @param packageName
	 * @throws ClassNotFoundException
	 */
	private void addClass(List<Class<?>> classList, String packagePath, String packageName)
			throws ClassNotFoundException {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) || file.isDirectory();
			}
		});
		// 遍历文件操作
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				// 获取对应的类名
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (!StringUtils.isEmpty(packageName)) {
					className = packageName + "." + className;
				}
				Class<?> cls = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
				// 判断类是否需要加载
				if (checkAdd(cls)) {
					classList.add(cls);
				}
			} else {
				// 进行递归调用,为目录
				String currentPackagePath = fileName;
				if (!StringUtils.isEmpty(packagePath)) {
					currentPackagePath = packagePath + "/" + currentPackagePath;
				}

				String currentPackageName = fileName;
				if (!StringUtils.isEmpty(packageName)) {
					currentPackageName = packageName + "." + currentPackageName;
				}
				addClass(classList, currentPackagePath, currentPackageName);
			}
		}
	}

	/***
	 * 判断哪些类需要加载
	 * 
	 * @param clzz
	 * @return
	 */
	public abstract boolean checkAdd(Class<?> clzz);

}
