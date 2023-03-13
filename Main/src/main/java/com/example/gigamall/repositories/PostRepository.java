package com.example.gigamall.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
	@Query("SELECT p from post p Where brand_id = :brandId order by likeCnt")
	public List<PostEntity> getPostUsingBrandIds(int brandId);
	
	@Query("SELECT p from post p Where id IN :ids")
	public List<PostEntity> getRandom(List<Integer> ids);
	
	@Query("SELECT p from post p order by likeCnt desc")
	public List<PostEntity> fetchTop6(Pageable pageable);
}