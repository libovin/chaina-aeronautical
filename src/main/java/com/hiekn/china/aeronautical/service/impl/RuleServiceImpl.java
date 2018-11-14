package com.hiekn.china.aeronautical.service.impl;

import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.china.aeronautical.model.bean.Dataset;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.vo.RuleQuery;
import com.hiekn.china.aeronautical.repository.RuleRepository;
import com.hiekn.china.aeronautical.service.RuleService;
import com.hiekn.china.aeronautical.util.DataBeanUtils;
import com.hiekn.china.aeronautical.util.QueryUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ruleService")
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public RestData<Rule> findAll(RuleQuery bean) {
        Dataset targe = new Dataset();
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
    public Rule modify(String id, Rule dataset) {
        Rule targe = ruleRepository.findOne(id);
        String[] stringArr = DataBeanUtils.getNullProperty(dataset);
        BeanUtils.copyProperties(dataset, targe, stringArr);
        return ruleRepository.save(targe);
    }

    @Override
    public Rule add(Rule dataset) {
        return ruleRepository.save(dataset);
    }
}
