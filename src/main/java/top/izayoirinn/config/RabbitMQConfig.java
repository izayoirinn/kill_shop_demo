package top.izayoirinn.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rinn Izayoi
 * @date 2021/7/21 12:31
 * 配置交换机,队列,绑定关系
 */
@Configuration
public class RabbitMQConfig {
    public static final String KILL_DIRECT_EXCHANGE = "kill_direct_exchange";
    public static final String KILL_DIRECT_QUEUE = "kill_direct_queue";
    public static final String KILL_DIRECT_ROUTING_KEY = "kill.direct.key";
    public static final String KILL_DELAYED_EXCHANGE = "kill_delayed_exchange";
    public static final String KILL_DELAYED_QUEUE = "kill_delayed_queue";
    public static final String KILL_DELAYED_ROUTING_KEY = "kill.delayed.routing.key";

    // 声明交换机
    @Bean(KILL_DIRECT_EXCHANGE)
    public DirectExchange directExchange() {
        return new DirectExchange(KILL_DIRECT_EXCHANGE, true, false, null);
    }

    // 创建队列
    @Bean(KILL_DIRECT_QUEUE)
    public Queue queue() {
        return QueueBuilder.durable(KILL_DIRECT_QUEUE).build();
    }

    // 声明绑定关系
    @Bean
    public Binding binding(@Qualifier(KILL_DIRECT_EXCHANGE) DirectExchange directExchange,
                           @Qualifier(KILL_DIRECT_QUEUE) Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with(KILL_DIRECT_ROUTING_KEY);
    }

    /**
     * 消息发送时转为json发送
     *
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    //自定义交换机 我们在这里定义的是一个延迟交换机
    @Bean(KILL_DELAYED_EXCHANGE)
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型
        args.put("x-delayed-type", "direct");
        return new CustomExchange(KILL_DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    // 定义队列
    @Bean(KILL_DELAYED_QUEUE)
    public Queue delayQueue() {
        return new Queue(KILL_DELAYED_QUEUE);
    }

    // 队列绑定交换机
    @Bean
    public Binding delayQueueBindingExchange() {
        return BindingBuilder.bind(delayQueue()).to(customExchange()).with(KILL_DELAYED_ROUTING_KEY).noargs();
    }
}
