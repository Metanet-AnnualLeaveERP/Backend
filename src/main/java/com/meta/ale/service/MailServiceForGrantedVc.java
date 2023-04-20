package com.meta.ale.service;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.EmpDto;
import com.meta.ale.domain.GrantedVcDto;
import com.meta.ale.mapper.EmpMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class MailServiceForGrantedVc {

    private final JavaMailSender javaMailSender;
    private final EmpMapper empMapper;

    public void sendGrantedVacationToCompanyEmail(GrantedVcDto grantedVcdto, String subject){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 수신자 지정
            messageHelper.setTo(grantedVcdto.getEmpDto().getCEmail());
            // 메일 제목 지정
            messageHelper.setSubject(subject);
            // 메일 내용 지정
            String body = setMail("<메타넷> 휴가 생성 알림",
                    grantedVcdto.getEmpDto().getName()+"님의 휴가가 생성되었습니다.<br>" +
                            "휴가유형 : "+grantedVcdto.getVcTypeDto().getTypeName()+"<br>"+
                            "생성휴가일수 : "+grantedVcdto.getVcDays()+"<br>");
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendAnpDocToCompanyEmail(AnpDocDto anpDocDto, String subject){
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 메일 제목 지정
            messageHelper.setSubject(subject);
            // 메일 내용 지정
            EmpDto empDto = empMapper.selectEmpByEmpId(anpDocDto.getEmpDto().getEmpId());
            anpDocDto.setEmpDto(empDto);
            String eName = anpDocDto.getEmpDto().getName();

            Date nowDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            String formattedNowDate = formatter.format(nowDate);
            // 10일후
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date afterTenDays = calendar.getTime();
            String formattedAfterTenDays = formatter.format(afterTenDays);

            int remainDays = anpDocDto.getRemainAnv();
            String body = setMail("<메타넷>미사용 연차휴가일수 통지 및 \n휴가사용시기 지정 요청 안내",
                    eName+"사원의 "+formattedNowDate +" 현재 사용하지 않은 연차휴가일수가 " +
                            remainDays+"일임을 알려드립니다. " +
                            formattedAfterTenDays+"까지 미사용 연차휴가의 사용시기를 지정해 주시기 바랍니다.");
            messageHelper.setText(body, true);
            // 수신자 지정
            messageHelper.setTo(anpDocDto.getEmpDto().getCEmail());
            javaMailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String setMail(String title, String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body style='width:600px; height:600px;'>");
        sb.append("<meta http-equiv='Content-Type' content='text/html'; charset=euc-kr>");
        sb.append("<h1>" + title + "</h1><br>");
        sb.append("<h3>안녕하세요 메타넷 입니다.</h3><br>");
        sb.append("<div>" + text + "</div>");
        sb.append("<div>감사합니다.</div>");
        sb.append("<div>메타넷 디지털 인사팀</div>");
        sb.append("</body></html>");
        String str = sb.toString();
        return str;
    }
//
//    public HashMap<String, String> AnpText(AnpDocDto anpDocDto){
//        HashMap<String, String> map = new HashMap<>();
//
//        String eName = anpDocDto.getEmpDto().getName();
//
//        Date nowDate = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
//        String formattedNowDate = formatter.format(nowDate);
//        // 10일후
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(nowDate);
//        calendar.add(Calendar.DAY_OF_MONTH, 10);
//        Date afterTenDays = calendar.getTime();
//        String formattedAfterTenDays = formatter.format(afterTenDays);
//
//        int remainDays = anpDocDto.getRemainAnv();
//
//        map.put("title", "<메타넷>미사용 연차휴가일수 통지 및 \n휴가사용시기 지정 요청 안내");
//        String text = eName+"사원의 "+formattedNowDate +" 현재 사용하지 않은 연차휴가일수가 " +
//                remainDays+"일임을 알려드립니다. \n" +
//                formattedAfterTenDays+"까지 미사용 연차휴가의 사용시기를 지정해 주시기 바랍니다.";
//
//        map.put("text", text);
//
//        return map;
//    }

}
