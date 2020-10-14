package com.boss.cloud.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author: lpb
 * @create: 2020-09-24 15:25
 */
public class Demo {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(PrintJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build();
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("-------------scheduler start ---------------");
        scheduler.start();
        TimeUnit.SECONDS.sleep(30);
        scheduler.shutdown();
        System.out.println("-------------scheduler shutdown  ---------------");

    }
}
