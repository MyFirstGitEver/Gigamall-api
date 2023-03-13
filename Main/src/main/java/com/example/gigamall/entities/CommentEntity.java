package com.example.gigamall.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer productId, postId;
	
	private int replyId, contentInStar, level, childCount;
	private String contentInText, attatchedUrl;
	private Date commentDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;
	
	public CommentEntity(
			int userId, int replyId, int productId, int contentInStar, int level,
			int childCount,
			String contentInText, String attatchedUrl, Date commentDate) {
		this.replyId = replyId;
		this.productId = productId;
		this.contentInStar = contentInStar;
		this.contentInText = contentInText;
		this.attatchedUrl = attatchedUrl;
		this.commentDate = commentDate;
		this.childCount = childCount;
	}
	
	public CommentEntity() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getContentInStar() {
		return contentInStar;
	}
	public void setContentInStar(int contentInStar) {
		this.contentInStar = contentInStar;
	}
	public String getContentInText() {
		return contentInText;
	}
	public void setContentInText(String contentInText) {
		this.contentInText = contentInText;
	}
	public String getAttatchedUrl() {
		return attatchedUrl;
	}
	public void setAttatchedUrl(String attatchedUrl) {
		this.attatchedUrl = attatchedUrl;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public int getLevel() {
		return level;
	}
	public Integer getPostId() {
		return postId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	public UserEntity getUser() {
		return user;
	}
}
