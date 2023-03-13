package com.example.gigamall.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ProductEntity")
@Table(name = "product")
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

    private String title, type, url, description;
    private float price;
    private int sold, star;
    
    public ProductEntity() {
	}
    
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public String getDescription() {
		return description;
	}
	public float getPrice() {
		return price;
	}
	public int getSold() {
		return sold;
	}
	public int getStar() {
		return star;
	}
}