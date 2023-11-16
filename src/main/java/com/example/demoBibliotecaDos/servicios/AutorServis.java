
package com.example.demoBibliotecaDos.servicios;

import com.example.demoBibliotecaDos.entidades.Autor;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.repositorio.AutorRepo;
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
public class AutorServis {
    
    @Autowired
    AutorRepo autorRepo;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
   
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepo.save(autor);
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();
        autores = autorRepo.findAll();
        return autores;
    }
    
    //@Transactional
    public void modificarAutor(String id, String nombre) throws MiException{
        validar(id, nombre);
        
        Optional<Autor> respuesta= autorRepo.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            autorRepo.save(autor);
        }
    }
    
    public Autor getO(String id){
        return autorRepo.getOne(id);
    }
    
    private void validar(String id, String nombre) throws MiException{
         if(id == null){
            throw new MiException("El id no puede ser nulo");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }
    }
    
}

