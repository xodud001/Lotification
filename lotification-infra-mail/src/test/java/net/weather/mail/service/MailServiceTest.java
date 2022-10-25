package net.weather.mail.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MailServiceTest {

    @Autowired
    MailService mailService;

    @DisplayName("1. send mail")
    @Test
    void test1(){
        SendMailRequest request = new SendMailRequest("dud708@naver.com", "[Lotification]", "소환사 롤로노아 김동영님이 8분 17초전 게임을 시작하였습니다.");
        mailService.mailSend(request);
    }
}