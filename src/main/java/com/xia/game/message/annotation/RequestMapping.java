package com.xia.game.message.annotation;

import java.lang.annotation.*;

/**
 * A method whose type is meta-annotated with this
 * is used to be a logic handler
 * @author kingston
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

}
