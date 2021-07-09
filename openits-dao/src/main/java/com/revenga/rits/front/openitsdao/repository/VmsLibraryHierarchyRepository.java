package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.VmsLibraryHierarchy;

@Repository
public interface VmsLibraryHierarchyRepository extends JpaRepository<VmsLibraryHierarchy, Long> {

	VmsLibraryHierarchy findById(Integer id);
	List<VmsLibraryHierarchy> findByNodeParentId(Integer id);
	List<VmsLibraryHierarchy> findAll();

	<S extends VmsLibraryHierarchy> S save(VmsLibraryHierarchy vmsLibraryHierarchy);
	void deleteById(Integer id);
	void delete(VmsLibraryHierarchy vmsLibraryHierarchy);
}
