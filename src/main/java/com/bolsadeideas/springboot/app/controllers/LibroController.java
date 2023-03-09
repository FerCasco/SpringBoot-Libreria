package com.bolsadeideas.springboot.app.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Libro;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.ILibroService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("libro")
public class LibroController {

	@Autowired
	private ILibroService libroService;
	
	@Autowired
	private IUploadFileService upLoadFileService;
	
	private final Logger log= LoggerFactory.getLogger(getClass());
	private final static String UPLOADS_FOLDER="uploads";
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){
		 Resource recurso=null;
		try {
			 recurso=upLoadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"").body(recurso);
	}
	
	
	@GetMapping(value="/verLibro/{id}")
	public String ver(@PathVariable(value="id") Long id,Map<String, Object> model,RedirectAttributes flash) {
	
		Libro libro=libroService.findOne(id);
		if(libro==null) {
			flash.addFlashAttribute("error","El cliente no existe en nuestra BBDD");
			return "redirect:/listar";
					}
		model.put("libro", libro);
		model.put("titulo", "Detalles del Libro: "+libro.getNombre()+", "+libro.getAutor());
		return "verLibro";
	}
	
	

	@RequestMapping(value = "/listarLibros", method = RequestMethod.GET)
	public String listar(@RequestParam(name="page",defaultValue="0") int page,Model model) {
		Pageable pageRequest= PageRequest.of(page,5);
		Page<Libro> libros= libroService.findAll(pageRequest);
		
		PageRender<Libro> pageRender= new PageRender<>("/listarLibros",libros);
		
		model.addAttribute("titulo", "Listado de libros por página");
		model.addAttribute("libros", libros);
		model.addAttribute("page",pageRender);
		return "listarLibros";
	}

	@RequestMapping(value = "/formLibros")
	public String crear(Map<String, Object> model) {

		Libro libro = new Libro();
		model.put("libro", libro);
		model.put("titulo", "Formulario de Libros");
		return "formLibros";
	}

	@RequestMapping(value = "/formLibros/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Libro libro = null;

		if (id > 0) {
			libro = libroService.findOne(id);
			if (libro == null) {
				flash.addFlashAttribute("error", "El ID del libro no existe en la BBDD!");
				return "redirect:/listarLibros";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del libro no puede ser cero!");
			return "redirect:/listarLibros";
		}
		model.put("libro", libro);
		model.put("titulo", "Editar Libro");
		return "formLibros";
	}

	@RequestMapping(value = "/formLibros", method = RequestMethod.POST)
	public String guardar(@Valid Libro libro, SessionStatus status) {
		libro.setCreateAt(new Date());
		libroService.save(libro);
		status.setComplete();
		return "redirect:listarLibros";
	}

	@RequestMapping(value = "/eliminarLibro/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Libro libro=libroService.findOne(id);			
			libroService.delete(id);
			
			
			if(upLoadFileService.delete(libro.getFoto())) {
					flash.addFlashAttribute("info","La Foto "+libro.getFoto()+ " ha sido eliminada con éxito");
				}
				
			}
		
		return "redirect:/listarLibros";
	}
}