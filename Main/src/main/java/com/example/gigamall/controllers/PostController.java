package com.example.gigamall.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gigamall.entities.MediaEntity;
import com.example.gigamall.entities.PostEntity;
import com.example.gigamall.repositories.MediaRepository;
import com.example.gigamall.repositories.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("api/posts/")
@RestController
public class PostController {
	@Autowired
	PostRepository postRepo;
	
	@Autowired
	MediaRepository mediaRepo;
	
	@GetMapping("/{brandId}/{page}")
	public ResponseEntity<List<PostEntity>> getBrandPosts(
			@PathVariable("brandId") int brandId) {
		return ResponseEntity.ok(
				postRepo.getPostUsingBrandIds(brandId));
	}
	
	@GetMapping("/random")
	public ResponseEntity<List<PostEntity>> getRandom(
			@RequestHeader String ids) throws JsonMappingException, JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		Integer[] sawIds = mapper.readValue(ids, Integer[].class);
		
		Random random = new Random();
		
		long returned = Math.min(18 - sawIds.length, 5);
		List<Integer> newIds = new ArrayList<>();
		
		if(returned < 5) {
			for(int i=0;i<18;i++) {
				if(!contains(i, sawIds)) {
					
				}
			}
		}
		else {
			while(newIds.size() < returned) {
				int r;
				
				while(true) {
					r = random.nextInt(1, 18);
					
					if(!contains(r, sawIds) && !newIds.contains(r)) {
						break;
					}
				}
				
				newIds.add(r);
			}
		}
		
		List<PostEntity> newPosts = postRepo.getRandom(newIds);
		
		for(PostEntity post : newPosts) {
			try {
				postImageAnalyser(post);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return ResponseEntity.ok(newPosts);
	}
	
	private boolean contains(Object key, Object[] array) {
		for(Object value : array) {
			if(key == value) {
				return true;
			}
		}
		
		return false;
	}
	
	private void postImageAnalyser(PostEntity post) throws IOException {
		for(MediaEntity media : post.getImages()) {
			if(media.getWidth() != null) {
				continue;
			}
			
			URL url = new URL(media.getUrl());
			
			BufferedImage image = ImageIO.read(url);
			
			mediaRepo.cacheWidthAndHeight(
					image.getWidth(),
					image.getHeight(), media.getId());
			
			media.setWidth(image.getWidth());
			media.setHeight(image.getHeight());
		}
	}
	
	@GetMapping("/top6")
	public ResponseEntity<List<PostEntity>> fetchTop6(){
		return ResponseEntity.ok(postRepo.fetchTop6(PageRequest.of(0, 6)));
	}
}