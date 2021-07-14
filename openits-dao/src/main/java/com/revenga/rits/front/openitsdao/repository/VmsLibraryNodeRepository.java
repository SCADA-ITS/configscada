package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.VmsLibraryNode;

@Repository
public interface VmsLibraryNodeRepository extends JpaRepository<VmsLibraryNode, Long> {

	VmsLibraryNode findById(Integer id);
	List<VmsLibraryNode> findAll();

	<S extends VmsLibraryNode> S save(S vmsLibraryNode);
	void deleteById(Integer id);
	void delete(VmsLibraryNode vmsLibraryNode);
}
