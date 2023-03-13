package com.example.gigamall.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "brand")
@Table(name = "brand")
public class BrandEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    private String name, logoUrl;
    
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
}