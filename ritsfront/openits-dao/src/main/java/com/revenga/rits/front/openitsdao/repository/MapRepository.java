package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.Map;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {

	Map findById(Integer id);
	List<Map> findAll();
}
