package com.example.auction.validators.annotations;

import com.example.auction.validators.UniqueCategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCategoryValidator.class)
public @interface UniqueCategory {
    String message() default "Kategoria już istnieje lub została zasugerowana";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
