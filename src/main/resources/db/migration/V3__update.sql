-- Clave en texto plano: 1234
UPDATE nw.public.usuario SET clave = '$2a$10$qtrcsZJTM3pnnOOZxc4eJ.273a.N/7SwXdyBNbRMMn2kH0NJqDKfK' WHERE idusuario = 'tic';

UPDATE nw.public.grupo SET grupo = 'ROLE_ADMIN' WHERE idgrupo = 103;
