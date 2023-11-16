package com.example.demoBibliotecaDos.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gimenez Victor
 */

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @GetMapping("/dashboard")
    public String panelAdmin(){
        return "panel.html";
    }
    
    
}
