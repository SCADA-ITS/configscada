package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.Grid;

@Repository
public interface GridRepository extends JpaRepository<Grid, Long> {

	Grid findById(Integer id);
	List<Grid> findAll();
}
