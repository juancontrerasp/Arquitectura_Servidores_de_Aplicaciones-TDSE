package org.example.demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DemoApplication {

	private static Map<String, Method> controllerMethods = new HashMap<>();

	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
		Class<?> c = Class.forName(args[0]);

		if (c.isAnnotationPresent(RestController.class)) {
			for (Method m : c.getDeclaredMethods()){
				if (m.isAnnotationPresent(GetMapping.class)){
					GetMapping a = m.getAnnotation(GetMapping.class);
					System.out.println("adding controllerMethod for Path " + a.value());
					controllerMethods.put(a.value(), m);
				}
			}
		}

		String executionPath = args[1];
		Method m = controllerMethods.get(executionPath);

		System.out.println("Invoking: " +  m.getName());
		System.out.println(m.invoke(null));

	}
}
