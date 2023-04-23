package com.meta.ale.service;

import com.meta.ale.domain.EmpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    // 패스워드를 재발급 받을 때 쓰는 것으로 empDto에 새롭게 인코딩 되기 전 발급한 패스워드를 넘겨줘야함.
    public void sendToPEmail(EmpDto empDto, String subject,String title,String text) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject(subject);
            messageHelper.setTo(empDto.getPEmail());
            String body = setAddText(title,text);
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 휴가 관련 메일을 발송해야함.(사내이메일 발송)
    public void sendToCEmail(EmpDto empDto, String subject,String title ,String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setSubject(subject);
            messageHelper.setTo(empDto.getCEmail());
            String body = setAddText(title,text);
            messageHelper.setText(body,true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setAddText(String title, String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body style='width:800px; height:500px;'>");
        sb.append("<meta http-equiv='Content-Type' content='text/html'; charset=euc-kr>");
        sb.append("<h1>" + title + "</h1><br>");
        sb.append("<h3>안녕하세요 메타넷 입니다.</h3><br>");
        sb.append("<div'>" + text + "</div>");
        sb.append("<h4>감사합니다.</h4>");
        sb.append("<div>메타넷 디지털 : 인사팀</div>");
        sb.append("</body></html>");
        String str = sb.toString();
        return str;
    }


}
