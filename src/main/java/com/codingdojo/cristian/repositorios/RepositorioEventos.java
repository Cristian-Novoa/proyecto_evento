package com.codingdojo.cristian.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.cristian.modelos.Evento;

@Repository
public interface RepositorioEventos extends CrudRepository<Evento, Long> {
	
	
	
	List<Evento> findByEstado(String estado);
	
	
	//SELECT *FROM eventos WHERE estado  != <ESTADO RECIBIDO>
	List<Evento> findByEstadoIsNot(String estado);
}
