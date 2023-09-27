package com.codingdojo.cristian.servicios;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.cristian.modelos.Evento;
import com.codingdojo.cristian.modelos.Mensaje;
import com.codingdojo.cristian.modelos.Usuario;
import com.codingdojo.cristian.repositorios.RepositorioEventos;
import com.codingdojo.cristian.repositorios.RepositorioMensajes;
import com.codingdojo.cristian.repositorios.RepositorioUsuarios;

@Service
public class Servicios {

	@Autowired
	private RepositorioUsuarios repoUser;
	
	@Autowired
	private RepositorioEventos repoEvent;
	
	@Autowired
	private RepositorioMensajes repoMensajes;
	
	
	//Metodo que registre un nuevo usuario
		public Usuario registrar(Usuario nuevoUsuario, BindingResult result) {
			
			//Comparamos contraseñas
			String contrasena = nuevoUsuario.getPassword();
			String confirmacion = nuevoUsuario.getConfirmacion();
			
			if(!contrasena.equals(confirmacion)) {
				result.rejectValue("confirmacion", "Matches", "Las contraseñas no coinciden");
			}
			
			//Revisamos que el correo que recibimos no exista en mi base de datos
			
			String email = nuevoUsuario.getEmail();
			Usuario existeUsuario = repoUser.findByEmail(email);
			
			if(existeUsuario != null) {
				//El correo esta creado
				result.rejectValue("email", "Unique", "El correo ingresado ya se encuentra registrado");
			}
			
			//Si existe error, entonces regresamos null
			if(result.hasErrors()) {
				return null;
			}else {
				//Si no hay error guardamos
				//Encriptamos la contraseña
				String contra_encriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
				nuevoUsuario.setPassword(contra_encriptada);
				return repoUser.save(nuevoUsuario);
			}
		}
		//Revisar si el usuario existe y las contraseñas coinciden
		public Usuario login(String email, String password) {
			//Revisamos que el correo este en la base de datos 
			Usuario usuarioInicioSesion = repoUser.findByEmail(email); //Objeto Usuario o null
			if(usuarioInicioSesion == null) {
				return null;
			}
			//Comparamos contraseñas 
			if(BCrypt.checkpw(password, usuarioInicioSesion.getPassword())) {
				return usuarioInicioSesion;
			}else {
				return null;
			}
			
		}
		
		public Evento guardarEvento(Evento nuevoEvento) {
			return repoEvent.save(nuevoEvento);
		}
		public Usuario encontrarUsuario(Long id) {
			return repoUser.findById(id).orElse(null);
		}
		
		public List<Evento> eventosEnMiEstado(String estado){
			
			return repoEvent.findByEstado(estado);
		}
		public List<Evento> eventosOtrosEstados(String estado){
			
			return repoEvent.findByEstadoIsNot(estado);
		}
		
		public Evento encontrarEvento(Long id) {
			return repoEvent.findById(id).orElse(null);
		}
		public void unirAlEvento(Long usuario_id, Long evento_id) {
			Usuario miUsuario = encontrarUsuario(usuario_id);
			Evento miEvento = encontrarEvento(evento_id);
			
			miUsuario.getEventosAsistidos().add(miEvento); //Le agrego el evento a asistir 
			repoUser.save(miUsuario);
			
			
			
			
			
		}
		public void quitarEvento(Long usuario_id, Long evento_id) {	//Quitar un evento asistido
			Usuario miUsuario = encontrarUsuario(usuario_id);
			Evento miEvento = encontrarEvento(evento_id);
			
			miUsuario.getEventosAsistidos().remove(miEvento);
			repoUser.save(miUsuario);
		}
		
		public Mensaje guardarMensaje(Mensaje nuevoMensaje) {
			return repoMensajes.save(nuevoMensaje);
		}
}		
