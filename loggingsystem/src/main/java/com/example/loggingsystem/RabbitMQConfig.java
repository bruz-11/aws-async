package com.example.loggingsystem;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "logs_direct_exchange";
    
    // Q1: Cola de Monitoreo General
    public static final String ALL_LOGS_QUEUE = "all_logs_queue"; 
    
    // Q2: Cola de Alertas Críticas
    public static final String ERRORS_ONLY_QUEUE = "errors_only_queue"; 

    // 1. Declarar el Exchange (Enrutador)
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 2. Declarar las Queues (Q1 y Q2)
    @Bean
    public Queue allLogsQueue() {
        return new Queue(ALL_LOGS_QUEUE, true);
    }

    @Bean
    public Queue errorsOnlyQueue() {
        return new Queue(ERRORS_ONLY_QUEUE, true);
    }

    // 3. Declarar Bindings (Reglas de Enrutamiento)
    // Q1 recibe INFO, WARNING y ERROR
    @Bean
    public Binding bindAllLogsInfo(DirectExchange exchange, Queue allLogsQueue) {
        return BindingBuilder.bind(allLogsQueue).to(exchange).with("INFO");
    }

    @Bean
    public Binding bindAllLogsWarning(DirectExchange exchange, Queue allLogsQueue) {
        return BindingBuilder.bind(allLogsQueue).to(exchange).with("WARNING");
    }

    @Bean
    public Binding bindAllLogsError(DirectExchange exchange, Queue allLogsQueue) {
        return BindingBuilder.bind(allLogsQueue).to(exchange).with("ERROR");
    }

    // Q2 recibe ÚNICAMENTE mensajes con Routing Key "ERROR"
    @Bean
    public Binding bindErrorsOnly(DirectExchange exchange, Queue errorsOnlyQueue) {
        return BindingBuilder.bind(errorsOnlyQueue).to(exchange).with("ERROR");
    }
}

