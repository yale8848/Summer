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


public abstract class PackageScanner {
	private static final String FILE_PROTOCL = "file";
	private static final String JAR_PROTOCL = "jar";
	private static final String CLASS_SUFFIX = ".class";

	public List<Class<?>> scan(String packagename) throws IOException, ClassNotFoundException {
		if (!StringUtils.isEmpty(packagename)) {

			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
					.getResources(packagename.replaceAll("\\.", "/"));

			List<Class<?>> classList = new ArrayList<Class<?>>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();

				String protocl = url.getProtocol();
				if (FILE_PROTOCL.equals(protocl)) {
					String packagePath = url.getPath().replaceAll("%20", " ");
					addClass(classList, packagePath, packagename);
				} else if (JAR_PROTOCL.equals(protocl)) {

					JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
					JarFile jarFile = jarURLConnection.getJarFile();
					Enumeration<JarEntry> jarEntrys = jarFile.entries();
					while (jarEntrys.hasMoreElements()) {

						JarEntry jarEntry = jarEntrys.nextElement();
						String jarEntryName = jarEntry.getName();
						if (jarEntryName.equals(CLASS_SUFFIX)) {
							String className = jarEntryName.substring(0, jarEntryName.indexOf(".")).replaceAll("/",
									".");
							Class<?> cls = Class.forName(className, true,
									Thread.currentThread().getContextClassLoader());

							if (checkAdd(cls)) {
								classList.add(cls);
							}
						}

					}
				}
			}

			return classList;
		}

		return null;
	}


	private void addClass(List<Class<?>> classList, String packagePath, String packageName)
			throws ClassNotFoundException {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) || file.isDirectory();
			}
		});
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				//get class name
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (!StringUtils.isEmpty(packageName)) {
					className = packageName + "." + className;
				}
				Class<?> cls = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
				//judge whether class to loader
				if (checkAdd(cls)) {
					classList.add(cls);
				}
			} else {
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


	public abstract boolean checkAdd(Class<?> clzz);

}
