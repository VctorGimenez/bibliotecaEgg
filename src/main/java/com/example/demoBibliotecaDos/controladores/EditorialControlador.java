package com.example.demoBibliotecaDos.controladores;

import com.example.demoBibliotecaDos.entidades.Editorial;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.servicios.EditorialServis;
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
@RequestMapping("/editorial")
public class EditorialControlador {

     @Autowired
    private EditorialServis editorialServis;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            editorialServis.crearEditorial(nombre);
            modelo.put("exito", "Editorial cargada exitosamente");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "editorial_form.html";
        }
        return "index.html";   
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Editorial> editoriales = editorialServis.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServis.getO(id));
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modficar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            editorialServis.modificarEditorial(nombre, id);
            return "redirect:../lista";
        } catch (MiException e) {
            modelo.put("editorial", editorialServis.getO(id));
            return "editorial_modificar.html";
        }
    }
}
