package com.pongsky.cloud.web.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * feign 远程调用日志打印
 *
 * @author pengsenhao
 * @create 2021-02-14
 */
@Slf4j
@Aspect
@Component
@Order(value = 3)
@RequiredArgsConstructor
public class RemoteCallAspect {

    @Around("execution(public * com.pongsky.cloud.feign..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Started remote call request");
        FeignClient feignClient = (FeignClient) joinPoint.getSignature().getDeclaringType().getAnnotation(FeignClient.class);
        RequestMapping request = method.getAnnotation(RequestMapping.class);
        if (request != null) {
            log.info("serviceName [{}] methodURL [{}] methodType [REQUEST]",
                    feignClient.value(), request.value().length > 0 ? request.value()[0] : "");
        }
        GetMapping get = method.getAnnotation(GetMapping.class);
        if (get != null) {
            log.info("serviceName [{}] methodURL [{}] methodType [GET]",
                    feignClient.value(), get.value().length > 0 ? get.value()[0] : "");
        }
        PutMapping put = method.getAnnotation(PutMapping.class);
        if (put != null) {
            log.info("serviceName [{}] methodURL [{}] methodType [PUT]",
                    feignClient.value(), put.value().length > 0 ? put.value()[0] : "");
        }
        PostMapping post = method.getAnnotation(PostMapping.class);
        if (post != null) {
            log.info("serviceName [{}] methodURL [{}] methodType [POST]",
                    feignClient.value(), post.value().length > 0 ? post.value()[0] : "");
        }
        DeleteMapping delete = method.getAnnotation(DeleteMapping.class);
        if (delete != null) {
            log.info("serviceName [{}] methodURL [{}] methodType [DELETE]",
                    feignClient.value(), delete.value().length > 0 ? delete.value()[0] : "");
        }
        log.info("cost [{}] ms", System.currentTimeMillis() - start);
        log.info("Ended remote call request");
        return result;
    }

    /*
     * //    // WARN 在 @Around or @AfterReturning 中抛异常都会导致 failed and fallback failed
     * //    @AfterReturning(value = "execution(public * com.pongsky.cloud.feign..*.*(..))", returning = "globalResult")
     * //    public void afterReturning(GlobalResult<?> globalResult) {
     * //        if (!globalResult.getCode().equals(ResultCode.Success.getCode())) {
     * //            throw new RemoteCallException(globalResult);
     * //        }
     * //    }
     */

}
