package com.example.gigamall.repositories;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.gigamall.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	@Query("SELECT p from ProductEntity p Where type = :category "
			+ "AND price >= :from AND price <= :to")
	List<ProductEntity> findProductsUsingCategory(
			String category, float from, float to, Pageable pageable);
	
	@Query("SELECT MAX(price) from ProductEntity Where type = :category")
	float findMaxPrice(String category);
	
	@Query("SELECT MIN(price) from ProductEntity Where type = :category")
	float findMinPrice(String category);
	
	@Query("SELECT p, max(p.sold) from ProductEntity p group by type")
	List<ProductEntity> getEach();
	
	@Query("SELECT p from ProductEntity p order by sold desc")
	List<ProductEntity> getMostPopular(Pageable pageable);
	
	@Query("SELECT p from ProductEntity p Where "
			+ "p.type LIKE %:term% "
			+ "OR p.title LIKE %:term% "
			+ "OR p.description LIKE %:term% order by sold desc")
	List<ProductEntity> searchUsingTerm(String term, Pageable pageable);
	
	@Query("SELECT p from ProductEntity p Where p.id NOT IN :list"
			+ " order by p.sold desc")
	List<ProductEntity> getOthers(int[] list, Pageable pageable);
}