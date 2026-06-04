-- codigo para pre cadastrar coisas necessarias no projeto
-- todas as senhas sao 8676

insert into users (id, criado_em, email, role, senha)
values (1, '2026-05-26 00:00:00.000000', 'admin@gmail.com', 'ADMIN', '$2a$10$qT9ZZD9mpB9hOAYTblyf7eazwsTb0MWgWxbMuN34Umo7S89MyjPre');

insert into users (id, criado_em, email, role, senha)
values (2, '2026-05-26 00:00:00.000000', 'medico@gmail.com', 'USER', '$2a$10$qT9ZZD9mpB9hOAYTblyf7eazwsTb0MWgWxbMuN34Umo7S89MyjPre');

insert into admins (id, nome, user_id)
values (1, 'Cupeludos2', 1);

insert into medicos (id, cidade, cpf, crm, especialidade, estado, hospital, nome, user_id)
values (1, 'Curitiba', '11812728905', '123456', 'CARDIOLOGISTA', 'Paraná', 'Pequeno Príncipe', 'Cupeludos', 2);

insert into perguntas (descricao) values
('Deficiência intelectual'),
('Face alongada / orelhas grandes'),
('Macroorquidismo'),
('Hipermobilidade articular'),
('Dificuldades de aprendizagem'),
('Déficit de atenção'),
('Movimentos repetitivos'),
('Atraso na fala'),
('Hiperatividade'),
('Evita contato visual'),
('Evita contato físico'),
('Agressividade');