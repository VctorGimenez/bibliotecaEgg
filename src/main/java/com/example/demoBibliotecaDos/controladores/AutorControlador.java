
package com.example.demoBibliotecaDos.controladores;

import com.example.demoBibliotecaDos.entidades.Autor;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.servicios.AutorServis;
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
@RequestMapping("/autor")
public class AutorControlador {
    @Autowired
    private AutorServis autorServis;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {
        try {
            autorServis.crearAutor(nombre);
            modelo.put("exito", "Editorial cargada exitosamente");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "autor_form.html";
        }
        return "index.html";   
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Autor> autores = autorServis.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autores_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor",  autorServis.getO(id));
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,  String nombre, ModelMap modelo){
        try {
            autorServis.modificarAutor( id, nombre);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("autor", autorServis.getO(id));
            return "autor_modificar.html";
        }
    }

}
