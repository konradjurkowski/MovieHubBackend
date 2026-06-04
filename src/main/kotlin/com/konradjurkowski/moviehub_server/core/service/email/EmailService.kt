package com.konradjurkowski.moviehub_server.core.service.email

import com.konradjurkowski.moviehub_server.core.model.EmailRequest
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

interface EmailService {
    fun send(request: EmailRequest)
}

@Service
class EmailServiceImpl(
    private val mailSender: JavaMailSender,
) : EmailService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Async
    override fun send(request: EmailRequest) {
        try {
            val message = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, false, StandardCharsets.UTF_8.name())
            helper.setTo(request.recipient)
            helper.setSubject(request.subject)
            helper.setText(request.content ?: "", true)
            mailSender.send(message)
        } catch (e: Exception) {
            logger.error("Failed to send email to ${request.recipient}", e)
        }
    }
}
