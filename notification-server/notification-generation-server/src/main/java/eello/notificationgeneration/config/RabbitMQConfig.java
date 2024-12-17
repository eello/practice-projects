package eello.notificationgeneration.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.exchange}")
    public String genExchangeName;

    @Value("${spring.rabbitmq.queue}")
    public String genQueueName;

    @Value("${spring.rabbitmq.routing-key}")
    public String routingKey;

    @Value("${spring.rabbitmq.dlx}")
    public String dlxName;

    @Value("${spring.rabbitmq.dlq}")
    public String dlqName;

    @Value("${spring.rabbitmq.dlr}")
    public String dlr; // dead-letter-routing key

    @Bean
    public Queue genQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", dlxName);
        arguments.put("x-dead-letter-routing-key", dlr);
        return new Queue(genQueueName, true, false, false, arguments);
    }

    @Bean
    public DirectExchange genExchange() {
        return new DirectExchange(genExchangeName);
    }

    @Bean
    public Binding binding(Queue genQueue, DirectExchange genExchange) {
        return BindingBuilder.bind(genQueue).to(genExchange).with(routingKey);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(dlqName);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(dlxName);
    }

    @Bean
    public Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(dlr);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(genExchangeName);
        rabbitTemplate.setRoutingKey(routingKey);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
            ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 재시도 횟수 정책
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);
        retryTemplate.setRetryPolicy(retryPolicy);

        // 재시도 간격 정책 [지수 백오프]
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 초기 간격 1초
        backOffPolicy.setMultiplier(0.5);      // 간격 배수 (0.5배씩 증가)
        backOffPolicy.setMaxInterval(10000);   // 최대 간격 10초
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
