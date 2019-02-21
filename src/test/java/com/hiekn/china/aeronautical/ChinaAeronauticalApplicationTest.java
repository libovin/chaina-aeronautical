package com.hiekn.china.aeronautical;

import com.hiekn.china.aeronautical.knowledge.BaseKnowledgeService;
import com.hiekn.china.aeronautical.knowledge.bean.AttributeDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChinaAeronauticalApplicationTest {

    @Autowired
    private BaseKnowledgeService baseKnowledgeService;

    private static String KG_DB_NAME = "abcd_1234_7349l1owxwqjqw3a";

    @Test
    public void contextLoads() {
        List<AttributeDefinition> attributeDefinitionList = baseKnowledgeService.getAttributeDefinitionList(KG_DB_NAME);
    }
}
