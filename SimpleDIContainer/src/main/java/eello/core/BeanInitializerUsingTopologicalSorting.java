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
				// indegree.remove(entry.getKey());
			}
		}

		int created = 0;
		while (!queue.isEmpty()) {
			Class<?> target = queue.poll();
			List<BeanDefinition> defs = beanDefinitionMap.get(target);

			if (defs.size() > 1) {
				// target이 인터페이스이고 target 인터페이스를 구현하는 빈이 여러개 등록될 경우
				// target 인터페이스에 어떤 구현체를 알 수 없음. 전체적으로 보면 결국 어떤 빈이 주입되어야 할 지 모호해짐.
				throw new IllegalStateException("모호함");
			}

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
			for (Class<?> interfaceType : bd.getInterfaceTypes()) {
				putWithInitializeIfNotContainsKey(beanDefinitionMap, interfaceType, bd);
			}

			putWithInitializeIfNotContainsKey(dependsOnMe, bd.getBeanType(), null);
			for (Class<?> dependency : bd.getDependsOn()) {
				putWithInitializeIfNotContainsKey(dependsOnMe, dependency, bd.getBeanType());
			}
		}

		for (Map.Entry<Class<?>, List<Class<?>>> entry : dependsOnMe.entrySet()) {
			if (!indegree.containsKey(entry.getKey())) {
				indegree.put(entry.getKey(), 0);
			}

			for (Class<?> val : entry.getValue()) {
				indegree.put(val, indegree.getOrDefault(val, 0) + 1);
			}
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
