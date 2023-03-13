package com.example.gigamall.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	@Modifying
	@Transactional
	@Query("Update UserEntity SET password = :pass Where id = :id")
	public void updatePassword(String pass, int id);
	
	@Query("SELECT e from UserEntity e Where userName = :userName")
	public UserEntity login(String userName);
}
