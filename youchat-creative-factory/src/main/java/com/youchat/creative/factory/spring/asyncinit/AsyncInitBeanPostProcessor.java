package com.youchat.creative.factory.spring.asyncinit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

/**
 * @author Lien6o
 * @date 2021/5/13 5:55 下午
 */
public class AsyncInitBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Object beanDefinition = getBeanDefinition(beanName);


        return null;
    }

    private Object getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
        // TODO: 2021/5/14
        if (beanDefinition instanceof AbstractBeanDefinition) {
            return !"singleton".equals(beanDefinition.getScope()) ? null : (AbstractBeanDefinition) beanDefinition;
        }
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }


}
