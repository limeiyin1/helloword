package com.redfinger.manager.common.gather.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RfClientNetinfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RfClientNetinfoExample() {
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

        public Criteria andNetinfoIdIsNull() {
            addCriterion("netinfo_id is null");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdIsNotNull() {
            addCriterion("netinfo_id is not null");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdEqualTo(Integer value) {
            addCriterion("netinfo_id =", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdNotEqualTo(Integer value) {
            addCriterion("netinfo_id <>", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdGreaterThan(Integer value) {
            addCriterion("netinfo_id >", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("netinfo_id >=", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdLessThan(Integer value) {
            addCriterion("netinfo_id <", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("netinfo_id <=", value, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdIn(List<Integer> values) {
            addCriterion("netinfo_id in", values, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdNotIn(List<Integer> values) {
            addCriterion("netinfo_id not in", values, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdBetween(Integer value1, Integer value2) {
            addCriterion("netinfo_id between", value1, value2, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andNetinfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("netinfo_id not between", value1, value2, "netinfoId");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIsNull() {
            addCriterion("error_code is null");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIsNotNull() {
            addCriterion("error_code is not null");
            return (Criteria) this;
        }

        public Criteria andErrorCodeEqualTo(String value) {
            addCriterion("error_code =", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotEqualTo(String value) {
            addCriterion("error_code <>", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeGreaterThan(String value) {
            addCriterion("error_code >", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeGreaterThanOrEqualTo(String value) {
            addCriterion("error_code >=", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLessThan(String value) {
            addCriterion("error_code <", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLessThanOrEqualTo(String value) {
            addCriterion("error_code <=", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeLike(String value) {
            addCriterion("error_code like", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotLike(String value) {
            addCriterion("error_code not like", value, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeIn(List<String> values) {
            addCriterion("error_code in", values, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotIn(List<String> values) {
            addCriterion("error_code not in", values, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeBetween(String value1, String value2) {
            addCriterion("error_code between", value1, value2, "errorCode");
            return (Criteria) this;
        }

        public Criteria andErrorCodeNotBetween(String value1, String value2) {
            addCriterion("error_code not between", value1, value2, "errorCode");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNull() {
            addCriterion("client_type is null");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNotNull() {
            addCriterion("client_type is not null");
            return (Criteria) this;
        }

        public Criteria andClientTypeEqualTo(String value) {
            addCriterion("client_type =", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotEqualTo(String value) {
            addCriterion("client_type <>", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThan(String value) {
            addCriterion("client_type >", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThanOrEqualTo(String value) {
            addCriterion("client_type >=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThan(String value) {
            addCriterion("client_type <", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThanOrEqualTo(String value) {
            addCriterion("client_type <=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLike(String value) {
            addCriterion("client_type like", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotLike(String value) {
            addCriterion("client_type not like", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeIn(List<String> values) {
            addCriterion("client_type in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotIn(List<String> values) {
            addCriterion("client_type not in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeBetween(String value1, String value2) {
            addCriterion("client_type between", value1, value2, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotBetween(String value1, String value2) {
            addCriterion("client_type not between", value1, value2, "clientType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeIsNull() {
            addCriterion("gather_type is null");
            return (Criteria) this;
        }

        public Criteria andGatherTypeIsNotNull() {
            addCriterion("gather_type is not null");
            return (Criteria) this;
        }

        public Criteria andGatherTypeEqualTo(String value) {
            addCriterion("gather_type =", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeNotEqualTo(String value) {
            addCriterion("gather_type <>", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeGreaterThan(String value) {
            addCriterion("gather_type >", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeGreaterThanOrEqualTo(String value) {
            addCriterion("gather_type >=", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeLessThan(String value) {
            addCriterion("gather_type <", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeLessThanOrEqualTo(String value) {
            addCriterion("gather_type <=", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeLike(String value) {
            addCriterion("gather_type like", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeNotLike(String value) {
            addCriterion("gather_type not like", value, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeIn(List<String> values) {
            addCriterion("gather_type in", values, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeNotIn(List<String> values) {
            addCriterion("gather_type not in", values, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeBetween(String value1, String value2) {
            addCriterion("gather_type between", value1, value2, "gatherType");
            return (Criteria) this;
        }

        public Criteria andGatherTypeNotBetween(String value1, String value2) {
            addCriterion("gather_type not between", value1, value2, "gatherType");
            return (Criteria) this;
        }

        public Criteria andClientVersionIsNull() {
            addCriterion("client_version is null");
            return (Criteria) this;
        }

        public Criteria andClientVersionIsNotNull() {
            addCriterion("client_version is not null");
            return (Criteria) this;
        }

        public Criteria andClientVersionEqualTo(String value) {
            addCriterion("client_version =", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionNotEqualTo(String value) {
            addCriterion("client_version <>", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionGreaterThan(String value) {
            addCriterion("client_version >", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionGreaterThanOrEqualTo(String value) {
            addCriterion("client_version >=", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionLessThan(String value) {
            addCriterion("client_version <", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionLessThanOrEqualTo(String value) {
            addCriterion("client_version <=", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionLike(String value) {
            addCriterion("client_version like", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionNotLike(String value) {
            addCriterion("client_version not like", value, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionIn(List<String> values) {
            addCriterion("client_version in", values, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionNotIn(List<String> values) {
            addCriterion("client_version not in", values, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionBetween(String value1, String value2) {
            addCriterion("client_version between", value1, value2, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientVersionNotBetween(String value1, String value2) {
            addCriterion("client_version not between", value1, value2, "clientVersion");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNull() {
            addCriterion("client_ip is null");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNotNull() {
            addCriterion("client_ip is not null");
            return (Criteria) this;
        }

        public Criteria andClientIpEqualTo(String value) {
            addCriterion("client_ip =", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotEqualTo(String value) {
            addCriterion("client_ip <>", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThan(String value) {
            addCriterion("client_ip >", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThanOrEqualTo(String value) {
            addCriterion("client_ip >=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThan(String value) {
            addCriterion("client_ip <", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThanOrEqualTo(String value) {
            addCriterion("client_ip <=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLike(String value) {
            addCriterion("client_ip like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotLike(String value) {
            addCriterion("client_ip not like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpIn(List<String> values) {
            addCriterion("client_ip in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotIn(List<String> values) {
            addCriterion("client_ip not in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpBetween(String value1, String value2) {
            addCriterion("client_ip between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotBetween(String value1, String value2) {
            addCriterion("client_ip not between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andImeiIsNull() {
            addCriterion("imei is null");
            return (Criteria) this;
        }

        public Criteria andImeiIsNotNull() {
            addCriterion("imei is not null");
            return (Criteria) this;
        }

        public Criteria andImeiEqualTo(String value) {
            addCriterion("imei =", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotEqualTo(String value) {
            addCriterion("imei <>", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThan(String value) {
            addCriterion("imei >", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiGreaterThanOrEqualTo(String value) {
            addCriterion("imei >=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThan(String value) {
            addCriterion("imei <", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLessThanOrEqualTo(String value) {
            addCriterion("imei <=", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiLike(String value) {
            addCriterion("imei like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotLike(String value) {
            addCriterion("imei not like", value, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiIn(List<String> values) {
            addCriterion("imei in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotIn(List<String> values) {
            addCriterion("imei not in", values, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiBetween(String value1, String value2) {
            addCriterion("imei between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andImeiNotBetween(String value1, String value2) {
            addCriterion("imei not between", value1, value2, "imei");
            return (Criteria) this;
        }

        public Criteria andMacIsNull() {
            addCriterion("mac is null");
            return (Criteria) this;
        }

        public Criteria andMacIsNotNull() {
            addCriterion("mac is not null");
            return (Criteria) this;
        }

        public Criteria andMacEqualTo(String value) {
            addCriterion("mac =", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotEqualTo(String value) {
            addCriterion("mac <>", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThan(String value) {
            addCriterion("mac >", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacGreaterThanOrEqualTo(String value) {
            addCriterion("mac >=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThan(String value) {
            addCriterion("mac <", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLessThanOrEqualTo(String value) {
            addCriterion("mac <=", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacLike(String value) {
            addCriterion("mac like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotLike(String value) {
            addCriterion("mac not like", value, "mac");
            return (Criteria) this;
        }

        public Criteria andMacIn(List<String> values) {
            addCriterion("mac in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotIn(List<String> values) {
            addCriterion("mac not in", values, "mac");
            return (Criteria) this;
        }

        public Criteria andMacBetween(String value1, String value2) {
            addCriterion("mac between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andMacNotBetween(String value1, String value2) {
            addCriterion("mac not between", value1, value2, "mac");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorIsNull() {
            addCriterion("network_operator is null");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorIsNotNull() {
            addCriterion("network_operator is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorEqualTo(String value) {
            addCriterion("network_operator =", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorNotEqualTo(String value) {
            addCriterion("network_operator <>", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorGreaterThan(String value) {
            addCriterion("network_operator >", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("network_operator >=", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorLessThan(String value) {
            addCriterion("network_operator <", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorLessThanOrEqualTo(String value) {
            addCriterion("network_operator <=", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorLike(String value) {
            addCriterion("network_operator like", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorNotLike(String value) {
            addCriterion("network_operator not like", value, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorIn(List<String> values) {
            addCriterion("network_operator in", values, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorNotIn(List<String> values) {
            addCriterion("network_operator not in", values, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorBetween(String value1, String value2) {
            addCriterion("network_operator between", value1, value2, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkOperatorNotBetween(String value1, String value2) {
            addCriterion("network_operator not between", value1, value2, "networkOperator");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeIsNull() {
            addCriterion("network_type is null");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeIsNotNull() {
            addCriterion("network_type is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeEqualTo(String value) {
            addCriterion("network_type =", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeNotEqualTo(String value) {
            addCriterion("network_type <>", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeGreaterThan(String value) {
            addCriterion("network_type >", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeGreaterThanOrEqualTo(String value) {
            addCriterion("network_type >=", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeLessThan(String value) {
            addCriterion("network_type <", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeLessThanOrEqualTo(String value) {
            addCriterion("network_type <=", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeLike(String value) {
            addCriterion("network_type like", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeNotLike(String value) {
            addCriterion("network_type not like", value, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeIn(List<String> values) {
            addCriterion("network_type in", values, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeNotIn(List<String> values) {
            addCriterion("network_type not in", values, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeBetween(String value1, String value2) {
            addCriterion("network_type between", value1, value2, "networkType");
            return (Criteria) this;
        }

        public Criteria andNetworkTypeNotBetween(String value1, String value2) {
            addCriterion("network_type not between", value1, value2, "networkType");
            return (Criteria) this;
        }

        public Criteria andPadCodeIsNull() {
            addCriterion("pad_code is null");
            return (Criteria) this;
        }

        public Criteria andPadCodeIsNotNull() {
            addCriterion("pad_code is not null");
            return (Criteria) this;
        }

        public Criteria andPadCodeEqualTo(String value) {
            addCriterion("pad_code =", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeNotEqualTo(String value) {
            addCriterion("pad_code <>", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeGreaterThan(String value) {
            addCriterion("pad_code >", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeGreaterThanOrEqualTo(String value) {
            addCriterion("pad_code >=", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeLessThan(String value) {
            addCriterion("pad_code <", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeLessThanOrEqualTo(String value) {
            addCriterion("pad_code <=", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeLike(String value) {
            addCriterion("pad_code like", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeNotLike(String value) {
            addCriterion("pad_code not like", value, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeIn(List<String> values) {
            addCriterion("pad_code in", values, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeNotIn(List<String> values) {
            addCriterion("pad_code not in", values, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeBetween(String value1, String value2) {
            addCriterion("pad_code between", value1, value2, "padCode");
            return (Criteria) this;
        }

        public Criteria andPadCodeNotBetween(String value1, String value2) {
            addCriterion("pad_code not between", value1, value2, "padCode");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayIsNull() {
            addCriterion("network_delay is null");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayIsNotNull() {
            addCriterion("network_delay is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayEqualTo(Integer value) {
            addCriterion("network_delay =", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayNotEqualTo(Integer value) {
            addCriterion("network_delay <>", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayGreaterThan(Integer value) {
            addCriterion("network_delay >", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayGreaterThanOrEqualTo(Integer value) {
            addCriterion("network_delay >=", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayLessThan(Integer value) {
            addCriterion("network_delay <", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayLessThanOrEqualTo(Integer value) {
            addCriterion("network_delay <=", value, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayIn(List<Integer> values) {
            addCriterion("network_delay in", values, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayNotIn(List<Integer> values) {
            addCriterion("network_delay not in", values, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayBetween(Integer value1, Integer value2) {
            addCriterion("network_delay between", value1, value2, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDelayNotBetween(Integer value1, Integer value2) {
            addCriterion("network_delay not between", value1, value2, "networkDelay");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailIsNull() {
            addCriterion("network_detail is null");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailIsNotNull() {
            addCriterion("network_detail is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailEqualTo(String value) {
            addCriterion("network_detail =", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailNotEqualTo(String value) {
            addCriterion("network_detail <>", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailGreaterThan(String value) {
            addCriterion("network_detail >", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailGreaterThanOrEqualTo(String value) {
            addCriterion("network_detail >=", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailLessThan(String value) {
            addCriterion("network_detail <", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailLessThanOrEqualTo(String value) {
            addCriterion("network_detail <=", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailLike(String value) {
            addCriterion("network_detail like", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailNotLike(String value) {
            addCriterion("network_detail not like", value, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailIn(List<String> values) {
            addCriterion("network_detail in", values, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailNotIn(List<String> values) {
            addCriterion("network_detail not in", values, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailBetween(String value1, String value2) {
            addCriterion("network_detail between", value1, value2, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andNetworkDetailNotBetween(String value1, String value2) {
            addCriterion("network_detail not between", value1, value2, "networkDetail");
            return (Criteria) this;
        }

        public Criteria andPingOkIsNull() {
            addCriterion("ping_ok is null");
            return (Criteria) this;
        }

        public Criteria andPingOkIsNotNull() {
            addCriterion("ping_ok is not null");
            return (Criteria) this;
        }

        public Criteria andPingOkEqualTo(Integer value) {
            addCriterion("ping_ok =", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkNotEqualTo(Integer value) {
            addCriterion("ping_ok <>", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkGreaterThan(Integer value) {
            addCriterion("ping_ok >", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkGreaterThanOrEqualTo(Integer value) {
            addCriterion("ping_ok >=", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkLessThan(Integer value) {
            addCriterion("ping_ok <", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkLessThanOrEqualTo(Integer value) {
            addCriterion("ping_ok <=", value, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkIn(List<Integer> values) {
            addCriterion("ping_ok in", values, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkNotIn(List<Integer> values) {
            addCriterion("ping_ok not in", values, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkBetween(Integer value1, Integer value2) {
            addCriterion("ping_ok between", value1, value2, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingOkNotBetween(Integer value1, Integer value2) {
            addCriterion("ping_ok not between", value1, value2, "pingOk");
            return (Criteria) this;
        }

        public Criteria andPingResultIsNull() {
            addCriterion("ping_result is null");
            return (Criteria) this;
        }

        public Criteria andPingResultIsNotNull() {
            addCriterion("ping_result is not null");
            return (Criteria) this;
        }

        public Criteria andPingResultEqualTo(Integer value) {
            addCriterion("ping_result =", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultNotEqualTo(Integer value) {
            addCriterion("ping_result <>", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultGreaterThan(Integer value) {
            addCriterion("ping_result >", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("ping_result >=", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultLessThan(Integer value) {
            addCriterion("ping_result <", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultLessThanOrEqualTo(Integer value) {
            addCriterion("ping_result <=", value, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultIn(List<Integer> values) {
            addCriterion("ping_result in", values, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultNotIn(List<Integer> values) {
            addCriterion("ping_result not in", values, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultBetween(Integer value1, Integer value2) {
            addCriterion("ping_result between", value1, value2, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingResultNotBetween(Integer value1, Integer value2) {
            addCriterion("ping_result not between", value1, value2, "pingResult");
            return (Criteria) this;
        }

        public Criteria andPingContentIsNull() {
            addCriterion("ping_content is null");
            return (Criteria) this;
        }

        public Criteria andPingContentIsNotNull() {
            addCriterion("ping_content is not null");
            return (Criteria) this;
        }

        public Criteria andPingContentEqualTo(String value) {
            addCriterion("ping_content =", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentNotEqualTo(String value) {
            addCriterion("ping_content <>", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentGreaterThan(String value) {
            addCriterion("ping_content >", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentGreaterThanOrEqualTo(String value) {
            addCriterion("ping_content >=", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentLessThan(String value) {
            addCriterion("ping_content <", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentLessThanOrEqualTo(String value) {
            addCriterion("ping_content <=", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentLike(String value) {
            addCriterion("ping_content like", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentNotLike(String value) {
            addCriterion("ping_content not like", value, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentIn(List<String> values) {
            addCriterion("ping_content in", values, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentNotIn(List<String> values) {
            addCriterion("ping_content not in", values, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentBetween(String value1, String value2) {
            addCriterion("ping_content between", value1, value2, "pingContent");
            return (Criteria) this;
        }

        public Criteria andPingContentNotBetween(String value1, String value2) {
            addCriterion("ping_content not between", value1, value2, "pingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingOkIsNull() {
            addCriterion("tcping_ok is null");
            return (Criteria) this;
        }

        public Criteria andTcpingOkIsNotNull() {
            addCriterion("tcping_ok is not null");
            return (Criteria) this;
        }

        public Criteria andTcpingOkEqualTo(Integer value) {
            addCriterion("tcping_ok =", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkNotEqualTo(Integer value) {
            addCriterion("tcping_ok <>", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkGreaterThan(Integer value) {
            addCriterion("tcping_ok >", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkGreaterThanOrEqualTo(Integer value) {
            addCriterion("tcping_ok >=", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkLessThan(Integer value) {
            addCriterion("tcping_ok <", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkLessThanOrEqualTo(Integer value) {
            addCriterion("tcping_ok <=", value, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkIn(List<Integer> values) {
            addCriterion("tcping_ok in", values, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkNotIn(List<Integer> values) {
            addCriterion("tcping_ok not in", values, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkBetween(Integer value1, Integer value2) {
            addCriterion("tcping_ok between", value1, value2, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingOkNotBetween(Integer value1, Integer value2) {
            addCriterion("tcping_ok not between", value1, value2, "tcpingOk");
            return (Criteria) this;
        }

        public Criteria andTcpingResultIsNull() {
            addCriterion("tcping_result is null");
            return (Criteria) this;
        }

        public Criteria andTcpingResultIsNotNull() {
            addCriterion("tcping_result is not null");
            return (Criteria) this;
        }

        public Criteria andTcpingResultEqualTo(Integer value) {
            addCriterion("tcping_result =", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultNotEqualTo(Integer value) {
            addCriterion("tcping_result <>", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultGreaterThan(Integer value) {
            addCriterion("tcping_result >", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("tcping_result >=", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultLessThan(Integer value) {
            addCriterion("tcping_result <", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultLessThanOrEqualTo(Integer value) {
            addCriterion("tcping_result <=", value, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultIn(List<Integer> values) {
            addCriterion("tcping_result in", values, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultNotIn(List<Integer> values) {
            addCriterion("tcping_result not in", values, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultBetween(Integer value1, Integer value2) {
            addCriterion("tcping_result between", value1, value2, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingResultNotBetween(Integer value1, Integer value2) {
            addCriterion("tcping_result not between", value1, value2, "tcpingResult");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescIsNull() {
            addCriterion("tcping_fail_desc is null");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescIsNotNull() {
            addCriterion("tcping_fail_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescEqualTo(String value) {
            addCriterion("tcping_fail_desc =", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescNotEqualTo(String value) {
            addCriterion("tcping_fail_desc <>", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescGreaterThan(String value) {
            addCriterion("tcping_fail_desc >", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescGreaterThanOrEqualTo(String value) {
            addCriterion("tcping_fail_desc >=", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescLessThan(String value) {
            addCriterion("tcping_fail_desc <", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescLessThanOrEqualTo(String value) {
            addCriterion("tcping_fail_desc <=", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescLike(String value) {
            addCriterion("tcping_fail_desc like", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescNotLike(String value) {
            addCriterion("tcping_fail_desc not like", value, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescIn(List<String> values) {
            addCriterion("tcping_fail_desc in", values, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescNotIn(List<String> values) {
            addCriterion("tcping_fail_desc not in", values, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescBetween(String value1, String value2) {
            addCriterion("tcping_fail_desc between", value1, value2, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingFailDescNotBetween(String value1, String value2) {
            addCriterion("tcping_fail_desc not between", value1, value2, "tcpingFailDesc");
            return (Criteria) this;
        }

        public Criteria andTcpingContentIsNull() {
            addCriterion("tcping_content is null");
            return (Criteria) this;
        }

        public Criteria andTcpingContentIsNotNull() {
            addCriterion("tcping_content is not null");
            return (Criteria) this;
        }

        public Criteria andTcpingContentEqualTo(String value) {
            addCriterion("tcping_content =", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentNotEqualTo(String value) {
            addCriterion("tcping_content <>", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentGreaterThan(String value) {
            addCriterion("tcping_content >", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentGreaterThanOrEqualTo(String value) {
            addCriterion("tcping_content >=", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentLessThan(String value) {
            addCriterion("tcping_content <", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentLessThanOrEqualTo(String value) {
            addCriterion("tcping_content <=", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentLike(String value) {
            addCriterion("tcping_content like", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentNotLike(String value) {
            addCriterion("tcping_content not like", value, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentIn(List<String> values) {
            addCriterion("tcping_content in", values, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentNotIn(List<String> values) {
            addCriterion("tcping_content not in", values, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentBetween(String value1, String value2) {
            addCriterion("tcping_content between", value1, value2, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTcpingContentNotBetween(String value1, String value2) {
            addCriterion("tcping_content not between", value1, value2, "tcpingContent");
            return (Criteria) this;
        }

        public Criteria andTraceOkIsNull() {
            addCriterion("trace_ok is null");
            return (Criteria) this;
        }

        public Criteria andTraceOkIsNotNull() {
            addCriterion("trace_ok is not null");
            return (Criteria) this;
        }

        public Criteria andTraceOkEqualTo(Integer value) {
            addCriterion("trace_ok =", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkNotEqualTo(Integer value) {
            addCriterion("trace_ok <>", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkGreaterThan(Integer value) {
            addCriterion("trace_ok >", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkGreaterThanOrEqualTo(Integer value) {
            addCriterion("trace_ok >=", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkLessThan(Integer value) {
            addCriterion("trace_ok <", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkLessThanOrEqualTo(Integer value) {
            addCriterion("trace_ok <=", value, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkIn(List<Integer> values) {
            addCriterion("trace_ok in", values, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkNotIn(List<Integer> values) {
            addCriterion("trace_ok not in", values, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkBetween(Integer value1, Integer value2) {
            addCriterion("trace_ok between", value1, value2, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceOkNotBetween(Integer value1, Integer value2) {
            addCriterion("trace_ok not between", value1, value2, "traceOk");
            return (Criteria) this;
        }

        public Criteria andTraceResultIsNull() {
            addCriterion("trace_result is null");
            return (Criteria) this;
        }

        public Criteria andTraceResultIsNotNull() {
            addCriterion("trace_result is not null");
            return (Criteria) this;
        }

        public Criteria andTraceResultEqualTo(Integer value) {
            addCriterion("trace_result =", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultNotEqualTo(Integer value) {
            addCriterion("trace_result <>", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultGreaterThan(Integer value) {
            addCriterion("trace_result >", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("trace_result >=", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultLessThan(Integer value) {
            addCriterion("trace_result <", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultLessThanOrEqualTo(Integer value) {
            addCriterion("trace_result <=", value, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultIn(List<Integer> values) {
            addCriterion("trace_result in", values, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultNotIn(List<Integer> values) {
            addCriterion("trace_result not in", values, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultBetween(Integer value1, Integer value2) {
            addCriterion("trace_result between", value1, value2, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceResultNotBetween(Integer value1, Integer value2) {
            addCriterion("trace_result not between", value1, value2, "traceResult");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescIsNull() {
            addCriterion("trace_fail_desc is null");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescIsNotNull() {
            addCriterion("trace_fail_desc is not null");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescEqualTo(String value) {
            addCriterion("trace_fail_desc =", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescNotEqualTo(String value) {
            addCriterion("trace_fail_desc <>", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescGreaterThan(String value) {
            addCriterion("trace_fail_desc >", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescGreaterThanOrEqualTo(String value) {
            addCriterion("trace_fail_desc >=", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescLessThan(String value) {
            addCriterion("trace_fail_desc <", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescLessThanOrEqualTo(String value) {
            addCriterion("trace_fail_desc <=", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescLike(String value) {
            addCriterion("trace_fail_desc like", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescNotLike(String value) {
            addCriterion("trace_fail_desc not like", value, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescIn(List<String> values) {
            addCriterion("trace_fail_desc in", values, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescNotIn(List<String> values) {
            addCriterion("trace_fail_desc not in", values, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescBetween(String value1, String value2) {
            addCriterion("trace_fail_desc between", value1, value2, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceFailDescNotBetween(String value1, String value2) {
            addCriterion("trace_fail_desc not between", value1, value2, "traceFailDesc");
            return (Criteria) this;
        }

        public Criteria andTraceContentIsNull() {
            addCriterion("trace_content is null");
            return (Criteria) this;
        }

        public Criteria andTraceContentIsNotNull() {
            addCriterion("trace_content is not null");
            return (Criteria) this;
        }

        public Criteria andTraceContentEqualTo(String value) {
            addCriterion("trace_content =", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentNotEqualTo(String value) {
            addCriterion("trace_content <>", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentGreaterThan(String value) {
            addCriterion("trace_content >", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentGreaterThanOrEqualTo(String value) {
            addCriterion("trace_content >=", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentLessThan(String value) {
            addCriterion("trace_content <", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentLessThanOrEqualTo(String value) {
            addCriterion("trace_content <=", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentLike(String value) {
            addCriterion("trace_content like", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentNotLike(String value) {
            addCriterion("trace_content not like", value, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentIn(List<String> values) {
            addCriterion("trace_content in", values, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentNotIn(List<String> values) {
            addCriterion("trace_content not in", values, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentBetween(String value1, String value2) {
            addCriterion("trace_content between", value1, value2, "traceContent");
            return (Criteria) this;
        }

        public Criteria andTraceContentNotBetween(String value1, String value2) {
            addCriterion("trace_content not between", value1, value2, "traceContent");
            return (Criteria) this;
        }

        public Criteria andClientTimeIsNull() {
            addCriterion("client_time is null");
            return (Criteria) this;
        }

        public Criteria andClientTimeIsNotNull() {
            addCriterion("client_time is not null");
            return (Criteria) this;
        }

        public Criteria andClientTimeEqualTo(Date value) {
            addCriterion("client_time =", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeNotEqualTo(Date value) {
            addCriterion("client_time <>", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeGreaterThan(Date value) {
            addCriterion("client_time >", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("client_time >=", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeLessThan(Date value) {
            addCriterion("client_time <", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeLessThanOrEqualTo(Date value) {
            addCriterion("client_time <=", value, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeIn(List<Date> values) {
            addCriterion("client_time in", values, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeNotIn(List<Date> values) {
            addCriterion("client_time not in", values, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeBetween(Date value1, Date value2) {
            addCriterion("client_time between", value1, value2, "clientTime");
            return (Criteria) this;
        }

        public Criteria andClientTimeNotBetween(Date value1, Date value2) {
            addCriterion("client_time not between", value1, value2, "clientTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeIsNull() {
            addCriterion("cloud_receive_time is null");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeIsNotNull() {
            addCriterion("cloud_receive_time is not null");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeEqualTo(Date value) {
            addCriterion("cloud_receive_time =", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeNotEqualTo(Date value) {
            addCriterion("cloud_receive_time <>", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeGreaterThan(Date value) {
            addCriterion("cloud_receive_time >", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cloud_receive_time >=", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeLessThan(Date value) {
            addCriterion("cloud_receive_time <", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("cloud_receive_time <=", value, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeIn(List<Date> values) {
            addCriterion("cloud_receive_time in", values, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeNotIn(List<Date> values) {
            addCriterion("cloud_receive_time not in", values, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeBetween(Date value1, Date value2) {
            addCriterion("cloud_receive_time between", value1, value2, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudReceiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("cloud_receive_time not between", value1, value2, "cloudReceiveTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeIsNull() {
            addCriterion("cloud_send_time is null");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeIsNotNull() {
            addCriterion("cloud_send_time is not null");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeEqualTo(Date value) {
            addCriterion("cloud_send_time =", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeNotEqualTo(Date value) {
            addCriterion("cloud_send_time <>", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeGreaterThan(Date value) {
            addCriterion("cloud_send_time >", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cloud_send_time >=", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeLessThan(Date value) {
            addCriterion("cloud_send_time <", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("cloud_send_time <=", value, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeIn(List<Date> values) {
            addCriterion("cloud_send_time in", values, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeNotIn(List<Date> values) {
            addCriterion("cloud_send_time not in", values, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeBetween(Date value1, Date value2) {
            addCriterion("cloud_send_time between", value1, value2, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andCloudSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("cloud_send_time not between", value1, value2, "cloudSendTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeIsNull() {
            addCriterion("server_time is null");
            return (Criteria) this;
        }

        public Criteria andServerTimeIsNotNull() {
            addCriterion("server_time is not null");
            return (Criteria) this;
        }

        public Criteria andServerTimeEqualTo(Date value) {
            addCriterion("server_time =", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotEqualTo(Date value) {
            addCriterion("server_time <>", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeGreaterThan(Date value) {
            addCriterion("server_time >", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("server_time >=", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeLessThan(Date value) {
            addCriterion("server_time <", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeLessThanOrEqualTo(Date value) {
            addCriterion("server_time <=", value, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeIn(List<Date> values) {
            addCriterion("server_time in", values, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotIn(List<Date> values) {
            addCriterion("server_time not in", values, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeBetween(Date value1, Date value2) {
            addCriterion("server_time between", value1, value2, "serverTime");
            return (Criteria) this;
        }

        public Criteria andServerTimeNotBetween(Date value1, Date value2) {
            addCriterion("server_time not between", value1, value2, "serverTime");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneIsNull() {
            addCriterion("user_mobile_phone is null");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneIsNotNull() {
            addCriterion("user_mobile_phone is not null");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneEqualTo(String value) {
            addCriterion("user_mobile_phone =", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneNotEqualTo(String value) {
            addCriterion("user_mobile_phone <>", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneGreaterThan(String value) {
            addCriterion("user_mobile_phone >", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneGreaterThanOrEqualTo(String value) {
            addCriterion("user_mobile_phone >=", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneLessThan(String value) {
            addCriterion("user_mobile_phone <", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneLessThanOrEqualTo(String value) {
            addCriterion("user_mobile_phone <=", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneLike(String value) {
            addCriterion("user_mobile_phone like", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneNotLike(String value) {
            addCriterion("user_mobile_phone not like", value, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneIn(List<String> values) {
            addCriterion("user_mobile_phone in", values, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneNotIn(List<String> values) {
            addCriterion("user_mobile_phone not in", values, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneBetween(String value1, String value2) {
            addCriterion("user_mobile_phone between", value1, value2, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andUserMobilePhoneNotBetween(String value1, String value2) {
            addCriterion("user_mobile_phone not between", value1, value2, "userMobilePhone");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdIsNull() {
            addCriterion("external_user_id is null");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdIsNotNull() {
            addCriterion("external_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdEqualTo(Integer value) {
            addCriterion("external_user_id =", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdNotEqualTo(Integer value) {
            addCriterion("external_user_id <>", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdGreaterThan(Integer value) {
            addCriterion("external_user_id >", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("external_user_id >=", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdLessThan(Integer value) {
            addCriterion("external_user_id <", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("external_user_id <=", value, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdIn(List<Integer> values) {
            addCriterion("external_user_id in", values, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdNotIn(List<Integer> values) {
            addCriterion("external_user_id not in", values, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdBetween(Integer value1, Integer value2) {
            addCriterion("external_user_id between", value1, value2, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andExternalUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("external_user_id not between", value1, value2, "externalUserId");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNull() {
            addCriterion("creater is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIsNotNull() {
            addCriterion("creater is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterEqualTo(String value) {
            addCriterion("creater =", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotEqualTo(String value) {
            addCriterion("creater <>", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThan(String value) {
            addCriterion("creater >", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterGreaterThanOrEqualTo(String value) {
            addCriterion("creater >=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThan(String value) {
            addCriterion("creater <", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLessThanOrEqualTo(String value) {
            addCriterion("creater <=", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterLike(String value) {
            addCriterion("creater like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotLike(String value) {
            addCriterion("creater not like", value, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterIn(List<String> values) {
            addCriterion("creater in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotIn(List<String> values) {
            addCriterion("creater not in", values, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterBetween(String value1, String value2) {
            addCriterion("creater between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreaterNotBetween(String value1, String value2) {
            addCriterion("creater not between", value1, value2, "creater");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNull() {
            addCriterion("enable_status is null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIsNotNull() {
            addCriterion("enable_status is not null");
            return (Criteria) this;
        }

        public Criteria andEnableStatusEqualTo(String value) {
            addCriterion("enable_status =", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotEqualTo(String value) {
            addCriterion("enable_status <>", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThan(String value) {
            addCriterion("enable_status >", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusGreaterThanOrEqualTo(String value) {
            addCriterion("enable_status >=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThan(String value) {
            addCriterion("enable_status <", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLessThanOrEqualTo(String value) {
            addCriterion("enable_status <=", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusLike(String value) {
            addCriterion("enable_status like", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotLike(String value) {
            addCriterion("enable_status not like", value, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusIn(List<String> values) {
            addCriterion("enable_status in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotIn(List<String> values) {
            addCriterion("enable_status not in", values, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusBetween(String value1, String value2) {
            addCriterion("enable_status between", value1, value2, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andEnableStatusNotBetween(String value1, String value2) {
            addCriterion("enable_status not between", value1, value2, "enableStatus");
            return (Criteria) this;
        }

        public Criteria andReorderIsNull() {
            addCriterion("reorder is null");
            return (Criteria) this;
        }

        public Criteria andReorderIsNotNull() {
            addCriterion("reorder is not null");
            return (Criteria) this;
        }

        public Criteria andReorderEqualTo(Integer value) {
            addCriterion("reorder =", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderNotEqualTo(Integer value) {
            addCriterion("reorder <>", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderGreaterThan(Integer value) {
            addCriterion("reorder >", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("reorder >=", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderLessThan(Integer value) {
            addCriterion("reorder <", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderLessThanOrEqualTo(Integer value) {
            addCriterion("reorder <=", value, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderIn(List<Integer> values) {
            addCriterion("reorder in", values, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderNotIn(List<Integer> values) {
            addCriterion("reorder not in", values, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderBetween(Integer value1, Integer value2) {
            addCriterion("reorder between", value1, value2, "reorder");
            return (Criteria) this;
        }

        public Criteria andReorderNotBetween(Integer value1, Integer value2) {
            addCriterion("reorder not between", value1, value2, "reorder");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCuidIsNull() {
            addCriterion("cuid is null");
            return (Criteria) this;
        }

        public Criteria andCuidIsNotNull() {
            addCriterion("cuid is not null");
            return (Criteria) this;
        }

        public Criteria andCuidEqualTo(String value) {
            addCriterion("cuid =", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotEqualTo(String value) {
            addCriterion("cuid <>", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidGreaterThan(String value) {
            addCriterion("cuid >", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidGreaterThanOrEqualTo(String value) {
            addCriterion("cuid >=", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidLessThan(String value) {
            addCriterion("cuid <", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidLessThanOrEqualTo(String value) {
            addCriterion("cuid <=", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidLike(String value) {
            addCriterion("cuid like", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotLike(String value) {
            addCriterion("cuid not like", value, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidIn(List<String> values) {
            addCriterion("cuid in", values, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotIn(List<String> values) {
            addCriterion("cuid not in", values, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidBetween(String value1, String value2) {
            addCriterion("cuid between", value1, value2, "cuid");
            return (Criteria) this;
        }

        public Criteria andCuidNotBetween(String value1, String value2) {
            addCriterion("cuid not between", value1, value2, "cuid");
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