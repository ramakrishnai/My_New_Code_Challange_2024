package com.scrum.retro;

import com.scrum.retro.entity.Participant;
import com.scrum.retro.entity.Retrospective;
import com.scrum.retro.repository.ScrumCeremonyRetroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ScrumCeremonyRetroRepositoryTest {

    @Autowired
    private ScrumCeremonyRetroRepository retroRepository;

    @Test
    public void saveRetrospectiveTest()  {

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
        Assertions.assertEquals(retrospective.getId(), 1);
        Optional<Retrospective> savedRetrospective = retroRepository.findById(1L);

        assertThat(savedRetrospective).isNotNull();
        assertThat(savedRetrospective).isPresent();
        assertThat(savedRetrospective.get().getName()).isEqualTo("Retrospective 1");
        assertThat(savedRetrospective.get().getSummary()).isEqualTo("Post release retrospective");

        Participant savedParticipant1 = savedRetrospective.get().getParticipants().stream().filter(p -> p.getName().equals("Gareth")).findAny().get();
        assertThat(savedParticipant1.getName()).isEqualTo("Gareth");

    }

}
