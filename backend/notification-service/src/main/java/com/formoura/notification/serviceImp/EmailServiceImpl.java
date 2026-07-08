package com.formoura.notification.serviceImp;

import com.formoura.event.notification.InvoiceEvent;
import com.formoura.notification.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(
            String to,
            String name) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);

        message.setSubject(
                "Welcome to Formoura");

        message.setText(
                "Hi " + name +
                        ",\n\n" +
                        "Welcome to Formoura Enterprise E-Commerce Platform.\n\n" +
                        "Your account has been created successfully.\n\n" +
                        "Happy Shopping!\n\n" +
                        "Regards,\nFormoura Team");

        mailSender.send(message);

    }


    @Override
    public void sendInvoice(InvoiceEvent event) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(event.getEmail());

            helper.setSubject(
                    "Formoura - Order Invoice #" + event.getOrderId());

            helper.setText(
                    """
                    Dear Customer,

                    Thank you for shopping with Formoura.

                    Your payment has been received successfully.

                    Order Id : %d

                    Please find your invoice attached.

                    Regards,
                    Formoura Team
                    """.formatted(event.getOrderId()));

            FileSystemResource file =
                    new FileSystemResource(
                            new File(event.getInvoicePath()));

            helper.addAttachment(
                    file.getFilename(),
                    file);

            mailSender.send(message);

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to send invoice email", ex);

        }

    }

}