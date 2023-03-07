package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Libro;

public interface ILibroService {
	
	public List<Libro> findAll();
	
	public Page<Libro> findAll(Pageable pageable);

	public void save(Libro libro);
	
	public Libro findOne(Long id);
	
	public void delete(Long id);
	
}
