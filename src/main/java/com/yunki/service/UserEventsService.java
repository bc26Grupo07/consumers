package com.yunki.service;

import com.yunki.events.UserCreatedEvent;
import com.yunki.events.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventsService {

	@KafkaListener(
			topics = "${topic.user.name:users}",
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "grupo1")
	public void consumer(Event<?> event) {
		if (event.getClass().isAssignableFrom(UserCreatedEvent.class)) {
			UserCreatedEvent userCreatedEvent = (UserCreatedEvent) event;
			log.info("Received User created event .... with Id={}, data={}",
					userCreatedEvent.getId(),
					userCreatedEvent.getData().toString());
		}
	}

}
