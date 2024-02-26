package com.scrum.retro;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.scrum.retro.entity.Feedback;
import com.scrum.retro.entity.Participant;
import com.scrum.retro.entity.Retrospective;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class RetrospectiveEntityTest {

    @Test
    public void createRetrospective() throws Exception {

        Participant participant1 = new Participant(1L, "Viktor");
        Participant participant2 = new Participant(1L, "Gareth");
        Participant participant3 = new Participant(1L, "Mike");
        List<Participant> participants = Arrays.asList(participant1,participant2,participant3);

        Feedback item1 = new Feedback(1L, "Gareth","Sprint objective met", "Positive");
        Feedback item2 = new Feedback(2L,"Viktor","Too many items piled up in the awaiting QA","Negative");
        Feedback item3 = new Feedback(3L, "Mike", "We should be looking to start using VS2015", "Idea");

        List<Feedback> feedbacks = Arrays.asList(item1,item2,item3);
        Retrospective retrospective =  new Retrospective();
        retrospective.setName("Retrospective 1");
        retrospective.setSummary("Post release retrospective");
        retrospective.setDate(LocalDate.of(2024, 2, 23));
        retrospective.setParticipants(participants);
        retrospective.setFeedback(feedbacks);

        assertThat(retrospective.getName()).isEqualTo("Retrospective 1");
        assertThat(retrospective.getSummary()).isEqualTo("Post release retrospective");
        assertThat(retrospective.getDate()).isEqualTo(LocalDate.of(2024, 2, 23));

        Participant savedParticipant1 = retrospective.getParticipants().stream().filter(r -> r.getName().equals("Gareth")).findAny().get();
        assertThat(savedParticipant1.getName()).isEqualTo("Gareth");

        Feedback savedFeedback = retrospective.getFeedback().stream().filter(p -> p.getName().equals("Viktor")).findAny().get();
        assertThat(savedFeedback.getName()).isEqualTo("Viktor");
        assertThat(savedFeedback.getFeedbackType()).isEqualTo("Negative");
    }
}
