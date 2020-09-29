package com.revenga.rits.front.openitsdao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revenga.rits.front.openitsdao.model.DataSource;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

	DataSource findById(Integer id);
	List<DataSource> findAll();
}
