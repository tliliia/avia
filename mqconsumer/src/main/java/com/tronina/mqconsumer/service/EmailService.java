package com.tronina.avia.service.impl;

import com.tronina.avia.exception.MailServiceException;
import com.tronina.avia.model.dto.StatisticDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final static String EMAIL_SUBJECT = "Новый отчет по статистике продаж авибилетов.";

    private final JavaMailSender mailSender;
    private final ITemplateEngine thymeleafTemplateEngine;
    private final String fromEmail;

    private void sendMail(String htmlBody, String subject, String from, String to) {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setText(htmlBody, true);
            messageHelper.setTo(to);
            messageHelper.setFrom(from);
            messageHelper.setSubject(subject);
        };

        mailSender.send(preparator);
    }

    public boolean sendStatisticReport(CustomerDto customer, StatisticDto data)  throws MailServiceException {
        Context thymeleafContext = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("customer", customer);
        templateModel.put("statlist", data.getStatLines());
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail/stat", thymeleafContext);
        return sendHtmlMessage(htmlBody, EMAIL_SUBJECT + data.getId(), fromEmail,  customer.getEmail());
    }



}
