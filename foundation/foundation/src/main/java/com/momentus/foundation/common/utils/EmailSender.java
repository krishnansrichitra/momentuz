package com.momentus.foundation.common.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class EmailSender {

  public static void sendEmail(
      String smtpHost,
      int smtpPort,
      String username, // your GoDaddy email (e.g. you@yourdomain.com)
      String password, // mailbox password
      String to,
      String subjectTemplate,
      String bodyTemplateHtml,
      Map<String, String> variables) {
    // Replace {{key}} placeholders with values
    String subject = applyTemplate(subjectTemplate, variables);
    String bodyHtml = applyTemplate(bodyTemplateHtml, variables);

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true"); // TLS
    props.put("mail.smtp.host", smtpHost);
    props.put("mail.smtp.port", String.valueOf(smtpPort));
    props.put("mail.smtp.connectiontimeout", "10000");
    props.put("mail.smtp.timeout", "10000");

    Session session =
        Session.getInstance(
            props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
              }
            });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      message.setSubject(subject);

      // Send HTML (use setText(body) if you want plain text)
      message.setContent(bodyHtml, "text/html; charset=UTF-8");

      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Failed to send email via SMTP: " + e.getMessage(), e);
    }
  }

  private static String applyTemplate(String template, Map<String, String> vars) {
    String result = template;
    if (vars != null) {
      for (Map.Entry<String, String> e : vars.entrySet()) {
        String key = "{{" + e.getKey() + "}}";
        String val = e.getValue() == null ? "" : e.getValue();
        result = result.replace(key, val);
      }
    }
    return result;
  }

  // Example usage
  public static void main(String[] args) {
    String smtpHost = "smtpout.secureserver.net";
    int smtpPort = 587; // TLS (common). If you need SSL, use 465 with different props (see below).

    String username = "yourname@yourdomain.com";
    String password = "YOUR_EMAIL_PASSWORD";

    String to = "someone@example.com";

    String subjectTemplate = "Welcome, {{name}}!";
    String bodyTemplateHtml =
        """
                <h3>Hello {{name}},</h3>
                <p>Your OTP is: <b>{{otp}}</b></p>
                <p>Regards,<br/>Support Team</p>
                """;

    Map<String, String> vars =
        Map.of(
            "name", "Krishnan",
            "otp", "834912");

    sendEmail(smtpHost, smtpPort, username, password, to, subjectTemplate, bodyTemplateHtml, vars);
    System.out.println("Email sent.");
  }
}
