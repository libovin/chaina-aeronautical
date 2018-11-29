package com.hiekn.china.aeronautical.util;

import com.hiekn.boot.autoconfigure.base.exception.RestException;
import com.hiekn.china.aeronautical.exception.ErrorCodes;
import com.hiekn.china.aeronautical.model.bean.Dict;
import com.hiekn.china.aeronautical.model.bean.Rule;
import com.hiekn.china.aeronautical.model.bean.RuleModel;
import com.hiekn.china.aeronautical.repository.DictRepository;
import com.hiekn.china.aeronautical.repository.RuleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Component
@Log
public class RuleUtils {


    public static RuleUtils instance;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private DictRepository dictRepository;

    private Map<String, Pattern> ruleMap;
    private Map<String, String> dictMap;
    private Map<String, Set<String>> dictRuleMap;

    @PostConstruct
    public void init() {
        RuleUtils.instance = this;
        refreshRuleMap();
    }


    public void refreshDictMap() {
        dictMap = new ConcurrentHashMap<>();
        List<Dict> dicts = dictRepository.findAll();
        for (Dict dict : dicts) {
            dictMap.put(dict.getId(), String.join("|", dict.getText()));
        }
    }

    public void refreshDictMap(String id) {
        Dict dict = dictRepository.findOne(id);
        if (dict != null) {
            dictMap.put(id, String.join("|", dict.getText()));

        } else {
            log.warning("字典id：" + id + "--数据库未找到");
        }

    }


    public void refreshRuleMap() {
        ruleMap = new ConcurrentHashMap<>();
        dictRuleMap = new ConcurrentHashMap<>();
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
                    Set<String> set = dictRuleMap.computeIfAbsent(rm.getValue(), k -> new HashSet<>());
                    set.add(rule.getId());
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

    public void refreshRuleMap(String id) {
        Rule rule = ruleRepository.findOne(id);
        if (rule != null) {
            List<RuleModel> ruleModels = rule.getRules();
            StringBuilder sb = new StringBuilder();
            ruleModels.forEach(rm -> {
                if (Objects.equals("dict", rm.getType())) {
                    String value = rm.getValue();
                    if (!dictRepository.exists(value)) {
                        throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
                    }
                    Dict dict = dictRepository.findOne(value);
                    String s = String.join("|", dict.getText());
                    sb.append("(");
                    sb.append(s);
                    sb.append(")");
                    Set<String> set = dictRuleMap.computeIfAbsent(rm.getValue(), k -> new HashSet<>());
                    set.add(rule.getId());
                } else if (Objects.equals("regex", rm.getType())) {
                    sb.append(rm.getValue());
                } else {
                    throw RestException.newInstance(ErrorCodes.PARAM_CHECKOUT_ERROR);
                }
            });
            Pattern pattern = Pattern.compile(sb.toString());
            ruleMap.put(rule.getId(), pattern);
        } else {
            log.warning("规则id：" + id + "--数据库未找到");
        }
    }

    public Pattern getRulePattern(String id) {
        return ruleMap.computeIfAbsent(id,k->Pattern.compile(""));
    }

}
