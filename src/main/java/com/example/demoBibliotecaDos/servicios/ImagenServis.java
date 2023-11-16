package com.example.demoBibliotecaDos.servicios;

import com.example.demoBibliotecaDos.entidades.Imagen;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.repositorio.ImagenRepo;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gimenez Victor
 */
@Service
public class ImagenServis {
         @Autowired
         private ImagenRepo imagenRepo;

    public Imagen guardar(MultipartFile archivo) throws MiException{
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepo.save(imagen);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException{
         if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepo.findById(idImagen);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepo.save(imagen);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
