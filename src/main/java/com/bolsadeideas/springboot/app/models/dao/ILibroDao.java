package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Libro;

public interface ILibroDao extends PagingAndSortingRepository<Libro, Long>{

	List<Libro> findAll();

	void save(Libro libro);

	Optional <Object> findById(Long id);

	void deleteById(Long id);

	@Query("select p from Libro p where p.nombre like %?1%")
	public List<Libro> findByNombre(String term);



}
