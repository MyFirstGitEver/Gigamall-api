package com.example.gigamall.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ConfirmationEntity")
@Table(name = "confirmation")
public class ConfirmationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    private String code;
    private int userId;
    private Date createdDate;
    
	public ConfirmationEntity(int userId, String code, Date createdDate) {
		this.code = code;
		this.userId = userId;
		this.createdDate = createdDate;
	}

	public ConfirmationEntity() {
	}
	
	public Integer getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public int getUserId() {
		return userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
}