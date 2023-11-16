
package com.example.demoBibliotecaDos.servicios;

import com.example.demoBibliotecaDos.entidades.Editorial;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.repositorio.EditorialRepo;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gimenez Victor
 */
@Service
public class EditorialServis {
    @Autowired
    EditorialRepo editorialRepo;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepo.save(editorial);
    }
    
    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepo.findAll();
        return editoriales;
    }
    
    public void modificarEditorial(String nombre, String id) throws MiException{
        validar(nombre);
        validarId(id);
        Optional<Editorial> respuesta = editorialRepo.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            
            editorialRepo.save(editorial);
        }
    }
    
    private void validar(String nombre) throws MiException{
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
    }
    
    private void validarId(String id) throws MiException{
        if(id == null || id.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
    }
    
    public Editorial getO(String id){
        return editorialRepo.getOne(id);
    }

}
