package com.xia.game.message.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MessageMeta {
    byte module() default 0;
    byte cmd() default 0;
}
