package com.example.demoBibliotecaDos.servicios;

import com.example.demoBibliotecaDos.entidades.Imagen;
import com.example.demoBibliotecaDos.entidades.Usuario;
import com.example.demoBibliotecaDos.enumeraciones.Rol;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.repositorio.UsuarioRepo;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gimenez Victor
 */
@Service
public class UsuarioServis implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepo;
    @Autowired
    private ImagenServis imagenServis;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String contrasenia, String contrasenia2) throws MiException {
        validar(nombre, email, contrasenia, contrasenia2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
        usuario.setRol(Rol.USER);
        Imagen imagen = imagenServis.guardar(archivo);
        usuario.setImagen(imagen);
        usuarioRepo.save(usuario);

    }

      @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String contrasenia, String contrasenia2) throws MiException {
        validar(nombre, email, contrasenia, contrasenia2);
          Optional<Usuario> respuesta = usuarioRepo.findById(idUsuario);

          if (respuesta.isPresent()) {
              Usuario usuario = respuesta.get();
              usuario.setNombre(nombre);
              usuario.setEmail(email);
              usuario.setContrasenia(new BCryptPasswordEncoder().encode(contrasenia));
              usuario.setRol(Rol.USER);
              
              String idImagen =null;
              if(usuario.getImagen() != null){
                  idImagen = usuario.getImagen().getId();
              }
              Imagen imagen = imagenServis.actualizar(archivo, idImagen);
              usuario.setImagen(imagen);
              usuarioRepo.save(usuario);
              
          }

    }
    
    public Usuario getO(String id){
        return usuarioRepo.getOne(id);
    }

    public void validar(String nombre, String email, String contrasenia, String contrasenia2) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede estar vacio");
        }
        if (contrasenia.isEmpty() || contrasenia == null || contrasenia.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia y debe ser de mas de 5 digitos");
        }
        if (!contrasenia.equals(contrasenia2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }
    }

    
    //Permisos
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.buscarEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            
            // Llamada para usuarios logueados dependiendo de su rol
            // Sessiones
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            return new User(usuario.getEmail(), usuario.getContrasenia(), permisos);
        } else {
            return null;
        }
    }

}
