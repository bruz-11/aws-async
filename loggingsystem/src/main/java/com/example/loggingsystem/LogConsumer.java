package com.example.loggingsystem;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {

    // Escucha la Cola Q1 (all_logs_queue)
    @RabbitListener(queues = RabbitMQConfig.ALL_LOGS_QUEUE)
    public void consumeQ1(String message) {
        System.out.println("[CONSUMER - Q1 (All Logs)] " + message);
    }

    // Escucha la Cola Q2 (errors_only_queue)
    @RabbitListener(queues = RabbitMQConfig.ERRORS_ONLY_QUEUE)
    public void consumeQ2(String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("[CONSUMER - Q2 (Errors Only)] ALERTA CRÍTICA: " + message);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
