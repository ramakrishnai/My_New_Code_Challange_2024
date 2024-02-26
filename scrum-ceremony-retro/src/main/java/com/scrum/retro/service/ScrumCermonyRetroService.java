package com.scrum.retro.service;

import com.scrum.retro.entity.Feedback;
import com.scrum.retro.entity.Retrospective;
import com.scrum.retro.exception.InputDataException;
import com.scrum.retro.exception.ResourceNotFoundException;
import com.scrum.retro.repository.ScrumCeremonyRetroRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScrumCermonyRetroService {

    @Autowired
    ScrumCeremonyRetroRepository retroRepository;

    public Retrospective createRetrospective(Retrospective retrospective) throws InputDataException {
        if(retrospective != null && retrospective.getParticipants() != null) {
            return retroRepository.save(retrospective);
        } else {
            log.info("Participants - can't empty");
            throw new InputDataException("No Participants found from the given Request Body ");
        }

    }

    public Optional<Retrospective> findRetrospective(Long id) {
        Optional<Retrospective> retrospective = retroRepository.findById(id);
        if(retrospective.isEmpty()) {
            log.info("ResourceNotFoundException - due to no records found for the given input");
            throw new ResourceNotFoundException("No records found for the given Retrospective Id : "+id);
        }
        return retrospective;
    }

    public Page<Retrospective> findAllRetros(Pageable pageable) {
        Page<Retrospective> retros = retroRepository.findAll(pageable);
        return retros;
    }

    public Retrospective updateRetrospective(Feedback feedbackItem, Long retroId) {

        Optional<Retrospective> retrospective = retroRepository.findById(retroId);
        if(retrospective.isEmpty()) {
            log.info("ResourceNotFoundException - due to no records found for the given input");
            throw new ResourceNotFoundException("No records found for the given Retrospective Id : "+retroId);
        }
        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedbackItem);
        Retrospective retroWithFeedback = retrospective.get();
        retroWithFeedback.setFeedback(feedbacks);
        return retroRepository.save(retroWithFeedback);
    }

    public Optional<Retrospective> findByDate(LocalDate date) {
        Optional<Retrospective> retrospective = retroRepository.findByDate(date);
        return retrospective;
    }
}
