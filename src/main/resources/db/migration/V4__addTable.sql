
CREATE TABLE nw.public.parametro (
	idparametro integer NOT NULL,
	descripcion varchar(100),
	expiracion_ms int,
    PRIMARY KEY (idparametro)
);

-- Asignar el tiempo de expiración de 5 min o 300,000 ms
INSERT INTO nw.public.parametro (idparametro, descripcion, expiracion_ms) VALUES (1, 'PARAMETROS GLOBALES', 300000);
