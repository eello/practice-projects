package eello.notificationgeneration.config;

import eello.notificationgeneration.service.NotificationGenerationWorker;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange}")
    public String exchange;

    @Value("${spring.rabbitmq.queue}")
    public String queue;

    @Value("${spring.rabbitmq.routing-key}")
    public String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationGenerationWorker worker, MessageConverter messageConverter) {
        MessageListenerAdapter messageListenerAdapter =
                new MessageListenerAdapter(worker, NotificationGenerationWorker.LISTENER_METHOD_NAME);
        messageListenerAdapter.setMessageConverter(messageConverter);
        return messageListenerAdapter;
    }
}
