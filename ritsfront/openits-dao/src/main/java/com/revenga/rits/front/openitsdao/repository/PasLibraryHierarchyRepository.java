package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import com.revenga.rits.front.openitsdao.model.PasLibraryHierarchy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasLibraryHierarchyRepository extends JpaRepository<PasLibraryHierarchy, Long> {

	PasLibraryHierarchy findById(Integer id);
	List<PasLibraryHierarchy> findByNodeParentId(Integer id);
	List<PasLibraryHierarchy> findByNodeChildId(Integer id);
	List<PasLibraryHierarchy> findAll();

	<S extends PasLibraryHierarchy> S save(S pasLibraryHierarchy);
	void deleteById(Integer id);
	void delete(PasLibraryHierarchy pasLibraryHierarchy);
}
