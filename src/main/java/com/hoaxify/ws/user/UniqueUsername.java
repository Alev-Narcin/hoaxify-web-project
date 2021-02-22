package com.hoaxify.ws.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target( {ElementType.FIELD} )  //Annotation ı nerede kullanıcağımızı belirtiyoruz.
@Retention(RetentionPolicy.RUNTIME) //Annotation ın runtime da çözümlenmesiyle ilgili bir davranış.
@Constraint(   //bu const. kullanıldığı yerlerde uygulanacak olan validation logic inin ilgili clasını soruyor.
        validatedBy = {UniqueUsernameValidator.class}
)
public @interface UniqueUsername {
    String message() default "{hoaxify.constraint.username.UniqueUsername.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
