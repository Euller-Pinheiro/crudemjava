-- Criar um novo banco de dados
create database dbagenda;
-- Selecionar o banco de dados
use dbagenda;

/* Tabelas */
-- Criar uma nova tabela
/*
  int (tipo de dados: números inteiros)
  primary key (chave primária)
  auto_increment (numeração automática)
  varchar (tipo de dados: String de caracteres)
  not null (campo com preenchimento obrigatório)
*/
create table contatos (
	idcon int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50)
);