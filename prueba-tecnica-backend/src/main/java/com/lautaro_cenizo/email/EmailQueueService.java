package com.lautaro_cenizo.email;

import com.lautaro_cenizo.service.NotificacionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
//Servicio de cola de mensajes
public class EmailQueueService {
    private final BlockingQueue<EmailMessage> emailQueue;
    private final NotificacionServicio notificacionServicio;
    private volatile boolean isRunning;

    public EmailQueueService(@Lazy  NotificacionServicio notificacionServicio) {
        this.emailQueue = new LinkedBlockingQueue<>();
        this.notificacionServicio = notificacionServicio;
        this.isRunning = true;
        startProcessing();
    }

    public void queueEmail(EmailMessage message) {
        try {
            emailQueue.put(message);
            log.info("Email queued successfully for: {}", message.getTo());
        } catch (InterruptedException e) {
            log.error("Error queueing email", e);
            Thread.currentThread().interrupt();
        }
    }

    private void startProcessing() {
        Thread processingThread = new Thread(() -> {
            while (isRunning) {
                try {
                    EmailMessage message = emailQueue.take();
                    notificacionServicio.enviarNotificacionEmail(message.getNotificacion());
                    log.info("Email processed successfully for: {}", message.getTo());
                } catch (InterruptedException e) {
                    log.error("Error processing email from queue", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        processingThread.setDaemon(true);
        processingThread.start();
    }

    public void shutdown() {
        isRunning = false;
    }
}

