package io.xiaowei.core.utils.query;

import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Author xiaowei_wang
 * @Description 条件构造器用于创建条件表达式
 * @Date 7:23 下午 2020/5/7
 * @Param
 * @return
 **/
public class Restrictions {

    public static SimpleSelector count(String fieldName) {
        return new SimpleSelector(fieldName, MySelector.Operator.COUNT);
    }

    public static SimpleSelector countDistinct(String fieldName) {
        return new SimpleSelector(fieldName, MySelector.Operator.COUNT_DISTINCT);
    }

    public static SimpleSelector sum(String fieldName) {
        return new SimpleSelector(fieldName, MySelector.Operator.SUM);
    }

    /**
     * 等于
     */
    public static SimpleExpression eq(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.EQ);
    }

    /**
     * 集合包含某个元素
     */
    public static SimpleExpression hasMember(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.IS_MEMBER);
    }

    /**
     * 不等于
     */
    public static SimpleExpression ne(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.NE);
    }

    /**
     * 模糊匹配
     */
    public static SimpleExpression like(String fieldName, String value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LIKE);
    }

    public static SimpleExpression isnull(String fieldName) {
        return new SimpleExpression(fieldName, null, Criterion.Operator.IS_NULL);
    }
    /**
     */
//    public static SimpleExpression like(String fieldName, String value,
//                                        MatchMode matchMode, boolean ignoreNull) {
//        if (StringUtils.isEmpty(value)) return null;
//        return null;
//    }

    /**
     * 大于
     */
    public static SimpleExpression gt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.GT);
    }

    /**
     * 小于
     */
    public static SimpleExpression lt(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LT);
    }

    /**
     * 小于等于
     */
    public static SimpleExpression lte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.LTE);
    }

    /**
     * 大于等于
     */
    public static SimpleExpression gte(String fieldName, Object value, boolean ignoreNull) {
        if (ignoreNull && StringUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, Criterion.Operator.GTE);
    }

    /**
     * 并且
     */
    public static LogicalExpression and(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.AND);
    }

    /**
     * 或者
     */
    public static LogicalExpression or(Criterion... criterions) {
        return new LogicalExpression(criterions, Criterion.Operator.OR);
    }

    /**
     * 包含于
     */
    @SuppressWarnings("rawtypes")
    public static LogicalExpression in(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.EQ);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }

    /**
     * 不包含于
     */
    public static LogicalExpression notin(String fieldName, Collection value, boolean ignoreNull) {
        if (ignoreNull && (value == null || value.isEmpty())) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, Criterion.Operator.NE);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.AND);
    }


    /**
     * 集合包含某几个元素，譬如可以查询User类中Set<String> set包含"ABC","bcd"的User集合，
     * 或者查询User中Set<Address>的Address的name为"北京"的所有User集合
     * 集合可以为基本类型或者JavaBean，可以是one to many或者是@ElementCollection
     *
     * @param fieldName 列名
     * @param value     集合
     * @return expresssion
     */
    public static LogicalExpression hasMembers(String fieldName, Object... value) {
        SimpleExpression[] ses = new SimpleExpression[value.length];
        int i = 0;
        //集合中对象是基本类型，如Set<Long>，List<String>
        Criterion.Operator operator = Criterion.Operator.IS_MEMBER;
        //集合中对象是JavaBean
        if (fieldName.contains(".")) {
            operator = Criterion.Operator.EQ;
        }
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, operator);
            i++;
        }
        return new LogicalExpression(ses, Criterion.Operator.OR);
    }

}
