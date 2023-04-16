package com.meta.ale.service;

import com.meta.ale.domain.AnpDocDto;
import com.meta.ale.domain.EmpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    // 패스워드를 재발급 받을 때 쓰는 것으로 empDto에 새롭게 인코딩 되기 전 발급한 패스워드를 넘겨줘야함.
    public void sendToPEmail(EmpDto empDto, String subject) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true,"UTF-8");
            messageHelper.setSubject(subject);
            messageHelper.setTo(empDto.getPEmail());
            String body = setAddText("<메타넷> 임시 비밀번호 생성", "임시 비밀번호는 " + empDto.getUserDto().getPwd() + "입니다.");
            messageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 휴가 관련 메일을 발송해야함.(사내이메일 발송)
    public void sendToCEmail(EmpDto empDto, String subject, String text) {
        try {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setSubject(subject);
        messageHelper.setTo(empDto.getCEmail());
        javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setAddText(String title, String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body style='width:300px; height:300px;'>");
        sb.append("<meta http-equiv='Content-Type' content='text/html'; charset=euc-kr>");
        sb.append("<h1>" + title + "</h1><br>");
        sb.append("<h3>안녕하세요 메타넷 입니다.</h3><br>");
        sb.append("<div>" + text + "</div>");
        sb.append("<h4>감사합니다.</h4>");
        sb.append("<div>메타넷 디지털 : 인사팀</div>");
        sb.append("</body></html>");
        String str = sb.toString();
        return str;
    }



}
