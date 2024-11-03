package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.Event;
import com.devneopavan.customer_invoice_manager.entity.UserEvent;
import com.devneopavan.customer_invoice_manager.dto.EventDTO;
import com.devneopavan.customer_invoice_manager.repository.EventRepository;
import com.devneopavan.customer_invoice_manager.repository.UserEventRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import com.devneopavan.customer_invoice_manager.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserEventRepository userEventRepository;

    public EventService(final EventRepository eventRepository,
            final UserEventRepository userEventRepository) {
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("id"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long id) {
        return eventRepository.findById(id)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getId();
    }

    public void update(final Long id, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setType(event.getType());
        eventDTO.setDescription(event.getDescription());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setType(eventDTO.getType());
        event.setDescription(eventDTO.getDescription());
        return event;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UserEvent eventUserEvent = userEventRepository.findFirstByEvent(event);
        if (eventUserEvent != null) {
            referencedWarning.setKey("event.userEvent.event.referenced");
            referencedWarning.addParam(eventUserEvent.getId());
            return referencedWarning;
        }
        return null;
    }

}
