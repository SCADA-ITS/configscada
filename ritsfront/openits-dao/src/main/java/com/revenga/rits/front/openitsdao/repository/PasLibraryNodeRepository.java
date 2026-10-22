package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryNode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasLibraryNodeRepository extends JpaRepository<PasLibraryNode, Long> {

	PasLibraryNode findById(Integer id);
	List<PasLibraryNode> findAll();

	<S extends PasLibraryNode> S save(S pasLibraryNode);
	void deleteById(Integer id);
}
