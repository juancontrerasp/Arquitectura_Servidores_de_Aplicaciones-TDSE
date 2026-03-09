package org.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingControllerTest {

    @Test
    void greeting_withProvidedName_returnsHolaPlusName() {
        assertEquals("Hola Alice", GreetingController.greeting("Alice"));
    }

    @Test
    void greeting_withDefaultWorld_returnsHolaWorld() {
        assertEquals("Hola World", GreetingController.greeting("World"));
    }

    @Test
    void greeting_withEmptyString_returnsHolaEmpty() {
        assertEquals("Hola ", GreetingController.greeting(""));
    }

    @Test
    void greetingControllerIsAnnotatedWithRestController() {
        assertTrue(GreetingController.class.isAnnotationPresent(RestController.class));
    }

    @Test
    void greetingMethodIsAnnotatedWithGetMapping() throws NoSuchMethodException {
        GetMapping annotation = GreetingController.class
                .getDeclaredMethod("greeting", String.class)
                .getAnnotation(GetMapping.class);
        assertNotNull(annotation);
        assertEquals("/greeting", annotation.value());
    }

    @Test
    void greetingParameter_hasRequestParamAnnotation() throws NoSuchMethodException {
        java.lang.reflect.Parameter param = GreetingController.class
                .getDeclaredMethod("greeting", String.class)
                .getParameters()[0];
        RequestParam rp = param.getAnnotation(RequestParam.class);
        assertNotNull(rp);
        assertEquals("name", rp.value());
        assertEquals("World", rp.defaultValue());
    }
}
