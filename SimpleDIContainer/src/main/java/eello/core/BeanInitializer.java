package main.java.eello.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface BeanInitializer {

	void initialize(String basePackage) throws
		InvocationTargetException,
		InstantiationException,
		IllegalAccessException, ClassNotFoundException;
}
