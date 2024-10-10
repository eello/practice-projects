package main.java.eello.core;

import java.lang.reflect.InvocationTargetException;

public interface BeanDefinition {

	String getBeanName();
	Class<?> getBeanType();
	String getBeanTypeName();
	Class<?>[] getInterfaceTypes();
	Class<?>[] getDependsOn();
	BeanScope getScope();
	Object newInstance(Object[] params) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException;
}
