package com.example.demoBibliotecaDos;

import com.example.demoBibliotecaDos.servicios.UsuarioServis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Gimenez Victor
 */
@Configuration
@EnableWebSecurity
public class SecWeb {

    @Autowired
    public UsuarioServis usuarioServis;

    //Encrypter contraseña
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServis)
                .passwordEncoder(new BCryptPasswordEncoder()
                );
    }
    
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                //Autorizaciones
                .authorizeHttpRequests(auth
                        -> {
                    auth.requestMatchers("/admin/*").hasRole("ADMIN"); //Acceso para usuarios con rol administrador
                    auth.requestMatchers("/login", "/registro").permitAll(); //Acceso para todos los usuarios
                    auth.requestMatchers("/css/*", "/js/*", "/img/*", "/html/*", "/registrar", "/**").permitAll(); //Acceso para todos los usuarios
                    auth.anyRequest().authenticated();
                })
                //Login
                .formLogin(form -> {
                    form.loginPage("/login"); //Pagina de login
                    form.loginProcessingUrl("/logincheck"); // Procesador de inicio de session
                    form.usernameParameter("email"); // Credenciales para el inicio de session
                    form.passwordParameter("contrasenia"); // Credenciales para el inicio de session
                    form.defaultSuccessUrl("/inicio", true); //Direccion si no hay errores
                    form.permitAll();
                })
                //Deslogearse
                .logout(logout -> {
                    logout.logoutUrl("/logout"); // Cerrar session
                    logout.logoutSuccessUrl("/"); // Regresa al index o al Login si se configura
                    logout.permitAll();
                })
                .build();
    }

}
