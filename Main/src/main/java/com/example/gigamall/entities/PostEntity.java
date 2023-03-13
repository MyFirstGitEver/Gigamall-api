package com.example.gigamall.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "post")
@Table(name = "post")
public class PostEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String title;
	private int likeCnt;
	
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private BrandEntity brand;
	
	@OneToMany(mappedBy = "post")
	private List<MediaEntity> images;
	
	public PostEntity() {
	}
	
	public Integer getId() {
		return id;
	}
	public BrandEntity getBrand() {
		return brand;
	}

	public String getTitle() {
		return title;
	}
	public int getLikeCnt() {
		return likeCnt;
	}

	public List<MediaEntity> getImages() {
		return images;
	}
}
