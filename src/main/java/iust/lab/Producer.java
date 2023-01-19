package iust.lab;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 */
public class Producer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        KafkaProducer<Integer, Integer> kafkaProducer = new KafkaProducer<>(props);
        ProducerRecord<Integer, Integer> record = new ProducerRecord<>("test",
                1400);
        RecordMetadata metadata = kafkaProducer.send(record).get();
        System.out.println(metadata.topic());
    }
}
