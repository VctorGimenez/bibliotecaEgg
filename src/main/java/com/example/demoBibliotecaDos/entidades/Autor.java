
package com.example.demoBibliotecaDos.entidades;

/**
 * @author Gimenez Victor
 */
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Autor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy ="uuid2")
    private String id;
    @Column
    private String nombre;

    public Autor() {
    }

    public Autor(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
    
}


