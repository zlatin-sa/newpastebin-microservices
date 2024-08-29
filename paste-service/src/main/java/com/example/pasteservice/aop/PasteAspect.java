package com.example.pasteservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class PasteAspect {

    @Around("execution(public * com.example.pasteservice.service.PasteService.*(..))")
    public Object logAroundAllMethods(ProceedingJoinPoint joinPoint) {
        log.info("Method %s starts with args: %s"
                .formatted(joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs())));
        Object returnedValue;
        try {
            returnedValue = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Method %s stops"
                    .formatted(joinPoint.getSignature().getName()));
        }
        return returnedValue;
    }

}
