package de.jo.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class PackageScanner {

    public static Class<?>[] classes(String pckg) {
        Reflections reflections = new Reflections(pckg);
        Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
        return (Class<?>[]) classes.toArray();
    }

    public static Object[] classes(String pckg, Class<? extends Annotation> ann) {
        Reflections reflections = new Reflections(pckg);
        Set<Class<? extends Object>> classes = reflections.getTypesAnnotatedWith(ann);
        return  classes.toArray();
    }

}
