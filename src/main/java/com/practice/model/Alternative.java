package com.practice.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Alternative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @OneToMany(mappedBy = "alternative")
    private List<Attempt> attempts;

    private long order;

    @NotNull
    private String description;

    private boolean correct;

}
