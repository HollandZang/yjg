package com.holland.holland.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("AlibabaClassMustHaveAuthor")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    AuthRole[] value();

    @SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
    enum AuthRole {
        ADMIN(1), EMPLOYEE(3), CUSTOMER(2);

        AuthRole(int code) {
            this.code = code;
        }

        public final int code;
    }
}
