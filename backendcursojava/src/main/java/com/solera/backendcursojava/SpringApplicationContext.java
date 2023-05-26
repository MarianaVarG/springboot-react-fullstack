package com.solera.backendcursojava;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;

/*
 With this we could already access any bean from other classes
 */
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
