package com.bilyoner.assignment.couponapi.service;

import com.bilyoner.assignment.couponapi.ServiceBase;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.exception.CouponApiException;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.repository.EventRepository;
import com.bilyoner.assignment.couponapi.util.PrepareClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ServiceBase
public class EventServiceTest extends PrepareClass {


    @InjectMocks
    EventService eventService;
    @Mock
    EventRepository eventRepository;


    @Test
    public void create_Event_Test_should_success() {
        EventDTO eventDTO = createEventDTO();
        EventEntity eventEntity = createEventEntity();
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);
        EventDTO response = eventService.createEvent(eventDTO);
        assert response != null;
    }


    @Test
    public void getEventById_Test_should_success() {
        EventDTO eventDTO = createEventDTO();
        EventEntity eventEntity = createEventEntity();
        when(eventRepository.findById(eventDTO.getId())).thenReturn(java.util.Optional.ofNullable(eventEntity));
        EventDTO response = eventService.getEventById(eventDTO.getId());
        assert response != null;
    }

    @Test
    public void getEventById_Test_should_Error_Event_Not_Found() {
        assertThrows(CouponApiException.class,
                () -> {
                    EventDTO eventDTO = createEventDTO();
                    eventService.getEventById(eventDTO.getId());
                });
    }
}
