# Requisitos Funcionais — Comércio Eletrônico

**Projeto:** Comércio Eletrônico — API REST  
**Versão:** 1.0  
**Situação:** Funcionalidades implementadas

## 1. Objetivo

Este documento apresenta os requisitos funcionais da API REST de Comércio Eletrônico.

A primeira versão do sistema tem como objetivo gerenciar um catálogo de produtos, incluindo categorias, marcas e etiquetas.

## 2. Requisitos funcionais

### 2.1. Gerenciamento de categorias

| Código | Requisito | Descrição |
|---|---|---|
| RF001 | Cadastrar categoria | O sistema deve permitir cadastrar uma categoria informando seu nome. |
| RF002 | Listar categorias | O sistema deve permitir consultar todas as categorias cadastradas. |
| RF003 | Consultar categoria | O sistema deve permitir buscar uma categoria pelo identificador. |
| RF004 | Atualizar categoria | O sistema deve permitir alterar o nome de uma categoria existente. |
| RF005 | Excluir categoria | O sistema deve permitir excluir uma categoria existente. |

### 2.2. Gerenciamento de marcas

| Código | Requisito | Descrição |
|---|---|---|
| RF006 | Cadastrar marca | O sistema deve permitir cadastrar uma marca informando seu nome. |
| RF007 | Listar marcas | O sistema deve permitir consultar todas as marcas cadastradas. |
| RF008 | Consultar marca | O sistema deve permitir buscar uma marca pelo identificador. |
| RF009 | Atualizar marca | O sistema deve permitir alterar o nome de uma marca existente. |
| RF010 | Excluir marca | O sistema deve permitir excluir uma marca existente. |

### 2.3. Gerenciamento de etiquetas

| Código | Requisito | Descrição |
|---|---|---|
| RF011 | Cadastrar etiqueta | O sistema deve permitir cadastrar uma etiqueta informando seu nome. |
| RF012 | Listar etiquetas | O sistema deve permitir consultar todas as etiquetas cadastradas. |
| RF013 | Consultar etiqueta | O sistema deve permitir buscar uma etiqueta pelo identificador. |
| RF014 | Atualizar etiqueta | O sistema deve permitir alterar o nome de uma etiqueta existente. |
| RF015 | Excluir etiqueta | O sistema deve permitir excluir uma etiqueta existente. |

### 2.4. Gerenciamento de produtos

| Código | Requisito | Descrição |
|---|---|---|
| RF016 | Cadastrar produto | O sistema deve permitir cadastrar produtos com nome, preço, categoria e marca. |
| RF017 | Listar produtos | O sistema deve permitir consultar os produtos cadastrados. |
| RF018 | Consultar produto | O sistema deve permitir buscar um produto pelo identificador. |
| RF019 | Atualizar produto | O sistema deve permitir atualizar os dados de um produto existente. |
| RF020 | Excluir produto | O sistema deve permitir excluir um produto existente. |

### 2.5. Pesquisa e organização dos produtos

| Código | Requisito | Descrição |
|---|---|---|
| RF021 | Pesquisar por nome | O sistema deve permitir filtrar produtos pelo nome. |
| RF022 | Filtrar por categoria | O sistema deve permitir consultar produtos de determinada categoria. |
| RF023 | Filtrar por marca | O sistema deve permitir consultar produtos de determinada marca. |
| RF024 | Combinar filtros | O sistema deve permitir utilizar filtros de pesquisa simultaneamente. |
| RF025 | Paginar resultados | O sistema deve permitir consultar produtos utilizando paginação. |

### 2.6. Associação de etiquetas

| Código | Requisito | Descrição |
|---|---|---|
| RF026 | Associar etiquetas | O sistema deve permitir associar etiquetas a um produto durante seu cadastro. |
| RF027 | Atualizar etiquetas | O sistema deve permitir modificar as etiquetas associadas a um produto. |
| RF028 | Consultar etiquetas do produto | O sistema deve retornar as etiquetas associadas ao consultar um produto. |

## 3. Regras funcionais de validação

As operações de cadastro e atualização de produtos devem respeitar as seguintes condições:

- O nome do produto é obrigatório e não pode conter apenas espaços em branco.
- O preço do produto é obrigatório e deve ser maior ou igual a R$ 0,01.
- A categoria e a marca são obrigatórias.
- A categoria e a marca informadas devem existir.
- As etiquetas são opcionais.
- Quando informadas, as etiquetas devem possuir identificadores válidos e corresponder a registros existentes.

## 4. Limites da primeira versão

Esta versão concentra-se no gerenciamento do catálogo de produtos.

Não fazem parte do escopo atual:

- Cadastro e autenticação de clientes.
- Carrinho de compras.
- Processamento de pedidos.
- Pagamentos.
- Controle de entregas.

Essas funcionalidades poderão ser consideradas em futuras versões.

## 5. Considerações finais

Os requisitos apresentados representam as funcionalidades identificadas na implementação atual da API.

O documento deverá ser atualizado sempre que novas funcionalidades forem incorporadas ao projeto.