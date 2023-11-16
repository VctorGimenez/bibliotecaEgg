
package com.example.demoBibliotecaDos.repositorio;

import com.example.demoBibliotecaDos.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gimenez Victor
 */
@Repository
public interface LibroRepo extends JpaRepository<Libro, Long>{
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarTitulo(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarAutor(@Param("nombre") String nombre);
    
    
}
