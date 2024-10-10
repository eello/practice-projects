package main.java.eello.core;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {

	private ClassLoader cl;

	public ClassPathBeanDefinitionScanner() {
		this.cl = Thread.currentThread().getContextClassLoader();
	}

	public Set<BeanDefinition> doScan(String basePackage) throws ClassNotFoundException {
		Set<Class<?>> classes = findAllClasses(basePackage);
		Set<BeanDefinition> beanDefinitions = new HashSet<>();

		for (Class<?> clazz : classes) {
			if (clazz.isAnnotationPresent(Component.class)) {
				beanDefinitions.add(DefaultBeanDefinition.of(clazz));
			}
		}

		return beanDefinitions;
	}

	private Set<Class<?>> findAllClasses(String basePackage) throws ClassNotFoundException {
		String path = basePackage.replace(".", "/");
		URL resource = cl.getResource(path);
		File directory = new File(resource.getFile());

		Set<Class<?>> classes = new HashSet<>();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				String childPackageName = basePackage + "." + file.getName();
				classes.addAll(findAllClasses(childPackageName));
			} else if (file.getName().endsWith(".class")) {
				String className = basePackage + '.' + file.getName().substring(0, file.getName().length() - 6);
				classes.add(Class.forName(className));
			}
		}

		return classes;
	}
}
