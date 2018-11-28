package com.hiekn.china.aeronautical.util;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.RuleModel;
import com.hiekn.china.aeronautical.repository.DictRepository;
import com.hiekn.china.aeronautical.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class RuleUtils {
    public static RuleUtils instance;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private DictRepository dictRepository;

    private Map<String, Pattern> ruleMap;
    private Map<String, String> dictMap;


    @PostConstruct
    public void init() {
        RuleUtils.instance = this;
        refreshRuleMap();
    }


    public void refreshDictMap() {
        dictMap = new HashMap<>();
        List<Dict> dicts = dictRepository.findAll();
        for (Dict dict : dicts) {
            dictMap.put(dict.getId(), String.join("|", dict.getText()));
        }
    }

    public void refreshRuleMap() {
        ruleMap = new HashMap<>();
        refreshDictMap();
        List<Rule> rules = ruleRepository.findAll();
        for (Rule rule : rules) {
            List<RuleModel> ruleModels = rule.getRules();
            StringBuilder sb = new StringBuilder();
            ruleModels.forEach(rm -> {
                if (Objects.equals("dict", rm.getType())) {
                    String words = dictMap.get(rm.getValue());
                    if (words == null) {
                        throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
                    }
                    sb.append("(");
                    sb.append(words);
                    sb.append(")");
                } else if (Objects.equals("regex", rm.getType())) {
                    sb.append(rm.getValue());
                } else {
                    throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
                }
            });
            Pattern pattern = Pattern.compile(sb.toString());
            ruleMap.put(rule.getId(), pattern);
        }
    }

    public Pattern getRulePattern(String id) {
        Pattern pattern = ruleMap.get(id);
        if (pattern == null) {
            throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
        }
        return pattern;
    }

}
