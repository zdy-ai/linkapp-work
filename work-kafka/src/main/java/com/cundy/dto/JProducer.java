package com.cundy.dto;

import java.util.Properties;

import com.cundy.vo.ConfigureAPI;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @Date May 22, 2015
 *
 * @Author dengjie
 *
 * @Note Kafka JProducer
 */
public class JProducer extends Thread {

    private Producer<Integer, String> producer;
    private String topic;
    private Properties props = new Properties();
    private final int SLEEP = 1000 * 3;

    public JProducer(String topic) {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", ConfigureAPI.KafkaProperties.BROKER_LIST);
        producer = new Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
    }

    @Override
    public void run() {
        int offsetNo = 1;
        while (true) {
            String msg = new String("my_test_Message_" + offsetNo);
            System.out.println("Send->[" + msg + "]");
            producer.send(new KeyedMessage<Integer, String>(topic, msg));
            offsetNo++;
            try {
                sleep(SLEEP);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}