package com.example.bilibili.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TasksExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TasksExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDateStartIsNull() {
            addCriterion("date_start is null");
            return (Criteria) this;
        }

        public Criteria andDateStartIsNotNull() {
            addCriterion("date_start is not null");
            return (Criteria) this;
        }

        public Criteria andDateStartEqualTo(Date value) {
            addCriterionForJDBCDate("date_start =", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartNotEqualTo(Date value) {
            addCriterionForJDBCDate("date_start <>", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartGreaterThan(Date value) {
            addCriterionForJDBCDate("date_start >", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_start >=", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartLessThan(Date value) {
            addCriterionForJDBCDate("date_start <", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_start <=", value, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartIn(List<Date> values) {
            addCriterionForJDBCDate("date_start in", values, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartNotIn(List<Date> values) {
            addCriterionForJDBCDate("date_start not in", values, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_start between", value1, value2, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStartNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_start not between", value1, value2, "dateStart");
            return (Criteria) this;
        }

        public Criteria andDateStopIsNull() {
            addCriterion("date_stop is null");
            return (Criteria) this;
        }

        public Criteria andDateStopIsNotNull() {
            addCriterion("date_stop is not null");
            return (Criteria) this;
        }

        public Criteria andDateStopEqualTo(Date value) {
            addCriterionForJDBCDate("date_stop =", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopNotEqualTo(Date value) {
            addCriterionForJDBCDate("date_stop <>", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopGreaterThan(Date value) {
            addCriterionForJDBCDate("date_stop >", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_stop >=", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopLessThan(Date value) {
            addCriterionForJDBCDate("date_stop <", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date_stop <=", value, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopIn(List<Date> values) {
            addCriterionForJDBCDate("date_stop in", values, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopNotIn(List<Date> values) {
            addCriterionForJDBCDate("date_stop not in", values, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_stop between", value1, value2, "dateStop");
            return (Criteria) this;
        }

        public Criteria andDateStopNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date_stop not between", value1, value2, "dateStop");
            return (Criteria) this;
        }

        public Criteria andTaskDescIsNull() {
            addCriterion("task_desc is null");
            return (Criteria) this;
        }

        public Criteria andTaskDescIsNotNull() {
            addCriterion("task_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTaskDescEqualTo(String value) {
            addCriterion("task_desc =", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotEqualTo(String value) {
            addCriterion("task_desc <>", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescGreaterThan(String value) {
            addCriterion("task_desc >", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescGreaterThanOrEqualTo(String value) {
            addCriterion("task_desc >=", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLessThan(String value) {
            addCriterion("task_desc <", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLessThanOrEqualTo(String value) {
            addCriterion("task_desc <=", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescLike(String value) {
            addCriterion("task_desc like", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotLike(String value) {
            addCriterion("task_desc not like", value, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescIn(List<String> values) {
            addCriterion("task_desc in", values, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotIn(List<String> values) {
            addCriterion("task_desc not in", values, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescBetween(String value1, String value2) {
            addCriterion("task_desc between", value1, value2, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andTaskDescNotBetween(String value1, String value2) {
            addCriterion("task_desc not between", value1, value2, "taskDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescIsNull() {
            addCriterion("gifts_desc is null");
            return (Criteria) this;
        }

        public Criteria andGiftsDescIsNotNull() {
            addCriterion("gifts_desc is not null");
            return (Criteria) this;
        }

        public Criteria andGiftsDescEqualTo(String value) {
            addCriterion("gifts_desc =", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescNotEqualTo(String value) {
            addCriterion("gifts_desc <>", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescGreaterThan(String value) {
            addCriterion("gifts_desc >", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescGreaterThanOrEqualTo(String value) {
            addCriterion("gifts_desc >=", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescLessThan(String value) {
            addCriterion("gifts_desc <", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescLessThanOrEqualTo(String value) {
            addCriterion("gifts_desc <=", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescLike(String value) {
            addCriterion("gifts_desc like", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescNotLike(String value) {
            addCriterion("gifts_desc not like", value, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescIn(List<String> values) {
            addCriterion("gifts_desc in", values, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescNotIn(List<String> values) {
            addCriterion("gifts_desc not in", values, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescBetween(String value1, String value2) {
            addCriterion("gifts_desc between", value1, value2, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andGiftsDescNotBetween(String value1, String value2) {
            addCriterion("gifts_desc not between", value1, value2, "giftsDesc");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}