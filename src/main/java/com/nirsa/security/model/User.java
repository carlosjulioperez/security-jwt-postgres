package com.nirsa.security.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(	name = "usuario")

public class User {

    @Id
    @Column(name = "idusuario")
    private String username;

    @Column(name = "clave")
    private String password;

    @Column(name = "expirar_sesion")
    private Boolean expirarSesion;

    private Boolean estado;

   	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "usuario_acceso_empresa_proyecto", 
				joinColumns = @JoinColumn(name = "idusuario"), 
				inverseJoinColumns = @JoinColumn(name = "idgrupo"))
	private Set<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}