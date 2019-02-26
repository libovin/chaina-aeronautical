package com.hiekn.china.aeronautical;

import com.hiekn.china.aeronautical.knowledge.ConferenceKgService;
import com.hiekn.china.aeronautical.model.bean.Conference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChinaAeronauticalApplicationTest {

    @Autowired
    private ConferenceKgService conferenceKgService;

    private static String KG_DB_NAME = "abcd_1234_7349l1owxwqjqw3a";

    @Test
    public void contextLoads() {
        conferenceKgService.insert(KG_DB_NAME, new Conference());
    }
}
