package com.github.romanqed.cglab4;

import org.atteo.classindex.ClassIndex;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class ReflectUtil {
    public static final Map<String, EllipseDrawer> DRAWERS = Collections.unmodifiableMap(findEllipseDrawers());

    static Map<String, EllipseDrawer> findEllipseDrawers() {
        Map<String, EllipseDrawer> ret = new HashMap<>();
        Iterable<Class<?>> found = ClassIndex.getAnnotated(Drawer.class);
        for (Class<?> clazz : found) {
            String key = clazz.getAnnotation(Drawer.class).value();
            EllipseDrawer drawer;
            try {
                drawer = (EllipseDrawer) clazz
                        .getDeclaredConstructor()
                        .newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid drawer declaration: " + clazz.getName(), e);
            }
            ret.put(key, drawer);
        }
        return ret;
    }
}
