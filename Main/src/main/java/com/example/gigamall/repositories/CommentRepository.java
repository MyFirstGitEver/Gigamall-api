package com.example.gigamall.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
	@Query("Select COUNT(c) from comment c Where productId = :id AND level = 0")
	int countCommentsUnderAProduct(int id);
	
	@Query("Select COUNT(c) from comment c Where postId = :id AND level = 0")
	int countCommentsUnderAPost(int id);
	
	@Query("Select c from comment c "
			+ "Where level = 0 AND productId = :id order by comment_date desc")
	List<CommentEntity> getAllCommentsOfPage(int id, Pageable pageable);
	
	@Query("Select c from comment c "
			+ "Where level = 0 AND postId = :id order by comment_date desc")
	List<CommentEntity> getAllCommentsOfPageUnderPost(int id, Pageable pageable);
	
	@Query("Select c from comment c Where reply_id = :id AND (reply_id != id)"
			+ "order by comment_date desc")
	List<CommentEntity> getReplies(int id, Pageable pageable);
	
	@Query("Select c from comment c Where postOd = :id AND (reply_id != id)"
			+ "order by comment_date desc")
	List<CommentEntity> getRepliesUnderPost(int id, Pageable pageable);
}