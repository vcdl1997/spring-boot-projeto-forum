INSERT INTO tb_usuarios(nome, email, senha) VALUES 
('Aluno', 'aluno@email.com', '$2a$10$3yPIvXUjcLYIIRi9Ag6rkeXm62lDpp7Npg7LFLgctywUMLk9LPIjK'),
('Moderador', 'moderador@email.com', '$2a$10$3yPIvXUjcLYIIRi9Ag6rkeXm62lDpp7Npg7LFLgctywUMLk9LPIjK');

INSERT INTO tb_perfis(nome) VALUES ('ROLE_ALUNO'), ('ROLE_MODERADOR');

INSERT INTO tb_usuarios_perfis(usuario_id, perfis_id) VALUES (1,1), (2,2);

INSERT INTO tb_cursos(nome, categoria) VALUES ('Spring Boot', 'Programação'), ('HTML 5', 'Front-end');

INSERT INTO tb_topicos(titulo, mensagem, data_criacao, status, autor_id, curso_id) VALUES 
('Dúvida', 'Erro ao criar projeto', '2019-05-05 18:00:00', 'NAO_RESPONDIDO', 1, 1), 
('Dúvida 2', 'Projeto não compila', '2019-05-05 19:00:00', 'NAO_RESPONDIDO', 1, 1),
('Dúvida 3', 'Tag HTML', '2019-05-05 20:00:00', 'NAO_RESPONDIDO', 1, 2);
