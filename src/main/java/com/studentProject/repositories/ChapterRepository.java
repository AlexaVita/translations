package com.studentProject.repositories;

import com.studentProject.entities.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    Page<Chapter> findAll(Pageable pageable);

}

