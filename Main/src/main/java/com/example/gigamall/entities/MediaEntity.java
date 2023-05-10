package com.example.gigamall.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "MediaEntity")
@Table(name = "media")
public class MediaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private PostEntity post;
	
	private String title;
	private String url;
	
	private Integer width, height;
	
	public MediaEntity() {
	}
	
	public Integer getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
