package com.example.gigamall.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.MediaEntity;

public interface MediaRepository extends JpaRepository<MediaEntity, Integer>{
	@Modifying
	@Transactional
	@Query("Update MediaEntity SET width = :w, height = :h Where id = :id")
	public void cacheWidthAndHeight(int w, int h, int id);
}