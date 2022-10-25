package net.weather.mail.service;

public record SendMailRequest(String to, String subject, String content) {
}
