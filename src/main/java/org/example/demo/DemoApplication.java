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
							java.lang.reflect.Parameter[] params = m.getParameters();
							if (params.length == 0) {
								return (String) m.invoke(null);
							}
							Object[] args2 = new Object[params.length];
							for (int i = 0; i < params.length; i++) {
								RequestParam rp = params[i].getAnnotation(RequestParam.class);
								if (rp != null) {
									String val = req.getValues(rp.value());
									args2[i] = (val != null) ? val : rp.defaultValue();
								} else {
									args2[i] = null;
								}
							}
							return (String) m.invoke(null, args2);
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
