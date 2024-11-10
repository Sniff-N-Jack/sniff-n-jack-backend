package com.soen342.sniffnjack.Repository;

import com.soen342.sniffnjack.Entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
