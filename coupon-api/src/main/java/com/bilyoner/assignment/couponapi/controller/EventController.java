package com.bilyoner.assignment.couponapi.controller;

import com.bilyoner.assignment.couponapi.model.EventCreateRequest;
import com.bilyoner.assignment.couponapi.model.EventDTO;
import com.bilyoner.assignment.couponapi.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {


    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAllEvents(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return ResponseEntity.ok(eventService.getAllEvents(pageIndex, pageSize));
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO  eventRequest) {
        return ResponseEntity.ok(eventService.createEvent(eventRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

}
