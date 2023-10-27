package de.jo.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {

    String name();
    String[] aliases() default {};
    String description();

    String syntax() default "";

}
