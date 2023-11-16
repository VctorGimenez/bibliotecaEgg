package com.example.demoBibliotecaDos.controladores;

import com.example.demoBibliotecaDos.entidades.Usuario;
import com.example.demoBibliotecaDos.exepciones.MiException;
import com.example.demoBibliotecaDos.servicios.UsuarioServis;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Gimenez Victor
 */
@Controller
@RequestMapping("/")
public class PortalContenedor {

    @Autowired
    private UsuarioServis usuarioServis;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "/registrar.html";
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String contrasenia, String contrasenia2, ModelMap modelo, MultipartFile archivo) {
        try {
            usuarioServis.registrar(archivo, nombre, email, contrasenia, contrasenia2);
            modelo.put("exito", "Usuario Registrado");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registrar.html";
        }
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole ('ROLE_USER', 'ROLE_ADMIN')") //Acceso para los que estan logeados con un rol activo
    @PostMapping("/perfil/{id}")
    public String actualizar(MultipartFile archivo, @RequestParam String nombre, @RequestParam String email,
            @RequestParam String contrasenia, String contrasenia2, ModelMap modelo) {
        try {
            usuarioServis.registrar(archivo, nombre, email, contrasenia, contrasenia2);
            modelo.put("exito", "Usuario Actualizado");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registrar.html";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrectos o usuario no registrado");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole ('ROLE_USER', 'ROLE_ADMIN')") //Acceso para los que estan logeados con un rol activo
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        //Si es un ADMIN ingresa a session Administradora
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }
}
