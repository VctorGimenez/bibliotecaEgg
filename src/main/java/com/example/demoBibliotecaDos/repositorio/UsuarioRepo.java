/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demoBibliotecaDos.repositorio;

import com.example.demoBibliotecaDos.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gimenez Victor
 */
@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, String>{
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarEmail(@Param("email") String email);

}
