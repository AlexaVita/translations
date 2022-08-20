package com.studentProject.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Требуется название")
    private String title;
    @Lob
    @NotEmpty(message = "Требуется текст")
    private String text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<Comment> comments;

    public Chapter() {
    }

    public Chapter(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }
}
