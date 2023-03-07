package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.dao.ILibroDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Libro;

@Service
public class LibroServiceImpl implements ILibroService {

	@Autowired
	private ILibroDao libroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Libro> findAll() {
		// TODO Auto-generated method stub
		return (List<Libro>) libroDao.findAll();
	}

	@Override
	@Transactional
	public void save(Libro libro) {
		libroDao.save(libro);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Libro findOne(Long id) {
		// TODO Auto-generated method stub
		return (Libro)(libroDao.findById(id).orElse(null));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		libroDao.deleteById(id);
		
	}

	@Override
	public Page<Libro> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return libroDao.findAll(pageable);
	}

	
}
