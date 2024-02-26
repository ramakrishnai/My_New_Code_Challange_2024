package com.scrum.retro.controller;

import com.scrum.retro.entity.Feedback;
import com.scrum.retro.exception.InputDataException;
import com.scrum.retro.exception.ResourceNotFoundException;
import com.scrum.retro.service.ScrumCermonyRetroService;
import com.scrum.retro.entity.Retrospective;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/retros")
@Slf4j
public class ScrumCermonyRetroController {
    @Autowired
    ScrumCermonyRetroService service;

    @PostMapping("")
    public ResponseEntity<Retrospective> saveRetrospective(@RequestBody @Valid Retrospective retro) throws InputDataException {
        log.info("saveRetrospective");
        Retrospective retrospective = service.createRetrospective(retro);
        return ResponseEntity.created(URI.create("/v1/api/retros")).body(retrospective);
    }

    @PutMapping("/feedbacks/{retroId}")
    public ResponseEntity<Retrospective> saveRetrospectiveFeedback(@PathVariable Long retroId, @RequestBody Feedback feedback) {
        log.info("saveRetrospectiveFeedback ");
        Retrospective feedbackResponse = service.updateRetrospective(feedback,retroId);

        return ResponseEntity.created(URI.create("/v1/api/retros/feedbacks/"+retroId)).body(feedbackResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retrospective> findRetrospective(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("findRetrospective by id");
        Optional<Retrospective> retrospective = service.findRetrospective(id);
        return ResponseEntity.ok().body(retrospective.get());
    }


    @GetMapping("")
    public ResponseEntity<List<Retrospective>> findAllRetros(
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "2") int pageSize) {

        log.info("findAllRetros by pagination currentPage: "+currentPage+" and  pageSize"+pageSize);
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        Page<Retrospective> retrospective = service.findAllRetros(pageRequest);
        return ResponseEntity.ok().body(retrospective.get().toList());
    }

    @GetMapping("/date/{date}") //format: /YYYY-mm-DD
    public ResponseEntity<Retrospective> findByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("findRetrospective by date: "+date);
        Optional<Retrospective> retrospective = service.findByDate(date);
        return ResponseEntity.ok().body(retrospective.get());
    }

}
