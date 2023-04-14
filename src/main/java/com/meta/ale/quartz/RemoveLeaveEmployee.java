package com.meta.ale.quartz;

import com.meta.ale.service.EmpService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

// 사원의 leaveDate != null 면서 오늘 일자로부터 2년이 지난
// 데이터의 삭제 스케줄러
public class RemoveLeaveEmployee extends QuartzJobBean {

    private static EmpService empService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext appCtx = (ApplicationContext) context.getJobDetail().getJobDataMap().get("appContext");
        empService = appCtx.getBean(EmpService.class);
        try {
            empService.deleteEmpOverTwoYrLeaveDate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
