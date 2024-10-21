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
		checkValidation();

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
			for (Class<?> interfaceType : bd.getInterfaceTypes()) {
				putWithInitializeIfNotContainsKey(beanDefinitionMap, interfaceType, bd);
			}

			putWithInitializeIfNotContainsKey(dependsOnMe, bd.getBeanType(), null);
			for (Class<?> dependency : bd.getDependsOn()) {
				putWithInitializeIfNotContainsKey(dependsOnMe, dependency, bd.getBeanType());
			}

			indegree.put(bd.getBeanType(), bd.getDependsOn().length);
		}
	}

	private void checkValidation() {
		for (Map.Entry<Class<?>, List<BeanDefinition>> entry : beanDefinitionMap.entrySet()) {
			if (dependsOnMe.containsKey(entry.getKey())
					&& !dependsOnMe.get(entry.getKey()).isEmpty() // entry.getKey()에 의존하는 클래스가 존재할 때
					&& entry.getValue().size() > 1 // 후보 빈이 2개 이상일 경우
			) {
				int count = 0; // @Primary 가 적용된 클래스 수
				for (BeanDefinition bd : entry.getValue()) {
					if (bd.getBeanType().isAnnotationPresent(Primary.class)) {
						count++;

						if (count > 1) {
							break;
						}
					}
				}

				if (count != 1) {
					String message = "[" + dependsOnMe.get(entry.getKey()).getFirst().getName() +
							"]의 파라미터 [" + entry.getKey().getName() + "] 타입";
					message += count == 0 ?
							"의 후보 빈이 2개 이상입니다."
							: "에 @Primary가 적용된 클래스가 2개 이상입니다.";

					throw new NoUniqueBeanDefinitionException(message);
				}
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

		boolean isPrimary = bd.getBeanType().isAnnotationPresent(Primary.class);
		for (Class<?> interfaceType : bd.getInterfaceTypes()) {
			if (isPrimary
					|| (beanDefinitionMap.containsKey(interfaceType) && beanDefinitionMap.get(interfaceType).size() == 1)
			) {
				if (dependsOnMe.containsKey(interfaceType)) {
					next.addAll(dependsOnMe.get(interfaceType));
				}
			}
		}
		return next.toArray(new Class<?>[0]);
	}
}
