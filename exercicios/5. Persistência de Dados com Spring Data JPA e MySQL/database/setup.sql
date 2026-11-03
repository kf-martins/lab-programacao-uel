CREATE DATABASE IF NOT EXISTS gerenciamento_contatos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'contatos_app'@'localhost'
    IDENTIFIED BY 'contatos_app';

GRANT ALL PRIVILEGES ON gerenciamento_contatos.* TO 'contatos_app'@'localhost';
FLUSH PRIVILEGES;
