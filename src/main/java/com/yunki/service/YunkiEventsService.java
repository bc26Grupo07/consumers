package com.yunki.service;

import com.yunki.entity.Bootcoin;
import com.yunki.entity.Yunki;
import com.yunki.events.YunkiCreatedEvent;
import com.yunki.events.Event;
import com.yunki.repository.IBootcoinRepository;
import com.yunki.repository.IYunkiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class YunkiEventsService {

	@Autowired
	IYunkiRepository iYunkiRepository;

	@Autowired
	IBootcoinRepository iBootcoinRepository;

	@KafkaListener(
			topics = "${topic.yunki.name:yunkis}",
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "transacciones")
	public void consumer(Event<?> event) {
			Yunki yunki = new Yunki();
		if (event.getClass().isAssignableFrom(YunkiCreatedEvent.class)) {
			YunkiCreatedEvent yunkiCreatedEvent = (YunkiCreatedEvent) event;

			yunki.setId(yunkiCreatedEvent.getData().getId());
			yunki.setUserId(yunkiCreatedEvent.getData().getUserId());
			yunki.setSecondUserId(yunkiCreatedEvent.getData().getSecondUserId());
			yunki.setPayMode(yunkiCreatedEvent.getData().getPayMode());
			yunki.setAmount(yunkiCreatedEvent.getData().getAmount());
			yunki.setAmountBootcoin(yunkiCreatedEvent.getData().getAmountBootcoin());
			yunki.setPhone(yunkiCreatedEvent.getData().getPhone());
			yunki.setEmail(yunkiCreatedEvent.getData().getEmail());

			log.info("Received Yunki created event .... with Id={}, data={}",
					yunkiCreatedEvent.getId(),
					yunkiCreatedEvent.getData().toString());

			log.info("El usuario {} a aceptado el intercambio",yunkiCreatedEvent.getData().getSecondUserId().toString());

			String number = UUID.randomUUID().toString();
			yunki.setTransaction(number);
			log.info("Se generó la siguiente transaccion: {}",number);


			if (yunki.getPayMode().equals("yanki") || yunki.getPayMode().equals("transferencia")){
				Optional<Bootcoin> moneda = iBootcoinRepository.findById(1);

				double sale = yunki.getAmountBootcoin()*moneda.get().getSale();
				double amount = yunki.getAmount();

				if ( Double.compare(sale,amount) == 0){
					//iYunkiRepository.insert(yunki);
					log.info("Compra Exitosa");
				} else {
					log.info("Monto incorrecto");
				}

			} else {
				log.info("Error: Debe indicar Yanki o Transferencia");
			}
		}


	}

}
