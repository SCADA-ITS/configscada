package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.BackOffice;

@Repository
public interface BackOfficeRepository extends JpaRepository<BackOffice, Long> {

	BackOffice findById(Integer id);
	List<BackOffice> findAll();
}
