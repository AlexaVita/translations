package com.studentProject.repositories;

import com.studentProject.entities.Chapter;
import com.studentProject.entities.Comment;
import com.studentProject.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Page<Comment> findByUser(Pageable pageable, User user);

    Page<Comment> findByChapter(Pageable pageable, Chapter chapter);
}
