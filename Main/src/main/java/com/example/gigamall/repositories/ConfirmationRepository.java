package com.example.gigamall.repositories;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.ConfirmationEntity;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Integer> {
	@Query("SELECT c from ConfirmationEntity c Where userId = :userId")
	public ConfirmationEntity getConfirmationByUserId(int userId);
	
	@Transactional
	@Modifying
	@Query("Update ConfirmationEntity SET code = :code, createdDate = :date Where id = :id")
	public void updateCodeAndTime(String code, Date date, int id);
}