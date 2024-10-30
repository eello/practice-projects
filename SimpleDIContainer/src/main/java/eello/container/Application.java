package main.java.eello.container;

import main.java.eello.container.core.*;
import main.java.eello.container.core.registry.DefaultSingletonRegistry;

import java.lang.reflect.InvocationTargetException;

public class Application {

	private static BeanFactory beanFactory;
	private static ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner;
	private static BeanInitializer beanInitializer;

	public static BeanFactory run(Class<?> baseClass) throws
		ClassNotFoundException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		return run(baseClass.getPackageName());
	}

	public static BeanFactory run(String basePackage) throws
		ClassNotFoundException,
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		beanFactory = new DefaultBeanFactory(new DefaultSingletonRegistry());
		classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner();
		beanInitializer = new BeanInitializerUsingTopologicalSorting(beanFactory, classPathBeanDefinitionScanner);
		beanInitializer.initialize(basePackage);
		return beanFactory;
	}
}
