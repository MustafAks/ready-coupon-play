package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.CouponApiApplication;
import com.bilyoner.assignment.couponapi.entity.EventEntity;
import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.service.EventService;
import com.bilyoner.assignment.couponapi.util.PrepareClass;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CouponApiApplication.class)
public class EventControllerTest extends PrepareClass {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Mock
    ModelMapper mapper;


    @Test
    public void create_event_shouldBeSuccess() {
        EventDTO eventDTO = createEventDTO();
        EventEntity eventEntity = createEventEntity();
        when(mapper.map(eventEntity, EventDTO.class)).thenReturn(eventDTO);
        doReturn(eventDTO).when(eventService).createEvent(eventDTO);
        ResponseEntity<EventDTO> response = eventController.createEvent(eventDTO);
        assert response != null;
    }
}
