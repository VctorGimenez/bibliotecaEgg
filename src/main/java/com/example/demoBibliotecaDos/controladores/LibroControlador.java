package com.example.demoBibliotecaDos.controladores;

import com.example.demoBibliotecaDos.entidades.Autor;
import com.example.demoBibliotecaDos.entidades.Editorial;
import com.example.demoBibliotecaDos.entidades.Libro;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.servicios.AutorServis;
import com.example.demoBibliotecaDos.servicios.EditorialServis;
import com.example.demoBibliotecaDos.servicios.LibroServis;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gimenez Victor
 */
@Controller
@RequestMapping("/libro") //localhost:8080/libro
public class LibroControlador {

    @Autowired
    private EditorialServis editorialServis;
    @Autowired
    private AutorServis autorServis;
    @Autowired
    private LibroServis libroServis;

    @GetMapping("/registrar") //localhost:8080/libro/reistrar
    public String registrar(ModelMap modelo) {
        List<Autor> autores = autorServis.listarAutores();
        List<Editorial> editoriales = editorialServis.listarEditoriales();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        try {
            libroServis.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            modelo.put("exito", "Libro cargado exitosamente");
        } catch (MiException e) {
            List<Autor> autores = autorServis.listarAutores();
            List<Editorial> editoriales = editorialServis.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", e.getMessage());
            return "libro_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista") //localhost:8080/libro/lista
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServis.listarLibros();
        modelo.put("libros", libros);
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
        modelo.put("libro", libroServis.getO(isbn));
        List<Autor> autores = autorServis.listarAutores();
        List<Editorial> editoriales = editorialServis.listarEditoriales();
        return "libro_modificar.html";
    }

    /*
   @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
      
        modelo.put("libro", libroServicio.getOne(isbn));
        
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo, MultipartFile archivo) {
        try {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);

            libroServicio.modificarLibro(archivo, isbn, titulo, ejemplares, idAutor, idEditorial);
            
                        
            return "redirect:../lista";

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            
            modelo.put("error", ex.getMessage());
            
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            
            return "libro_modificar.html";
        }

    }
    */
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor,
            String idEditorial, ModelMap modelo) {
        try {
            List<Autor> autores = autorServis.listarAutores();
            List<Editorial> editoriales = editorialServis.listarEditoriales();
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            libroServis.modificarLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            return "redirect:../lista";
        } catch (MiException ex) {
            List<Autor> autores = autorServis.listarAutores();
            List<Editorial> editoriales = editorialServis.listarEditoriales();
            modelo.put("error", ex.getMessage());
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_modificar.html";
        }

    }

}
