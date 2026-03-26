package com.uniflow.enrollservice.model;

import com.uniflow.enrollservice.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"studentId", "subjectName", "semester"}
        )
)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long studentId;
    private String facultyName;
    private String specializationName;
    private String subjectName;
    private int semester;
    @Setter
    private Integer points = 0;
    @Setter
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public Enrollment(Long studentId, String facultyName, String specializationName, String subjectName, int semester, EnrollmentStatus enrollmentStatus) {
        this.studentId = studentId;
        this.facultyName = facultyName;
        this.specializationName = specializationName;
        this.subjectName = subjectName;
        this.semester = semester;
        this.status = enrollmentStatus;
    }
}
