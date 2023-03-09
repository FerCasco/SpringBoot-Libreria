package com.bolsadeideas.springboot.app.models.dao;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IFacturaDao extends PagingAndSortingRepository<Factura, Long> {
    void save(Factura factura);
    List<Cliente> findAll();

    Optional<Object> findById(Long id);

    void deleteById(Long id);

}
