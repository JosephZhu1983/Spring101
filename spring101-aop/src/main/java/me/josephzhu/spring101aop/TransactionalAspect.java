package me.josephzhu.spring101aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@Slf4j
class TransactionalAspect extends TransactionSynchronizationAdapter {

    @Autowired
    private DbMapper dbMapper;

    private ThreadLocal<JoinPoint> joinPoint = new ThreadLocal<>();

    @Before("@within(org.springframework.transaction.annotation.Transactional) || @annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerSynchronization(JoinPoint jp) {
        joinPoint.set(jp);
        TransactionSynchronizationManager.registerSynchronization(this);
    }

    @Override
    public void afterCompletion(int status) {
        log.info(String.format("【%s】【%s】事务提交 %s，目前记录数：%s",
                joinPoint.get().getSignature().getDeclaringType().toString(),
                joinPoint.get().getSignature().toLongString(),
                status == 0 ? "成功":"失败",
                dbMapper.personCount()));
        joinPoint.remove();
    }
}