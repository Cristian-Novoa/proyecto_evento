package com.codingdojo.cristian.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cristian.modelos.Mensaje;

@Repository
public interface RepositorioMensajes extends CrudRepository<Mensaje, Long> {

	
}
