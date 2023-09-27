package com.codingdojo.cristian.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.cristian.modelos.Estado;
import com.codingdojo.cristian.modelos.Evento;
import com.codingdojo.cristian.modelos.Mensaje;
import com.codingdojo.cristian.modelos.Usuario;
import com.codingdojo.cristian.servicios.Servicios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorEventos {

	
	//Pendiente SERVICIOS 
	
	@Autowired
	private Servicios servicio;
	
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model, @ModelAttribute("nuevoEvento")Evento nuevoEvento) {
		//Revisamos que el usuario haya iniciado sesion
		
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		
		// Lista de Eventos
		
		String miEstado = tempUsuario.getEstado(); //Obtenemos el estado del usuario en sesion
		
		List<Evento> eventosMiEstado = servicio.eventosEnMiEstado(miEstado);
		model.addAttribute("eventosMiEstado", eventosMiEstado);
		
		List<Evento> eventosOtroEstado = servicio.eventosOtrosEstados(miEstado);
		model.addAttribute("eventosEnOtroEstado", eventosOtroEstado);
		
		Usuario miUsuario = servicio.encontrarUsuario(tempUsuario.getId());
		model.addAttribute("usuario", miUsuario);
		
		
		model.addAttribute("estados", Estado.Estados);
		return "dashboard.jsp";
	}
	
	@PostMapping("/crear")
	public String crear(HttpSession session, @Valid @ModelAttribute("nuevoEvento")Evento nuevoEvento, BindingResult result, Model model) {
		/*-------- Revisamos Sesion ---------*/
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			model.addAttribute("estados", Estado.Estados);
			String miEstado = tempUsuario.getEstado(); //Obtenemos el estado del usuario en sesion
			
			List<Evento> eventosMiEstado = servicio.eventosEnMiEstado(miEstado);
			model.addAttribute("eventosMiEstado", eventosMiEstado);
			
			List<Evento> eventosOtroEstado = servicio.eventosOtrosEstados(miEstado);
			model.addAttribute("eventosEnOtroEstado", eventosOtroEstado);
			
			Usuario miUsuario = servicio.encontrarUsuario(tempUsuario.getId());
			model.addAttribute("usuario", miUsuario);
			return "dashboard.jsp";
		}else {
			servicio.guardarEvento(nuevoEvento);
			return "redirect:/dashboard";
		}
	}
	@GetMapping("/unir/{id}")
	public String unir(@PathVariable("id")Long evento_id, HttpSession session) {
		
		
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		
		servicio.unirAlEvento(tempUsuario.getId(), evento_id);
		return "redirect:/dashboard";
	}
	@GetMapping("/quitar/{id}")
	public String quitar(@PathVariable("id")Long evento_id, HttpSession session) {
		
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		
		servicio.quitarEvento(tempUsuario.getId(), evento_id);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/evento/{id}")
	public String evento(@PathVariable("id")Long evento_id, HttpSession session, Model model, @ModelAttribute("mensaje")Mensaje mensaje) {
		
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		
		Evento evento = servicio.encontrarEvento(evento_id);
		model.addAttribute("evento", evento);
		
		return "evento.jsp";
		
	}
	@PostMapping("/crearmensaje")
	public String crearmensaje(@Valid @ModelAttribute("mensaje") Mensaje mensaje, BindingResult result, HttpSession session,
			Model model) {
		
		Usuario tempUsuario = (Usuario)session.getAttribute("usuarioEnSesion");
		
		if(tempUsuario == null) {
			return "redirect:/";
		}
		if(result.hasErrors()) {
			model.addAttribute("evento", mensaje.getEvento());
			return "evento.jsp";
		}else {
			servicio.guardarMensaje(mensaje);
			return "redirect:/evento/"+mensaje.getEvento().getId();
		}
	}
}
