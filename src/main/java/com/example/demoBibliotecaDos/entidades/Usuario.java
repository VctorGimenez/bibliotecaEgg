package com.example.demoBibliotecaDos.entidades;

import com.example.demoBibliotecaDos.enumeraciones.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Gimenez Victor
 */
@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String contrasenia;
    private String email;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;

}
