package com.yunki.service;

import com.yunki.entity.Bootcoin;
import com.yunki.events.Event;
import com.yunki.events.BootcoinCreatedEvent;
import com.yunki.repository.IBootcoinRepository;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BootcoinEventsService {


	@Autowired
	IBootcoinRepository iBootcoinRepository;

	@KafkaListener(
			topics = "${topic.bootcoin.name:bootcoins}",
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "grupo1")
	public void consumer(Event<?> event) {
		Bootcoin bootcoin = new Bootcoin();
		if (event.getClass().isAssignableFrom(BootcoinCreatedEvent.class)) {
			BootcoinCreatedEvent bootcoinCreatedEvent = (BootcoinCreatedEvent) event;
			log.info("Received Bootcoin created event .... with Id={}, data={}",
					bootcoinCreatedEvent.getId(),
					bootcoinCreatedEvent.getData().toString());

			bootcoin.setId(bootcoinCreatedEvent.getData().getId());
			bootcoin.setPurchase(bootcoinCreatedEvent.getData().getPurchase());
			bootcoin.setSale(bootcoinCreatedEvent.getData().getSale());

			log.info("New Bootcoin: {}", bootcoin);

		}
		iBootcoinRepository.save(bootcoin);
	}

}
