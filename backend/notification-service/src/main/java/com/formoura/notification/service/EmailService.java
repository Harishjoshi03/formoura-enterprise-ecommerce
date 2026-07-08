package com.formoura.notification.service;

import com.formoura.event.notification.InvoiceEvent;

public interface EmailService {

    void sendWelcomeEmail(
            String to,
            String name);

    void sendInvoice(InvoiceEvent event);

}