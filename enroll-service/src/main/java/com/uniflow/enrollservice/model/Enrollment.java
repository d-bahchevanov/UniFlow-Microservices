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
                columnNames = {"studentId", "subjectId"}
        )
)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long studentId;
    private long facultyId;
    private long specializationId;
    private long subjectId;
    private int year;
    @Setter
    private Integer points = 0;
    @Setter
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public Enrollment(Long studentId, long facultyId,long specializationId, long subjectId, int year, EnrollmentStatus enrollmentStatus) {
        this.studentId = studentId;
        this.facultyId = facultyId;
        this.specializationId = specializationId;
        this.subjectId = subjectId;
        this.year = year;
        this.status = enrollmentStatus;
    }
}
