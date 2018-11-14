package com.hiekn.china.aeronautical.service;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.vo.RuleQuery;

public interface RuleService {

    RestData<Rule> findAll(RuleQuery bean);

    Rule findOne(String id);

    void delete(String id);

    Rule modify(String id, Rule dataset);

    Rule add(Rule dataset);
}
