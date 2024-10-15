package main.java.eello.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BeanInitializerUsingTopologicalSorting implements BeanInitializer {

	private BeanFactory beanFactory;
	private ClassPathBeanDefinitionScanner bdScanner;

	private Map<Class<?>, Integer> indegree;
	private Map<Class<?>, List<Class<?>>> dependsOnMe;
	private Map<Class<?>, List<BeanDefinition>> beanDefinitionMap;

	public BeanInitializerUsingTopologicalSorting(BeanFactory beanFactory, ClassPathBeanDefinitionScanner bdScanner) {
		this.beanFactory = beanFactory;
		this.bdScanner = bdScanner;
	}

	@Override
	public void initialize(String basePackage) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException, ClassNotFoundException {
		Set<BeanDefinition> bds = bdScanner.doScan(basePackage);
		init(bds);

		Queue<Class<?>> queue = new ArrayDeque<>();
		for (Map.Entry<Class<?>, Integer> entry : indegree.entrySet()) {
			if (entry.getValue() == 0) {
				queue.add(entry.getKey());
			}
		}

		int created = 0;
		while (!queue.isEmpty()) {
			Class<?> target = queue.poll();
			List<BeanDefinition> defs = beanDefinitionMap.get(target);

			BeanDefinition bd = defs.getFirst();
			if (beanFactory.isRegistered(bd)) {
				continue;
			}

			beanFactory.registerBean(bd);
			for (Class<?> nextClass : getNextClasses(bd)) {
				int n = indegree.get(nextClass);

				if (n == 1) {
					queue.add(nextClass);
					indegree.remove(nextClass);
				} else indegree.put(nextClass, n - 1);
			}

			created++;
		}
		
		if (created != bds.size()) {
			throw new IllegalStateException("순환참조오류 발생 가능성 있음.");
		}
	}

	private void init(Set<BeanDefinition> bds) {
		indegree = new HashMap<>();
		dependsOnMe = new HashMap<>();
		beanDefinitionMap = new HashMap<>();

		for (BeanDefinition bd : bds) {
			putWithInitializeIfNotContainsKey(beanDefinitionMap, bd.getBeanType(), bd);

			putWithInitializeIfNotContainsKey(dependsOnMe, bd.getBeanType(), null);
			for (Class<?> dependency : bd.getDependsOn()) {
				putWithInitializeIfNotContainsKey(dependsOnMe, dependency, bd.getBeanType());
			}

			indegree.put(bd.getBeanType(), bd.getDependsOn().length);
		}
	}

	private <T> void putWithInitializeIfNotContainsKey(Map<Class<?>, List<T>> target, Class<?> key, T value) {
		if (!target.containsKey(key)) {
			target.put(key, new ArrayList<>());
		}

		if (value != null) {
			target.get(key).add(value);
		}
	}

	private Class<?>[] getNextClasses(BeanDefinition bd) {
		Set<Class<?>> next = new HashSet<>(dependsOnMe.get(bd.getBeanType()));
		for (Class<?> interfaceType : bd.getInterfaceTypes()) {
			if (dependsOnMe.containsKey(interfaceType)) {
				next.addAll(dependsOnMe.get(interfaceType));
			}
		}
		return next.toArray(new Class<?>[0]);
	}
}
