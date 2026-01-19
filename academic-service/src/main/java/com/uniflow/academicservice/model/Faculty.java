package com.uniflow.academicservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "faculty_id"})
        }
)
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private final List<Specialization> specializations = new ArrayList<>();

    public Faculty(String name) {
        this.name = name;
    }
}
