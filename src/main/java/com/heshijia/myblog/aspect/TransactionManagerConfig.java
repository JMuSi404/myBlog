package com.heshijia.myblog.aspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;

/**
 * @Description: 配置全局事务
 * @author: DongFangQi
 */
@Aspect
@Configuration
public class TransactionManagerConfig {
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.heshijia.myblog.service.impl.*.*(..))";
    private static final String[] REQUIRED_RULE_TRANSACTION = {"insert*", "edit*","create*", "add*", "save*","modify*", "update*","remove*", "del*", "delete*"};
    private static final String[] READ_RULE_TRANSACTION = {"select*", "get*", "query*", "search*", "count*","detail*", "find*"};

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        RuleBasedTransactionAttribute REQUIRED = new RuleBasedTransactionAttribute();
        REQUIRED.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        REQUIRED.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute READONLY = new RuleBasedTransactionAttribute();
        READONLY.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        READONLY.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        READONLY.setReadOnly(true);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        for (String s : REQUIRED_RULE_TRANSACTION) {
            source.addTransactionalMethod(s, REQUIRED);
        }

        for (String s : READ_RULE_TRANSACTION) {
            source.addTransactionalMethod(s, READONLY);
        }

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}

