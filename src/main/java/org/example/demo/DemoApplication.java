package org.example.demo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;

public class DemoApplication {

	public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException {
		Class<?> c = Class.forName(args[0]);

		if (c.isAnnotationPresent(RestController.class)) {
			for (Method m : c.getDeclaredMethods()) {
				if (m.isAnnotationPresent(GetMapping.class)) {
					GetMapping a = m.getAnnotation(GetMapping.class);
					String path = a.value();
					System.out.println("Registering route: " + path + " -> " + m.getName());
					HttpServer.get(path, (req, res) -> {
						try {
							return (String) m.invoke(null);
						} catch (Exception e) {
							return "Error: " + e.getMessage();
						}
					});
				}
			}
		}

		HttpServer.start();
	}
}
