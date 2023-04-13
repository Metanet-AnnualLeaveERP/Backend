package com.meta.ale.quartz;

import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.function.Function;

@Configuration
public class QuartzConfig implements WebMvcConfigurer {

    private Scheduler scheduler;
    private ApplicationContext applicationContext;

    public static String APPLICATION_NAME = "appContext";

    public QuartzConfig(Scheduler scheduler, ApplicationContext applicationContext) {
        this.scheduler = scheduler;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void schInint() throws SchedulerException {
        final Function<String, Trigger> trigger = (exp) -> TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(exp)).build();

        JobDataMap ctx = new JobDataMap();
        ctx.put(APPLICATION_NAME, applicationContext);
        // newJob  : 새로운 스케줄링 작업을 등록
        // setJobData(ctx) applicationContext에 등록된 빈들을 모두 JobData로 등록하여
        // Scheduler에서 사용할 수 있도록 해줌.
        JobDetail annualCreateNewYearJob = JobBuilder.newJob(AnnualCreateNewYearEmpOverOneYr.class)
                .setJobData(ctx)
                .build();
        JobDetail removeLeaveEmployee = JobBuilder.newJob(RemoveLeaveEmployee.class)
                .setJobData(ctx)
                .build();
        // 1시간 간격으로 스케쥴러 수행
        scheduler.scheduleJob(annualCreateNewYearJob, trigger.apply("0 0 0 1/1 * ? *"));
        scheduler.scheduleJob(removeLeaveEmployee,trigger.apply("0 0 0 1/1 * ? *"));

    }
}
