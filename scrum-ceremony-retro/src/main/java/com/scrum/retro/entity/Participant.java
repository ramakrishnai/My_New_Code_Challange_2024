package com.scrum.retro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="participant")
public class Participant {

    @Id
    @GeneratedValue
    @Column(name="part_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}