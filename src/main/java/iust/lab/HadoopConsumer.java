package iust.lab;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Progressable;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Properties;

public class HadoopConsumer {

    static public void main(String... str) throws IOException, URISyntaxException {
        URI uri = new URI("hdfs://localhost:9000");
        Configuration configuration = new Configuration();
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        try (FileSystem hdfs = FileSystem.get(uri, configuration)) {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "localhost:9092");
            props.setProperty("group.id", "group1");
            props.setProperty("enable.auto.commit", "true");
            props.setProperty("auto.commit.interval.ms", "1000");
            props.setProperty("key.deserializer", IntegerDeserializer.class.getName());
            props.setProperty("value.deserializer", IntegerDeserializer.class.getName());
            KafkaConsumer<Integer, Integer> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList("test"));
            Path file = new Path("hdfs://localhost:9000/test");
            if (hdfs.exists(file)) {
                hdfs.delete(file, true);
            }
            FSDataOutputStream os = hdfs.create(file,
                    new Progressable() {
                        public void progress() {
                            System.out.println("...bytes written ]");
                        }
                    });
            try (BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 50000) {
                    ConsumerRecords<Integer, Integer> records = consumer.poll(1000);
                    for (ConsumerRecord<Integer, Integer> record : records) {
                        System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());


                        br.write(record.value().toString());
                    }
                }
            }
        }
    }

}
