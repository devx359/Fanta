package utilities;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface RetryCountIfFailed {
	int value() default 0;
}
