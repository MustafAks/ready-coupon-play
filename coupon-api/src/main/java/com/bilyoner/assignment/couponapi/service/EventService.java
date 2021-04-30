package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventDTO> getAllEvents() {
        return getEvents();
    }

    public List<EventDTO> getEvents() {
        /**
         * TODO : Implement get events
         */
        return null;
    }

    public EventDTO createEvent(EventDTO eventRequest) {
        final EventEntity createdEventEntity = eventRepository.save(EventEntity.builder()
                .name(eventRequest.getName())
                .mbs(eventRequest.getMbs())
                .type(eventRequest.getType())
                .eventDate(eventRequest.getEventDate())
                .build());

        final EventDTO response = EventDTO.mapToEventDTO(createdEventEntity);

        return response;
    }
}
