package com.example.demoBibliotecaDos.repositorio;

import com.example.demoBibliotecaDos.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gimenez Victor
 */
@Repository
public interface AutorRepo extends JpaRepository<Autor, String>{
    
}
