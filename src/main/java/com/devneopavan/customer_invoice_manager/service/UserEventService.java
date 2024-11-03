package com.devneopavan.customer_invoice_manager.service;

import com.devneopavan.customer_invoice_manager.entity.Event;
import com.devneopavan.customer_invoice_manager.entity.User;
import com.devneopavan.customer_invoice_manager.entity.UserEvent;
import com.devneopavan.customer_invoice_manager.dto.UserEventDTO;
import com.devneopavan.customer_invoice_manager.repository.EventRepository;
import com.devneopavan.customer_invoice_manager.repository.UserEventRepository;
import com.devneopavan.customer_invoice_manager.repository.UserRepository;
import com.devneopavan.customer_invoice_manager.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserEventService(final UserEventRepository userEventRepository,
            final UserRepository userRepository, final EventRepository eventRepository) {
        this.userEventRepository = userEventRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<UserEventDTO> findAll() {
        final List<UserEvent> userEvents = userEventRepository.findAll(Sort.by("id"));
        return userEvents.stream()
                .map(userEvent -> mapToDTO(userEvent, new UserEventDTO()))
                .toList();
    }

    public UserEventDTO get(final Long id) {
        return userEventRepository.findById(id)
                .map(userEvent -> mapToDTO(userEvent, new UserEventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserEventDTO userEventDTO) {
        final UserEvent userEvent = new UserEvent();
        mapToEntity(userEventDTO, userEvent);
        return userEventRepository.save(userEvent).getId();
    }

    public void update(final Long id, final UserEventDTO userEventDTO) {
        final UserEvent userEvent = userEventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userEventDTO, userEvent);
        userEventRepository.save(userEvent);
    }

    public void delete(final Long id) {
        userEventRepository.deleteById(id);
    }

    private UserEventDTO mapToDTO(final UserEvent userEvent, final UserEventDTO userEventDTO) {
        userEventDTO.setId(userEvent.getId());
        userEventDTO.setDevice(userEvent.getDevice());
        userEventDTO.setIpAddress(userEvent.getIpAddress());
        userEventDTO.setCreatedAt(userEvent.getCreatedAt());
        userEventDTO.setUser(userEvent.getUser() == null ? null : userEvent.getUser().getId());
        userEventDTO.setEvent(userEvent.getEvent() == null ? null : userEvent.getEvent().getId());
        return userEventDTO;
    }

    private UserEvent mapToEntity(final UserEventDTO userEventDTO, final UserEvent userEvent) {
        userEvent.setDevice(userEventDTO.getDevice());
        userEvent.setIpAddress(userEventDTO.getIpAddress());
        userEvent.setCreatedAt(userEventDTO.getCreatedAt());
        final User user = userEventDTO.getUser() == null ? null : userRepository.findById(userEventDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        userEvent.setUser(user);
        final Event event = userEventDTO.getEvent() == null ? null : eventRepository.findById(userEventDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        userEvent.setEvent(event);
        return userEvent;
    }

}
