package com.nirsa.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "grupo")
public class Role {
	@Id
	@Column(name="idgrupo")
	private Long id;

    @Column(name = "grupo")
	private String name;
    
	public Role(String name) {
        this.name = name;
    }

}