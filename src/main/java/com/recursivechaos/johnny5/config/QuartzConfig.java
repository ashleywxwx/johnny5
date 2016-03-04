/**
 * Created by Andrew Bell 11/27/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.config;

import com.recursivechaos.johnny5.properties.QuartzProperties;
import com.recursivechaos.johnny5.schedule.StandupJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Configures Quartz beans
 * https://github.com/davidkiss/spring-boot-quartz-demo/blob/master/src/main/java/com/kaviddiss/bootquartz/SchedulerConfig.java
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    QuartzProperties quartzProperties;

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(StandupJob.class);
        JobDataMap map = new JobDataMap();
        map.put("message", quartzProperties.greeting);
        factoryBean.setJobDataMap(map);
        return factoryBean;
    }

    @Bean(name = "standupJobTrigger")
    public CronTriggerFactoryBean standupJobTrigger(JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(quartzProperties.time);
        return factoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("standupJobTrigger") Trigger standupJobTrigger) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setTriggers(standupJobTrigger);
        // Job factory using Spring DI
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factoryBean.setJobFactory(jobFactory);
        return factoryBean;
    }

}
