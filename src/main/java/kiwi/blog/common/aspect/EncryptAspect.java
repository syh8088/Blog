package kiwi.blog.common.aspect;

import kiwi.blog.common.annotation.Encrypt;
import kiwi.blog.common.enums.EncryptType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EncryptAspect {

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Pointcut("execution(public * kiwi.blog.*.repository..*.*(..))")
    public void mapper() {
    }

    @Around("mapper()")
    public Object checkArgumentAndReturnObjectForEncryptAnnotationField(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            int tempI = i;
            List<Field> encryptFields = Arrays.stream(args[i].getClass().getDeclaredFields())
                    .filter(field -> field.getAnnotation(Encrypt.class) != null)
                    .collect(Collectors.toList());
            encryptFields.forEach(field -> {
                try {
                    encryptByType(args[tempI], field);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        return joinPoint.proceed(args);
    }

    private void encryptByType(Object arg, Field field) throws IllegalAccessException {

        field.setAccessible(true);

        if (field.getAnnotation(Encrypt.class).type() == EncryptType.PASSWORD) {
            String password = (String) field.get(arg);
            //field.set(arg, passwordEncoder.encode(password));
        }
    }
}
