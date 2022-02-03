package com.nirsa.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Parametro {
    
    @Id
	@Column(name="idparametro")
    private Long id;

    private String descripcion;

	@Column(name="expiracion_ms")
    private Integer expiracionMs;
}
