package com.momentus.corefw.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE })
@Retention( RetentionPolicy.RUNTIME )
public @interface EntityProperties {

    boolean isPK() default false;
    boolean isBK() default false;

    boolean isMandatory() default false;
   /* boolean isFutureDate() default false;
    boolean isPastDate() default false;*/

    boolean isUnique() default false;

}
