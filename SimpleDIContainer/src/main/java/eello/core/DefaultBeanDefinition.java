package main.java.eello.core;

import java.lang.reflect.InvocationTargetException;

public class DefaultBeanDefinition implements BeanDefinition {

	private String beanName;
	private Class<?> beanType;
	private Class<?>[] interfaces;
	private Class<?>[] dependsOn;
	private BeanScope scope;

	private DefaultBeanDefinition() {}

	public static BeanDefinition of(Class<?> clazz) {
		return of(clazz, null);
	}

	public static BeanDefinition of(Class<?> clazz, String beanName) {
		DefaultBeanDefinition def = new DefaultBeanDefinition();
		def.beanName = beanName != null ? beanName : DefaultBeanNameResolver.resolve(clazz.getSimpleName());
		def.beanType = clazz;
		def.interfaces = clazz.getInterfaces();
		def.dependsOn = clazz.getConstructors()[0].getParameterTypes();
		def.scope = BeanScope.SINGLETON;
		return def;
	}

	@Override
	public String getBeanName() {
		return beanName;
	}

	@Override
	public Class<?> getBeanType() {
		return beanType;
	}

	@Override
	public String getBeanTypeName() {
		return beanType.getName();
	}

	@Override
	public Class<?>[] getInterfaceTypes() {
		return interfaces;
	}

	@Override
	public Class<?>[] getDependsOn() {
		return dependsOn;
	}

	@Override
	public BeanScope getScope() {
		return scope;
	}

	@Override
	public Object newInstance(Object[] params) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		return beanType.getConstructors()[0].newInstance(params);
	}
}
