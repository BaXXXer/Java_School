package edu.tsystems.javaschool.logapp.api.producer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MessageProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger LOG = Logger.getLogger(MessageProducer.class);

    @Value(value = "${test.topic.name}")
    private String topicName;

    @Autowired
    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){

        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOG.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                LOG.info("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }

}
