package com.practice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ExamUpdateDTO {
    @Id
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @JsonIgnore
    private final LocalDateTime editedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }
}