package com.example.gigamall.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gigamall.entities.CommentEntity;
import com.example.gigamall.entities.UserEntity;
import com.example.gigamall.repositories.CommentRepository;

@RequestMapping("api/comments")
@RestController
public class CommentController {
	@Autowired
	CommentRepository commentRepo;
	
	@GetMapping("/{productId}/{page}/{ofProduct}")
	public ResponseEntity<List<CommentEntity>> getCommentsOfProductInPage(
			@PathVariable("productId") int id, 
			@PathVariable("page") int page,
			@PathVariable("ofProduct") boolean ofProduct){
		
		List<CommentEntity> comments;
		
		if(ofProduct) {
			comments = commentRepo.getAllCommentsOfPage(
					id, PageRequest.of(page, 6));
		}
		else {
			comments = commentRepo.getAllCommentsOfPageUnderPost(
					id, PageRequest.of(page, 6));
		}
		
		HttpHeaders headers = null;
		
		if(page == 0) {
			int count;
			
			if(ofProduct) {
				count = commentRepo.countCommentsUnderAProduct(id);
			}
			else {
				count = commentRepo.countCommentsUnderAPost(id);
			}
			
			headers = new HttpHeaders();
			headers.set("total", count + "");
		}
		
		return ResponseEntity.ok().headers(headers).body(comments);
	}
	
	@GetMapping("/replies/{replyId}/{page}/{ofProduct}")
	public ResponseEntity<List<CommentEntity>> getRepliesOfPage(
			@PathVariable("replyId") int replyId,
			@PathVariable("page") int page,
			@PathVariable("ofProduct") boolean ofProduct){
		
		List<CommentEntity> comments = commentRepo
				.getReplies(replyId, PageRequest.of(page, 6));
		
		return ResponseEntity.ok(comments);
	}
}