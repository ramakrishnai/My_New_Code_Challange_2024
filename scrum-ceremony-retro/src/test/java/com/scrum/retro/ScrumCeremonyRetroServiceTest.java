package com.scrum.retro;

import com.scrum.retro.entity.Feedback;
import com.scrum.retro.entity.Participant;
import com.scrum.retro.entity.Retrospective;
import com.scrum.retro.repository.ScrumCeremonyRetroRepository;
import com.scrum.retro.service.ScrumCermonyRetroService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class ScrumCeremonyRetroServiceTest {

    @InjectMocks
    ScrumCermonyRetroService retroService;

    @Mock
    ScrumCeremonyRetroRepository repository;

    Participant participant1, participant2, participant3;
    List<Participant> participants;
    Feedback feedbackItem1, feedbackItem2,  feedbackItem3;
    List<Feedback> feedbacks;
    Retrospective retrospectiveEntity;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        participant1 = Participant.builder().name("Viktor").build();
        participant2 = Participant.builder().name("Gareth").build();
        participant3 = Participant.builder().name("Mike").build();
        participants = Arrays.asList(participant1,participant2,participant3);

        feedbackItem1 = Feedback.builder().name("Viktor").body("Sprint objective met").feedbackType("Positive").build();
        feedbackItem2 = Feedback.builder().name("Gareth").body("Sprint objective met").feedbackType("Positive").build();
        feedbackItem3 = Feedback.builder().name("Mike").body("Sprint objective met").feedbackType("Positive").build();
        feedbacks = Arrays.asList(feedbackItem1,feedbackItem2,feedbackItem3);

        retrospectiveEntity =  Retrospective.builder()
                .id(1L)
                .name("Retrospective 1")
                .summary("Post release retrospective")
                .date(LocalDate.of(2024, 2, 23))
                .participants(participants)
                .feedback(feedbacks)
                .build();

    }

    @Test
    public void createRetrospectiveTest() throws Exception {

        when(repository.save(retrospectiveEntity)).thenReturn(retrospectiveEntity);
        Retrospective resultFromService = retroService.createRetrospective(retrospectiveEntity);
        Assertions.assertThat(resultFromService.getId()).isGreaterThan(0);
    }

    @Test
    public void find_Retrospective_ByDateTest() throws Exception {

        when(repository.findByDate(LocalDate.of(2024, 2, 23))).thenReturn(Optional.ofNullable(retrospectiveEntity));
        Optional<Retrospective> resultFromService = retroService.findByDate(LocalDate.of(2024, 2, 23));
        Assertions.assertThat(resultFromService.get().getName()).isEqualTo("Retrospective 1");
        Assertions.assertThat(resultFromService.get().getSummary()).isEqualTo("Post release retrospective");
        Assertions.assertThat(resultFromService.get().getDate()).isEqualTo(LocalDate.of(2024, 2, 23));
    }

}
