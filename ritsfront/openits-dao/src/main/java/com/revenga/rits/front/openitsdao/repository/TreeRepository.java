package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {

	Tree findById(Integer id);
	List<Tree> findAll();
}
