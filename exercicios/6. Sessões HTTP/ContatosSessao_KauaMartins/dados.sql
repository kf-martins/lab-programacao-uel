CREATE DATABASE IF NOT EXISTS contatos_sessao
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE contatos_sessao;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contatos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(120) NOT NULL,
    telefone VARCHAR(30) NOT NULL,
    email VARCHAR(160) NOT NULL,
    endereco VARCHAR(255),
    data_nascimento DATE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_contatos_email (email)
) ENGINE=InnoDB;

INSERT INTO usuarios (nome, email, senha)
VALUES ('Usuário de Teste', 'usuario@exemplo.com', '123456')
ON DUPLICATE KEY UPDATE
    nome = VALUES(nome),
    senha = VALUES(senha);

INSERT INTO contatos (nome, telefone, email, endereco, data_nascimento)
VALUES
    ('Ana Silva', '43999990001', 'ana@exemplo.com', 'Rua Exemplo, 100', '2000-01-01'),
    ('Bruno Souza', '43999990002', 'bruno@exemplo.com', 'Avenida Brasil, 250', '1998-05-14'),
    ('Carla Oliveira', '43999990003', 'carla@exemplo.com', 'Rua das Flores, 35', '2001-11-22')
ON DUPLICATE KEY UPDATE
    nome = VALUES(nome),
    telefone = VALUES(telefone),
    endereco = VALUES(endereco),
    data_nascimento = VALUES(data_nascimento);
