package com.scrum.retro;

import com.scrum.retro.entity.Feedback;
import com.scrum.retro.entity.Participant;
import com.scrum.retro.entity.Retrospective;
import com.scrum.retro.repository.ScrumCeremonyRetroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RetrospectiveFeedbackTest {

    @Autowired
    private ScrumCeremonyRetroRepository retroRepository;

    @Test
    public void updateRetrospectiveWithFeedbackTest()  {

        Participant participant1 = Participant.builder().name("Viktor").build();
        Participant participant2 = Participant.builder().name("Gareth").build();
        Participant participant3 = Participant.builder().name("Mike").build();
        List<Participant> participants = Arrays.asList(participant1,participant2,participant3);

        Retrospective retrospective =  Retrospective.builder()
                .name("Retrospective 1")
                .summary("Post release retrospective")
                .date(LocalDate.of(2024, 2, 23))
                .participants(participants)
                .build();

        retroRepository.save(retrospective);

        Optional<Retrospective> savedRetrospective = retroRepository.findById(1L);
        Feedback feedback = Feedback.builder().name("Gareth").body("Sprint objective met").feedbackType("Positive").build();
        List<Feedback> feedbacks = Arrays.asList(feedback);

        Retrospective retrospectiveWithFeedback = Retrospective.builder()
                .name(savedRetrospective.get().getName())
                .summary(savedRetrospective.get().getSummary())
                .date(savedRetrospective.get().getDate())
                .feedback(feedbacks)
                .build();

        Retrospective retro = retroRepository.save(retrospectiveWithFeedback);
        assertThat(savedRetrospective).isNotNull();
        assertThat(savedRetrospective).isPresent();
        assertThat(savedRetrospective.get().getName()).isEqualTo("Retrospective 1");
        assertThat(savedRetrospective.get().getSummary()).isEqualTo("Post release retrospective");

        Feedback savedFeedback = retro.getFeedback().stream().filter(p -> p.getName().equals("Gareth")).findAny().get();
        assertThat(savedFeedback.getName()).isEqualTo("Gareth");
        assertThat(savedFeedback.getFeedbackType()).isEqualTo("Positive");

    }
}
