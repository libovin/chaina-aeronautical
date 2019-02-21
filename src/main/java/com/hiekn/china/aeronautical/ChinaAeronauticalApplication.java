package com.hiekn.china.aeronautical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.RandomValuePropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.context.support.ServletContextPropertySource;

import java.util.Iterator;
import java.util.Map;

@SpringBootApplication
@EnableMongoAuditing
public class ChinaAeronauticalApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ChinaAeronauticalApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println("----------环境变量----------");
        for (Map.Entry<String, Object> x : environment.getSystemEnvironment().entrySet()) {
            System.out.println(x.getKey() + "=" + x.getValue());
        }
        System.out.println("----------系统配置----------");
        for (Map.Entry<String, Object> x : environment.getSystemProperties().entrySet()) {
            System.out.println(x.getKey() + "=" + x.getValue());
        }
        System.out.println("----------程序配置----------");
        MutablePropertySources propertySources = environment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        while (iterator.hasNext()) {
            PropertySource<?> next = iterator.next();
            if (next instanceof MapPropertySource) {
            } else if (next instanceof PropertySource.StubPropertySource) {
            } else if (next instanceof ServletContextPropertySource) {
            } else if (next instanceof SystemEnvironmentPropertySource) {
            } else if (next instanceof RandomValuePropertySource) {
            } else if (next instanceof PropertiesPropertySource) {
            }
        }
    }

}
