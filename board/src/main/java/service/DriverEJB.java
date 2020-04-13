package service;


import config.BoardKafkaConsumer;
import converter.FromJsonToDtoConverter;
import dto.DriverDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Remote
@Stateless
public class DriverEJB {

    @Inject
    BoardKafkaConsumer consumer;

    public List<DriverDTO> getSavedDriver(){
        List<DriverDTO> driverList = new ArrayList();
        ConsumerRecords<Long, String> consumerRecords = consumer.runConsumerAndGetMsg();
        for (ConsumerRecord<Long, String> record: consumerRecords) {
            String savedNewDriverJSON = record.value();
            try {
                driverList.add(FromJsonToDtoConverter.convertToDriverDto(savedNewDriverJSON));
            } catch (IOException e) {
                throw new RuntimeException("Converting from JSON to POJO troubles");
            }
        }
        return driverList;

    }




}
