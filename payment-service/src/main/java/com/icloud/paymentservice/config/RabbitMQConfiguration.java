package com.icloud.paymentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    static final String paymentSucceedExchangeName = "payment.succeed";
    static final String paymentFailedExchangeName = "payment.failed";


    @Bean
    Queue paymentSucceedQueue() {
        return new Queue(paymentSucceedExchangeName, false);
    }

    @Bean
    TopicExchange paymentSucceedExchange() {
        return new TopicExchange(paymentSucceedExchangeName);
    }

    @Bean
    Queue paymentFailedQueue() {
        return new Queue(paymentFailedExchangeName, false);
    }

    @Bean
    TopicExchange paymentFailedExchange() {
        return new TopicExchange(paymentFailedExchangeName);
    }

    @Bean
    Binding paymentSucceedBinding(Queue paymentSucceedQueue,
                                  TopicExchange paymentSucceedExchange) {
        return BindingBuilder.bind(paymentSucceedQueue)
                .to(paymentSucceedExchange).with("payment.succeed");
    }

    @Bean
    Binding paymentFailedBinding(Queue paymentFailedQueue,
                                 TopicExchange paymentFailedExchange) {
        return BindingBuilder.bind(paymentFailedQueue)
                .to(paymentFailedExchange).with("payment.succeed");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
