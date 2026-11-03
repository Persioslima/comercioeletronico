# Requisitos Não Funcionais — Comércio Eletrônico

**Projeto:** Comércio Eletrônico — API REST  
**Versão:** 1.0  
**Situação:** Documentação da primeira versão

## 1. Objetivo

Este documento apresenta os requisitos não funcionais da API REST de Comércio Eletrônico.

Os requisitos estabelecem as características técnicas, arquiteturais e de qualidade adotadas no desenvolvimento da aplicação.

## 2. Requisitos não funcionais

### 2.1. Tecnologias e plataforma

| Código | Requisito | Descrição |
|---|---|---|
| RNF001 | Linguagem de programação | A aplicação deve utilizar Java 17. |
| RNF002 | Framework | O backend deve utilizar Spring Boot 4.1.2. |
| RNF003 | Banco de dados | A persistência dos dados deve utilizar PostgreSQL 17. |
| RNF004 | Gerenciamento de dependências | O projeto deve utilizar Maven. |
| RNF005 | Ambiente de banco de dados | O PostgreSQL deve poder ser executado por meio do Docker Compose. |

### 2.2. Arquitetura e manutenção

| Código | Requisito | Descrição |
|---|---|---|
| RNF006 | Organização em camadas | A aplicação deve separar as responsabilidades entre Controller, Service e Repository. |
| RNF007 | Transferência de dados | A API deve utilizar DTOs para representar os dados de entrada e saída das operações. |
| RNF008 | Persistência | A comunicação com o banco deve utilizar Spring Data JPA. |
| RNF009 | Migrações do banco | As alterações na estrutura do banco devem ser versionadas com Flyway. |
| RNF010 | Organização do código | As classes devem estar organizadas em pacotes de acordo com suas responsabilidades. |

### 2.3. Qualidade e confiabilidade

| Código | Requisito | Descrição |
|---|---|---|
| RNF011 | Validação de entrada | A API deve validar os dados recebidos nas operações de cadastro e atualização, conforme as regras definidas. |
| RNF012 | Respostas HTTP | A API deve utilizar códigos HTTP apropriados para representar os resultados das operações. |
| RNF013 | Tratamento de erros | A aplicação deve possuir tratamento de exceções para situações previstas nas regras de negócio. |
| RNF014 | Testes automatizados | As principais regras de negócio do serviço de produtos devem possuir testes unitários utilizando JUnit 5 e Mockito. |
| RNF015 | Integridade dos dados | A aplicação deve verificar a existência das categorias, marcas e etiquetas informadas antes de associá-las aos produtos. |

### 2.4. Configuração e segurança

| Código | Requisito | Descrição |
|---|---|---|
| RNF016 | Configuração externa | A senha do banco de dados deve ser fornecida por variável de ambiente. |
| RNF017 | Proteção de credenciais | Arquivos contendo credenciais locais não devem ser versionados no repositório público. |
| RNF018 | Comunicação HTTP | A aplicação deve disponibilizar seus recursos por meio de uma API REST. |

### 2.5. Documentação

| Código | Requisito | Descrição |
|---|---|---|
| RNF019 | Documentação de execução | O repositório deve possuir instruções para configurar e executar a aplicação. |
| RNF020 | Documentação da API | Os endpoints devem ser documentados com seus métodos HTTP, caminhos e finalidades. |
| RNF021 | Documentação do banco | Os relacionamentos entre as tabelas devem ser representados em um diagrama entidade-relacionamento. |
| RNF022 | Documentação da arquitetura | A organização dos principais componentes deve ser representada em um diagrama de arquitetura. |

## 3. Evidências de implementação

Os seguintes elementos do projeto permitem verificar parte dos requisitos:

| Elemento | Requisitos relacionados |
|---|---|
| `pom.xml` | RNF001, RNF002, RNF004, RNF008, RNF014 |
| `compose.yaml` | RNF003, RNF005, RNF016 |
| `application.properties` | RNF003, RNF008, RNF009, RNF016 |
| Controllers e DTOs | RNF006, RNF007, RNF011, RNF012, RNF018 |
| Services e Repositories | RNF006, RNF008, RNF015 |
| Migrações Flyway | RNF009 |
| Testes automatizados | RNF014 |
| `.gitignore` | RNF017 |
| Documentação em `docs/` | RNF019, RNF020, RNF021, RNF022 |

A presença de um componente ou arquivo constitui uma evidência de implementação, mas não substitui a verificação do comportamento correspondente.

## 4. Limitações da primeira versão

A primeira versão concentra-se no gerenciamento de catálogo de produtos.

Não estão definidos, nesta etapa, requisitos quantitativos de desempenho, disponibilidade ou capacidade de usuários simultâneos, pois ainda não foram realizados testes específicos para estabelecer esses indicadores.

A aplicação também não possui, no escopo atual, um módulo de autenticação e autorização de usuários.

Essas características deverão ser avaliadas antes de uma eventual implantação em ambiente de produção.

## 5. Considerações finais

Os requisitos não funcionais documentam as decisões técnicas adotadas e as características de qualidade esperadas para a aplicação.

Este documento deverá ser revisado conforme a evolução do projeto e a implementação de novas funcionalidades.