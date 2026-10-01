package com.example.loggingsystem;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend en Vite/React
public class LogProducerController {

    private final RabbitTemplate rabbitTemplate;

    public LogProducerController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Estructura del cuerpo de la petición JSON
    public record LogRequest(String level, String message) {}

    @PostMapping
    public String sendLog(@RequestBody LogRequest request) {
        String routingKey = request.level().toUpperCase();
        
        // Publica el mensaje en el Exchange usando la Routing Key correspondiente
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            routingKey, 
            request.message()
        );

        return "Log enviado con éxito. Nivel: " + routingKey;
    }
}