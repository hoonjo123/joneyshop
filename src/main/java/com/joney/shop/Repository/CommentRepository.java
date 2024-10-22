package com.joney.shop.Repository;

import com.joney.shop.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment>  findAllByParentId(Long parentId);
}
