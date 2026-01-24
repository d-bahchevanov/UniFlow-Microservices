package com.uniflow.academicservice.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "specialization_id"})
        }
)

public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @OneToMany(mappedBy = "specialization", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private final List<Subject> subjects = new ArrayList<>();

    public Specialization(String name, Faculty faculty) {
        this.name = name;
        this.faculty = faculty;
    }
}
