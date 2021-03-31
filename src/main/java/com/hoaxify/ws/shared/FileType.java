package com.hoaxify.ws.shared;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})  //Annotation ı nerede kullanıcağımızı belirtiyoruz.
@Retention(RetentionPolicy.RUNTIME) //Annotation ın runtime da çözümlenmesiyle ilgili bir davranış.
@Constraint(validatedBy = {FileTypeValidator.class})   //bu const. kullanıldığı yerlerde uygulanacak olan validation logic inin ilgili clasını soruyor.

public @interface FileType {
    String message() default "{hoaxify.constraint.FileType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] types();
}
