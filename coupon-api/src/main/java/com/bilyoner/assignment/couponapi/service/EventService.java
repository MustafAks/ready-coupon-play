package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.exception.ErrorCodeEnum;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ModelMapper mapper;

    public Page<EventDTO> getAllEvents(int pageIndex, int pageSize) {
        log.info("******** Incoming User getAllEvents Start ********");
        log.info("getAllEvents {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));
        Page<EventDTO> response = eventRepository.findAll(PageRequest.of(pageIndex, pageSize))
                .map(eventEntity -> mapper.map(eventEntity, EventDTO.class));
        log.info("******** Incoming User getAllEvents End ********");

        return response;
    }


    public EventDTO createEvent(EventDTO eventRequest) {
        log.info("******** Incoming User createEvent Start ********");
        log.info("createEvent {}", kv("eventRequest", eventRequest));
        final EventEntity createdEventEntity = eventRepository.save(EventEntity.builder()
                .name(eventRequest.getName())
                .mbs(eventRequest.getMbs())
                .type(eventRequest.getType())
                .eventDate(eventRequest.getEventDate())
                .build());

        final EventDTO response = EventDTO.mapToEventDTO(createdEventEntity);
        log.info("******** Incoming User createEvent End ********");
        return response;
    }

    public List<EventEntity> getEventsByIds(List<Long> eventIds) {
        log.info("******** Incoming User getEventsByIds Start ********");
        log.info("getEventEntitiesByIds {}", kv("eventIds", eventIds));
        List<EventEntity> response = eventRepository.findByIdIn(eventIds);
        log.info("******** Incoming User getEventsByIds End ********");
        return response;
    }

    public EventDTO getEventById(Long id) {
        log.info("******** Incoming User getEventById Start ********");
        Optional<EventEntity> dbEventEntity = eventRepository.findById(id);
        if (dbEventEntity.isPresent()) {
            EventDTO response = EventDTO.mapToEventDTO(dbEventEntity.get());
            log.info("******** Incoming User getEventById End ********");
            return response;
        } else {
            throw new CouponApiException(ErrorCodeEnum.EVENT_NOT_FOUND);
        }
    }

    public void deleteEvent(Long id) {
        log.info("******** Incoming User deleteEvent Start ********");
        log.info("deleteEvent-begin {}", kv("id", id));
        eventRepository.deleteById(id);
        log.info("******** Incoming User deleteEvent End ********");
    }
}
