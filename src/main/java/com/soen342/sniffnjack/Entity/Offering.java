package com.soen342.sniffnjack.Entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.soen342.sniffnjack.DTO.OfferingDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Entity
@Getter
@NoArgsConstructor
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Formula("(SELECT COUNT(*) FROM booking b WHERE b.offering_id = id)")
    private int bookedSpots;

    @NonNull
    @ManyToOne(targetEntity = Instructor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    @Setter
    private Instructor instructor;

    @NonNull
    @OneToOne(targetEntity = Offering.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @Setter
    private Lesson lesson;

    public boolean isFull() {
        return bookedSpots >= lesson.getTotalSpots();
    }

    public Offering(@NonNull Instructor instructor, @NonNull Lesson lesson) {
        this.instructor = instructor;
        this.lesson = lesson;
    }

    @JsonSerialize
    public String toString() {
        return (new OfferingDTO(this)).toString();
    }
}
