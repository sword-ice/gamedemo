package com.xia.framework.message.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleMeta {
	
	
	byte module() default 0;

}
