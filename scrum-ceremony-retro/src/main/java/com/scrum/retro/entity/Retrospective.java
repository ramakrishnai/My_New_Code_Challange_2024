package com.scrum.retro.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="retrospective")
public class Retrospective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="retro_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "summary", nullable = false)
    private String summary;

    @NotNull(message = "Retrospective Date can't be  null or empty")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_retro_id", referencedColumnName = "retro_id")
    private List<Participant> participants;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_retro_id", referencedColumnName = "retro_id")
    private List<Feedback> feedback;


}
