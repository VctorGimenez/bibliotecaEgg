
package com.example.demoBibliotecaDos.repositorio;

import com.example.demoBibliotecaDos.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gimenez Victor
 */
@Repository
public interface EditorialRepo extends JpaRepository<Editorial, String> {
    
}
