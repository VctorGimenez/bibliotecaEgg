package com.example.demoBibliotecaDos.repositorio;

import com.example.demoBibliotecaDos.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Gimenez Victor
 */
@Repository
public interface ImagenRepo extends JpaRepository<Imagen, String> {

    
}
