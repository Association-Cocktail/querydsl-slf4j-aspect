package fr.assococktail.querydsl.slf4j.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class QuerydslSqlLogger {

    private static final Logger logger = LoggerFactory.getLogger(QuerydslSqlLogger.class);

    @Before("execution(* com.querydsl.sql.AbstractSQLQuery.logQuery(..))")
    public void beforeLogQueryInAbstractSQLQuery(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length == 2) {
            String queryString = (String) args[0];
            logQueryWithSlf4jLogger(queryString);
        }
    }

    @Before("execution(* com.querydsl.sql.dml.AbstractSQLClause.logQuery(..))")
    public void beforeLogQueryInAbstractSQLClause(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args.length == 3) {
            String queryString = (String) args[1];
            logQueryWithSlf4jLogger(queryString);
        }
    }

    private static void logQueryWithSlf4jLogger(String queryString) {
        String normalizedQuery = queryString.replace('\n', ' ');
        logger.debug(normalizedQuery);
    }
}
