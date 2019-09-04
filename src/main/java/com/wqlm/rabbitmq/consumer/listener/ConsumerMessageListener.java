package com.wqlm.rabbitmq.consumer.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消费消息
 *
 * @author wqlm
 * @date 2019/8/25 10:34
 */
@Component
public class ConsumerMessageListener {
    /**
     * 监听指定队列
     *
     * @param message 消息体
     * @param headers 消息头
     * @param channel 通道
     * @return
     * @RabbitListener 指定了 exchange 、key、Queue 后，如果 Rabbitmq 没有会去创建
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "exchangeName", type = "direct", durable = "true", autoDelete = "false", internal = "false"),
            key = "routingKeyValue",
            value = @Queue(value = "queryName", durable = "true", exclusive = "false", autoDelete = "false")
    ))
    public void listenerMessage(String message, @Headers Map<String, Object> headers, Channel channel)
            throws IOException {
        System.out.println(message);
        System.out.println(headers);
        //手动 ack
        channel.basicAck((Long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
    }
}
