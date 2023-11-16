package com.example.demoBibliotecaDos.servicios;

import com.example.demoBibliotecaDos.entidades.Autor;
import com.example.demoBibliotecaDos.entidades.Editorial;
import com.example.demoBibliotecaDos.entidades.Libro;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.repositorio.AutorRepo;
import com.example.demoBibliotecaDos.repositorio.EditorialRepo;
import com.example.demoBibliotecaDos.repositorio.LibroRepo;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gimenez Victor
 */
@Service
public class LibroServis {

    @Autowired
    private LibroRepo libroRepo;

    @Autowired
    private AutorRepo autorRepo;

    @Autowired
    private EditorialRepo editorialRepo;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Libro libro = new Libro();

        Autor autor = autorRepo.findById(idAutor).get();

        Editorial editorial = editorialRepo.findById(idEditorial).get();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepo.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();

        libros = libroRepo.findAll();

        return libros;
    }

    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Optional<Libro> respuesta = libroRepo.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepo.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepo.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroRepo.save(libro);
        }
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El isbn no puede ser nulo");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares <= 0) {
            throw new MiException("El numero de ejemplares no puede ser cero");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("El id del autor no puede ser nulo o estar vacio");
        }
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiException("El id de la Editorial no puede ser nulo o estar vacio");
        }
    }

    public Libro buscar(Long isbn) {
        return libroRepo.getReferenceById(isbn);
    }

    public Libro getO(Long isbn) {
        return libroRepo.getOne(isbn);
    }

    /*
    public Autor getO(String id){
        return autorRepo.getOne(id);
    }
     */
}
