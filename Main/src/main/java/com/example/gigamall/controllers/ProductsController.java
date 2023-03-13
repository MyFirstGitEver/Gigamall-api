package com.example.gigamall.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gigamall.entities.BrandEntity;
import com.example.gigamall.entities.ProductEntity;
import com.example.gigamall.repositories.BrandRepository;
import com.example.gigamall.repositories.ProductRepository;

@RequestMapping("api/products")
@RestController
public class ProductsController {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	BrandRepository brandRepo;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductEntity>> getAll(){
		Integer[] ids = new Integer[50];
		
		for(int i=0;i<50;i++) {
			ids[i] = i + 1;
		}
		
		List<Integer> arr = Arrays.asList(ids);
		
		Collections.shuffle(arr);
		
		return ResponseEntity.ok(productRepo.findAllById(arr.subList(0, 30)));
	}
	
	@GetMapping("brands/all")
	public ResponseEntity<List<BrandEntity>> getAllBrands(){
		return ResponseEntity.ok(brandRepo.findAll());
	}
	
	@GetMapping("category/each")
	public ResponseEntity<List<ProductEntity>> getEach(){
		return ResponseEntity.ok(productRepo.getEach());
	}
	
	@GetMapping("category/{category}/{page}/{sortCondition}/{from}/{to}")
	public ResponseEntity<List<ProductEntity>> getCategorizedProducts(
			@PathVariable("category") String category,
			@PathVariable("page") int page,
			@PathVariable("sortCondition") int sortCondition,
			@PathVariable("from") float from,
			@PathVariable("to") float to){
		
		HttpHeaders headers = null;
		if(page == 0) {
			headers = new HttpHeaders();
			headers.set("min", productRepo.findMinPrice(category) + "");
			headers.set("max", productRepo.findMaxPrice(category) + "");
		}
		
		Sort sort;
		
		if(sortCondition == 0) {
			sort = Sort.unsorted();
		}
		else if(sortCondition == 1) {
			sort = Sort.by("sold");
		}
		else {
			sort = Sort.by("price");
		}
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(productRepo.findProductsUsingCategory(
						category, from, to, PageRequest.of(page, 6, sort)));
	}
	
	@GetMapping("/mostPopular")
	public ResponseEntity<List<ProductEntity>> getMostPopular(){
		return ResponseEntity.ok(productRepo
				.getMostPopular(PageRequest.of(0, 10)));
	}
	
	@GetMapping("/search/{term}/{page}")
	public ResponseEntity<List<ProductEntity>> search(
			@PathVariable("term") String term,
			@PathVariable("page") int page){
		List<ProductEntity> products = productRepo
				.searchUsingTerm(term, PageRequest.of(page, 5));
		
		int[] ids = new int[products.size()];
		for(int i=0;i<ids.length;i++) {
			ids[i] = products.get(i).getId();
		}
		
		if(products.size() != 5 && page == 0) {
			if(products.size() == 0) {
				products.addAll(productRepo.getMostPopular(PageRequest.of(0, 2)));
			}
			else {
				products.addAll(productRepo.getOthers(ids, PageRequest.of(0, 2)));
			}
		}
		
		return ResponseEntity.ok(products);
	}
}