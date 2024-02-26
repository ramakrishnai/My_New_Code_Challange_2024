package com.scrum.retro;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrum.retro.controller.ScrumCermonyRetroController;
import com.scrum.retro.entity.Feedback;
import com.scrum.retro.entity.Participant;
import com.scrum.retro.entity.Retrospective;
import com.scrum.retro.service.ScrumCermonyRetroService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;

@WebMvcTest(ScrumCermonyRetroController.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScrumCermonyRetroControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ScrumCermonyRetroService service;

    @Autowired
    private ObjectMapper objectMapper;

    Participant participant1, participant2, participant3;
    List<Participant> participants;
    Feedback feedbackItem1, feedbackItem2,  feedbackItem3;
    List<Feedback> feedbacks;
    Retrospective retrospectiveEntity;
    String json = "";


    @Test
    public void saveRetrospectiveTest() throws Exception {

        Retrospective stubbedRetrospective = retrospectiveEntity;
        stubbedRetrospective.setId(100L);
        when(service.createRetrospective(retrospectiveEntity)).thenReturn(stubbedRetrospective);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/retros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(retrospectiveEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Retrospective 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.summary").value("Post release retrospective"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.participants").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedback").isEmpty());
    }

    @Test
    public void updateRetrospective_FeedbackTest() throws Exception {

        List<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(feedbackItem1);
        retrospectiveEntity =  Retrospective.builder()
                .id(1L)
                .name("Retrospective 1")
                .summary("Post release retrospective")
                .date(LocalDate.of(2024, 2, 23))
                .participants(participants)
                .feedback(feedbacks)
                .build();

        when(service.updateRetrospective(feedbackItem1, 1L)).thenReturn(retrospectiveEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/retros/feedbacks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(retrospectiveEntity)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @BeforeEach
    void setup() {
        participant1 = Participant.builder().id(1L).name("Viktor").build();
        participant2 = Participant.builder().id(2L).name("Gareth").build();
        participant3 = Participant.builder().id(3L).name("Mike").build();
        participants = Arrays.asList(participant1,participant2,participant3);

        feedbackItem1 = Feedback.builder().id(1L).name("Gareth").body("Sprint objective met").feedbackType("Positive").build();
        feedbackItem1 = Feedback.builder().id(2L).name("Viktor").body("Too many items piled up in the awaiting QA").feedbackType("Negative").build();
        feedbackItem1 = Feedback.builder().id(3L).name("Mike").body("We should be looking to start using VS2015").feedbackType("Idea").build();
        feedbacks = Arrays.asList(feedbackItem1,feedbackItem2,feedbackItem3);

        retrospectiveEntity =  Retrospective.builder()
                .name("Retrospective 1")
                .summary("Post release retrospective")
                .date(LocalDate.of(2024, 2, 23))
                .participants(participants)
                //.feedback(feedbacks)
                .build();
    }


}
