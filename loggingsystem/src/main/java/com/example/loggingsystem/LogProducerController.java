package com.example.loggingsystem;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogProducerController {

    private final RabbitTemplate rabbitTemplate;

    public LogProducerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public record LogPayload(String level, String message) {}

    @PostMapping
    public String produceLog(@RequestBody LogPayload payload) {
        String routingKey = payload.level().toUpperCase();

        // Envía el mensaje al Exchange especificando la Routing Key (INFO, WARNING, ERROR)
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            routingKey, 
            payload.message()
        );

        return "Mensaje enviado con Routing Key: " + routingKey;
    }
}
