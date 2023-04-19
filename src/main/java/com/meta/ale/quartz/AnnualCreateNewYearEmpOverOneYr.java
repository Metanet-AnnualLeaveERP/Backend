package com.meta.ale.quartz;

import com.meta.ale.service.GrantedVcService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AnnualCreateNewYearEmpOverOneYr extends QuartzJobBean {

    private static GrantedVcService GrantedVcService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext appCtx = (ApplicationContext) context.getJobDetail().getJobDataMap().get("appContext");
        // Autowired를 사용할 수 없으므로 ApplicationContext를 Quartz의 Job으로 등록하여
        // 사용할 수 있도록 설정한 부분

        GrantedVcService = appCtx.getBean(GrantedVcService.class);
        try {
            if (GrantedVcService.insertAnnualByEmpOverOneYr()) {
                System.out.println("시스템 정상 동작");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("알 수 없는 문제로 인해 연차 생성이 실패하였습니다.");
        }
    }
}
