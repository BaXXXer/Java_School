package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.dto.TruckStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.TruckDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.exception.DuplicateEntityException;
import edu.tsystems.javaschool.logapp.api.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TruckService {

    @Autowired
    private MessageProducer messageProducer;
    private final TruckDao truckDao;
    private final TruckDtoConverter truckConverter;
    private static final Logger LOG = Logger.getLogger(TruckService.class);
    private static final String MESSAGE = "trucks changed";

    @Transactional
    public void saveTruck(TruckDTO truckDTO) {
        List<TruckDTO> allTrucks = getAllTrucks();
        for (TruckDTO t : allTrucks) {
            if (t.getRegNumber().equals(truckDTO.getRegNumber())) {
                LOG.error("Truck with number " + truckDTO.getRegNumber() + " already exists in DB!");
                throw new DuplicateEntityException();
            }
        }
        Truck entity = truckConverter.convertToEntity(truckDTO);
        truckDao.saveTruck(entity);
        messageProducer.sendMessage(MESSAGE);
    }


    @Transactional
    public List<TruckDTO> getAllTrucks() {
        List<TruckDTO> dtos = new ArrayList();

        for (Truck t : truckDao.getAllTrucks()) {
            dtos.add(truckConverter.convertToDto(t));
        }
        return dtos;

    }

    @Transactional
    public void updateTruck(TruckDTO truck) {
        truckDao.updateTruck(truckConverter.convertToEntity(truck));
        messageProducer.sendMessage(MESSAGE);
    }

    @Transactional
    public void removeTruck(int id) {
        truckDao.removeTruck(id);
        messageProducer.sendMessage(MESSAGE);
    }

    @Transactional
    public TruckDTO getTruckById(int id) {
        Truck entity = truckDao.getTruckById(id);
        return truckConverter.convertToDto(entity);
    }

    public TruckStatusDTO getTruckStatus() {
        TruckStatusDTO status = new TruckStatusDTO();
        status.setTotalTrucksNumber(truckDao.getAllTrucksNumber());
        status.setTotalBrokenNumber(truckDao.getBrokenTrucksNumber());
        long restTucks = truckDao.getAllTrucksNumber() - truckDao.getTrucksOnOrderNumber();
        status.setTotalRestNumber(restTucks);
        return status;
    }

    @Transactional
    public Map<Integer, String> getTruckMap() {
        Map<Integer, String> map = new HashMap<>();
        for (TruckDTO t : getAllTrucks()) {
            map.put(t.getId(), t.getRegNumber());
        }
        return map;
    }

    public List<TruckDTO> getReadyToGoTrucks() {
        List<TruckDTO> readyDtos = new ArrayList<>();
        for (Truck t : truckDao.getReadyToGoTrucks()) {
            readyDtos.add(truckConverter.convertToDto(t));
        }
        return readyDtos;
    }

    @Transactional
    public int getLastAddedTruckIndex() {
        int index = getAllTrucks().size() - 1;
        return getAllTrucks().get(index).getId();
    }


}
