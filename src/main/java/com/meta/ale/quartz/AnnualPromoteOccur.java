package com.meta.ale.quartz;

import com.meta.ale.service.AnpDocService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AnnualPromoteOccur extends QuartzJobBean {
    private static AnpDocService anpDocService;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext appCtx = (ApplicationContext) context.getJobDetail().getJobDataMap().get("appContext");
    }
}
