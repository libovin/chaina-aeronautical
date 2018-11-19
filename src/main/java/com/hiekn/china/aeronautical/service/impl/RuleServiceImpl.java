package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.RuleModel;
import com.hiekn.china.aeronautical.model.vo.CheckRule;
import com.hiekn.china.aeronautical.model.vo.RuleQuery;
import com.hiekn.china.aeronautical.repository.DictRepository;
import com.hiekn.china.aeronautical.repository.RuleRepository;
import com.hiekn.china.aeronautical.service.RuleService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private DictRepository dictRepository;

    @Override
    public RestData<Rule> findAll(RuleQuery bean) {
        Rule targe = new Rule();
        Map<String, Object> map = QueryUtils.trastation(bean, targe);
        Example<Rule> example = Example.of((Rule) map.get("bean"));
        return new RestData<>(ruleRepository.findAll(example), ruleRepository.count(example));
    }

    @Override
    public Rule findOne(String id) {
        return ruleRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        ruleRepository.delete(id);
    }

    @Override
    public Rule modify(String id, Rule rule) {
        Rule targe = ruleRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(rule);
        BeanUtils.copyProperties(rule, targe, stringArr);
        return ruleRepository.save(targe);
    }

    @Override
    public Rule add(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public Map<String, Object> check(CheckRule checkRule) throws RestException {
        Map<String, Object> map = new HashMap<>(2);
        String content = checkRule.getContent();
        List<RuleModel> ruleModels = checkRule.getRules();
        StringBuilder sb = new StringBuilder();
        ruleModels.forEach(rm -> {
            if ("dict".equals(rm.getType())) {
                String id = rm.getValue();
                if(!dictRepository.exists(id)) {
                    throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
                }
                Dict dict = dictRepository.findOne(id);
                String s = String.join("|",dict.getText());
                sb.append("(");
                sb.append(s);
                sb.append(")");
            } else if ("regex".equals(rm.getType())) {
                sb.append(rm.getValue());
            } else {
                throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
            }
        });
        Pattern pattern = Pattern.compile(sb.toString());
        Matcher matcher = pattern.matcher(content);
        map.put("rules", checkRule.getRules());
        map.put("result", matcher.matches());
        return map;
    }
}
