package main.java.eello.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBeanFactory implements BeanFactory {

	private Map<String, Object> beanByName;
	private Map<Class<?>, List<Object>> beanByType;

	public DefaultBeanFactory() {
		this.beanByName = new HashMap<>();
		this.beanByType = new HashMap<>();
	}

	@Override
	public void registerBean(BeanDefinition def) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		if (isRegistered(def)) {
			throw new IllegalArgumentException("[" + def.getBeanName() + "]은 이미 등록된 빈.");
		}

		Object bean = createBean(def);

		beanByName.put(def.getBeanName(), bean);
		if (!beanByType.containsKey(def.getBeanType())) {
			beanByType.put(def.getBeanType(), new ArrayList<>());
		}
		beanByType.get(def.getBeanType()).add(bean);

		for (Class<?> interf : def.getInterfaceTypes()) {
			if (!beanByType.containsKey(interf)) {
				beanByType.put(interf, new ArrayList<>());
			}
			beanByType.get(interf).add(bean);
		}

		System.out.println("빈 [" + def.getBeanTypeName() + "]이 [" + def.getBeanName() + "] 이름으로 등록됨.");
	}

	private Object createBean(BeanDefinition def) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException {
		Class<?>[] dependsOn = def.getDependsOn();
		Object[] constructorParams = new Object[dependsOn.length];

		for (int i = 0; i < def.getDependsOn().length; i++) {
			List<Object> beans = beanByType.get(dependsOn[i]);
			if (beans == null || beans.isEmpty()) {
				throw new IllegalStateException(
					"[" + def.getBeanName() + "]을 생성하기 위해 주입해야할 빈 [" + dependsOn[i].getName() + "]이 생성되지 않음.");
			}

//			if (beans.size() > 1) {
//				throw new IllegalStateException(
//					"[" + def.getBeanName() + "]에 주입해야할 빈 [" + dependsOn[i].getName() + "]이 모호함.");
//			}

			constructorParams[i] = beans.getFirst();
			if (beans.size() > 1) {
				List<Object> candidate = new ArrayList<>();
				for (Object candi : beans) {
					if (candi.getClass().isAnnotationPresent(Primary.class)) {
						candidate.add(candi);
					}
				}

				if (candidate.size() != 1) {
					throw new NoUniqueBeanDefinitionException(
							"[" + def.getBeanName() + "]에 주입해야할 빈 [" + dependsOn[i].getName() + "]이 모호함."
					);
				}

				constructorParams[i] = candidate.getFirst();
			}
		}

		return def.newInstance(constructorParams);
	}

	@Override
	public Object getBean(String beanName) {
		return beanByName.get(beanName);
	}

	@Override
	public <T> T getBean(String beanName, Class<T> requiredType) {
		return (T) beanByName.get(beanName);
	}

	@Override
	public <T> T[] getBean(Class<T> beanType) {
		return beanByType.get(beanType).toArray((T[]) new Object[0]);
	}

	@Override
	public boolean isRegistered(String beanName) {
		return beanByName.containsKey(beanName);
	}

	@Override
	public boolean isRegistered(BeanDefinition def) {
		return beanByName.containsKey(def.getBeanName());
	}
}
