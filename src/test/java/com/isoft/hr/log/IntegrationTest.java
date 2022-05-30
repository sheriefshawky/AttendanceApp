package com.isoft.hr.log;

import com.isoft.hr.log.AttendanceApp;
import com.isoft.hr.log.RedisTestContainerExtension;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = AttendanceApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public @interface IntegrationTest {
}
