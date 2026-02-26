package com.example.bilibili.pojo;

import java.util.ArrayList;
import java.util.List;

public class AdminTreeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdminTreeExample() {
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

        public Criteria andStreeidIsNull() {
            addCriterion("streeID is null");
            return (Criteria) this;
        }

        public Criteria andStreeidIsNotNull() {
            addCriterion("streeID is not null");
            return (Criteria) this;
        }

        public Criteria andStreeidEqualTo(Long value) {
            addCriterion("streeID =", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidNotEqualTo(Long value) {
            addCriterion("streeID <>", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidGreaterThan(Long value) {
            addCriterion("streeID >", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidGreaterThanOrEqualTo(Long value) {
            addCriterion("streeID >=", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidLessThan(Long value) {
            addCriterion("streeID <", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidLessThanOrEqualTo(Long value) {
            addCriterion("streeID <=", value, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidIn(List<Long> values) {
            addCriterion("streeID in", values, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidNotIn(List<Long> values) {
            addCriterion("streeID not in", values, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidBetween(Long value1, Long value2) {
            addCriterion("streeID between", value1, value2, "streeid");
            return (Criteria) this;
        }

        public Criteria andStreeidNotBetween(Long value1, Long value2) {
            addCriterion("streeID not between", value1, value2, "streeid");
            return (Criteria) this;
        }

        public Criteria andTreenameIsNull() {
            addCriterion("treeName is null");
            return (Criteria) this;
        }

        public Criteria andTreenameIsNotNull() {
            addCriterion("treeName is not null");
            return (Criteria) this;
        }

        public Criteria andTreenameEqualTo(String value) {
            addCriterion("treeName =", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameNotEqualTo(String value) {
            addCriterion("treeName <>", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameGreaterThan(String value) {
            addCriterion("treeName >", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameGreaterThanOrEqualTo(String value) {
            addCriterion("treeName >=", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameLessThan(String value) {
            addCriterion("treeName <", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameLessThanOrEqualTo(String value) {
            addCriterion("treeName <=", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameLike(String value) {
            addCriterion("treeName like", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameNotLike(String value) {
            addCriterion("treeName not like", value, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameIn(List<String> values) {
            addCriterion("treeName in", values, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameNotIn(List<String> values) {
            addCriterion("treeName not in", values, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameBetween(String value1, String value2) {
            addCriterion("treeName between", value1, value2, "treename");
            return (Criteria) this;
        }

        public Criteria andTreenameNotBetween(String value1, String value2) {
            addCriterion("treeName not between", value1, value2, "treename");
            return (Criteria) this;
        }

        public Criteria andTreepathIsNull() {
            addCriterion("treePath is null");
            return (Criteria) this;
        }

        public Criteria andTreepathIsNotNull() {
            addCriterion("treePath is not null");
            return (Criteria) this;
        }

        public Criteria andTreepathEqualTo(String value) {
            addCriterion("treePath =", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathNotEqualTo(String value) {
            addCriterion("treePath <>", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathGreaterThan(String value) {
            addCriterion("treePath >", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathGreaterThanOrEqualTo(String value) {
            addCriterion("treePath >=", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathLessThan(String value) {
            addCriterion("treePath <", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathLessThanOrEqualTo(String value) {
            addCriterion("treePath <=", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathLike(String value) {
            addCriterion("treePath like", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathNotLike(String value) {
            addCriterion("treePath not like", value, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathIn(List<String> values) {
            addCriterion("treePath in", values, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathNotIn(List<String> values) {
            addCriterion("treePath not in", values, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathBetween(String value1, String value2) {
            addCriterion("treePath between", value1, value2, "treepath");
            return (Criteria) this;
        }

        public Criteria andTreepathNotBetween(String value1, String value2) {
            addCriterion("treePath not between", value1, value2, "treepath");
            return (Criteria) this;
        }

        public Criteria andFtreeidIsNull() {
            addCriterion("ftreeID is null");
            return (Criteria) this;
        }

        public Criteria andFtreeidIsNotNull() {
            addCriterion("ftreeID is not null");
            return (Criteria) this;
        }

        public Criteria andFtreeidEqualTo(Long value) {
            addCriterion("ftreeID =", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidNotEqualTo(Long value) {
            addCriterion("ftreeID <>", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidGreaterThan(Long value) {
            addCriterion("ftreeID >", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidGreaterThanOrEqualTo(Long value) {
            addCriterion("ftreeID >=", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidLessThan(Long value) {
            addCriterion("ftreeID <", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidLessThanOrEqualTo(Long value) {
            addCriterion("ftreeID <=", value, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidIn(List<Long> values) {
            addCriterion("ftreeID in", values, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidNotIn(List<Long> values) {
            addCriterion("ftreeID not in", values, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidBetween(Long value1, Long value2) {
            addCriterion("ftreeID between", value1, value2, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andFtreeidNotBetween(Long value1, Long value2) {
            addCriterion("ftreeID not between", value1, value2, "ftreeid");
            return (Criteria) this;
        }

        public Criteria andLvIsNull() {
            addCriterion("lv is null");
            return (Criteria) this;
        }

        public Criteria andLvIsNotNull() {
            addCriterion("lv is not null");
            return (Criteria) this;
        }

        public Criteria andLvEqualTo(String value) {
            addCriterion("lv =", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvNotEqualTo(String value) {
            addCriterion("lv <>", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvGreaterThan(String value) {
            addCriterion("lv >", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvGreaterThanOrEqualTo(String value) {
            addCriterion("lv >=", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvLessThan(String value) {
            addCriterion("lv <", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvLessThanOrEqualTo(String value) {
            addCriterion("lv <=", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvLike(String value) {
            addCriterion("lv like", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvNotLike(String value) {
            addCriterion("lv not like", value, "lv");
            return (Criteria) this;
        }

        public Criteria andLvIn(List<String> values) {
            addCriterion("lv in", values, "lv");
            return (Criteria) this;
        }

        public Criteria andLvNotIn(List<String> values) {
            addCriterion("lv not in", values, "lv");
            return (Criteria) this;
        }

        public Criteria andLvBetween(String value1, String value2) {
            addCriterion("lv between", value1, value2, "lv");
            return (Criteria) this;
        }

        public Criteria andLvNotBetween(String value1, String value2) {
            addCriterion("lv not between", value1, value2, "lv");
            return (Criteria) this;
        }

        public Criteria andIconIsNull() {
            addCriterion("icon is null");
            return (Criteria) this;
        }

        public Criteria andIconIsNotNull() {
            addCriterion("icon is not null");
            return (Criteria) this;
        }

        public Criteria andIconEqualTo(String value) {
            addCriterion("icon =", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotEqualTo(String value) {
            addCriterion("icon <>", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThan(String value) {
            addCriterion("icon >", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThanOrEqualTo(String value) {
            addCriterion("icon >=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThan(String value) {
            addCriterion("icon <", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThanOrEqualTo(String value) {
            addCriterion("icon <=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLike(String value) {
            addCriterion("icon like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotLike(String value) {
            addCriterion("icon not like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconIn(List<String> values) {
            addCriterion("icon in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotIn(List<String> values) {
            addCriterion("icon not in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconBetween(String value1, String value2) {
            addCriterion("icon between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotBetween(String value1, String value2) {
            addCriterion("icon not between", value1, value2, "icon");
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