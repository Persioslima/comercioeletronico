# Documentação da API — Comércio Eletrônico

**Projeto:** Comércio Eletrônico — API REST  
**Versão:** 1.0  
**Formato de dados:** JSON

## 1. Visão geral

Esta API permite gerenciar um catálogo de produtos, incluindo categorias, marcas e etiquetas.

As operações são disponibilizadas por endpoints REST, utilizando os métodos HTTP GET, POST, PUT e DELETE.

### Endereço local

```text
http://localhost:8080
```

O endereço considera a porta padrão do Spring Boot, sem configuração adicional de porta.

## 2. Códigos HTTP

| Código | Significado | Utilização |
|---|---|---|
| 200 OK | Operação realizada | Consultas e atualizações bem-sucedidas |
| 201 Created | Recurso criado | Cadastros |
| 204 No Content | Operação realizada sem corpo | Exclusões bem-sucedidas |
| 400 Bad Request | Requisição inválida | Erros de validação e parâmetros inválidos, conforme tratamento da aplicação |
| 404 Not Found | Recurso não encontrado | Consultas, atualizações ou exclusões de registros inexistentes, conforme tratamento da aplicação |

## 3. Categorias

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/categorias` | Cadastrar categoria |
| GET | `/categorias` | Listar categorias |
| GET | `/categorias/{id}` | Buscar categoria por ID |
| PUT | `/categorias/{id}` | Atualizar categoria |
| DELETE | `/categorias/{id}` | Excluir categoria |

### Exemplo de cadastro

**POST `/categorias`**

Requisição:

```json
{
  "nome": "Informática"
}
```

Resposta de sucesso — `201 Created`:

```json
{
  "id": 1,
  "nome": "Informática"
}
```

O exemplo considera o método de cadastro corrigido para retornar `CategoriaRespostaDto`.

## 4. Marcas

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/marcas` | Cadastrar marca |
| GET | `/marcas` | Listar marcas |
| GET | `/marcas/{id}` | Buscar marca por ID |
| PUT | `/marcas/{id}` | Atualizar marca |
| DELETE | `/marcas/{id}` | Excluir marca |

### Exemplo de cadastro

**POST `/marcas`**

Requisição:

```json
{
  "nome": "Marca Exemplo"
}
```

Resposta de sucesso — `201 Created`:

```json
{
  "id": 1,
  "nome": "Marca Exemplo"
}
```

## 5. Etiquetas

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/etiquetas` | Cadastrar etiqueta |
| GET | `/etiquetas` | Listar etiquetas |
| GET | `/etiquetas/{id}` | Buscar etiqueta por ID |
| PUT | `/etiquetas/{id}` | Atualizar etiqueta |
| DELETE | `/etiquetas/{id}` | Excluir etiqueta |

### Exemplo de cadastro

**POST `/etiquetas`**

Requisição:

```json
{
  "nome": "Destaque"
}
```

Resposta de sucesso — `201 Created`:

```json
{
  "id": 1,
  "nome": "Destaque"
}
```

O cadastro de etiquetas também informa a localização do recurso criado por meio do cabeçalho `Location`.

## 6. Produtos

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Buscar produto por ID |
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Excluir produto |

### Exemplo de cadastro

**POST `/produtos`**

Requisição:

```json
{
  "nome": "Teclado Mecânico",
  "preco": 199.90,
  "categoriaId": 1,
  "marcaId": 1,
  "etiquetasIds": [1]
}
```

Resposta de sucesso — `201 Created`:

```json
{
  "id": 1,
  "nome": "Teclado Mecânico",
  "preco": 199.90,
  "categoriaId": 1,
  "categoriaNome": "Informática",
  "marcaId": 1,
  "marcaNome": "Marca Exemplo",
  "etiquetas": [
    {
      "id": 1,
      "nome": "Destaque"
    }
  ]
}
```

Os identificadores utilizados são ilustrativos. Categoria, marca e etiquetas precisam existir previamente no banco de dados.

### Pesquisa e paginação

**GET `/produtos`**

Parâmetros disponíveis:

| Parâmetro | Tipo | Obrigatório | Descrição |
|---|---|---|---|
| `nome` | String | Não | Filtrar produtos pelo nome |
| `categoriaId` | Long | Não | Filtrar por categoria |
| `marcaId` | Long | Não | Filtrar por marca |
| `page` | Integer | Não | Número da página, iniciando em zero |
| `size` | Integer | Não | Quantidade de registros por página |

Valores padrão:

- `page=0`
- `size=5`

Exemplos:

```http
GET /produtos
GET /produtos?nome=teclado
GET /produtos?categoriaId=1
GET /produtos?marcaId=1
GET /produtos?nome=teclado&categoriaId=1&marcaId=1
GET /produtos?page=0&size=10
```

A listagem retorna uma estrutura paginada do Spring Data, contendo os registros e informações sobre a paginação.

## 7. Validações de produtos

| Campo | Regra |
|---|---|
| `nome` | Obrigatório e não pode conter apenas espaços |
| `preco` | Obrigatório e deve ser maior ou igual a 0,01 |
| `categoriaId` | Obrigatório e deve identificar uma categoria existente |
| `marcaId` | Obrigatório e deve identificar uma marca existente |
| `etiquetasIds` | Opcional; quando informado, deve conter identificadores válidos de etiquetas existentes |

## 8. Atualização de registros

As atualizações utilizam o método HTTP PUT.

Exemplo:

**PUT `/produtos/1`**

```json
{
  "nome": "Teclado Mecânico Atualizado",
  "preco": 219.90,
  "categoriaId": 1,
  "marcaId": 1,
  "etiquetasIds": [1]
}
```

Em caso de sucesso, o endpoint retorna `200 OK` com os dados atualizados.

## 9. Exclusão de registros

As exclusões utilizam o método HTTP DELETE.

Exemplo:

```http
DELETE /produtos/1
```

Em caso de sucesso, o endpoint retorna:

`204 No Content`

Quando o registro solicitado não existe, o controller retorna `404 Not Found`.

Exclusões que violem restrições de integridade do banco podem produzir erros adicionais, conforme as regras e o tratamento de exceções da aplicação.

## 10. Testando a API

As requisições podem ser executadas utilizando um cliente HTTP, como o Bruno.

Para testar o cadastro de produtos, recomenda-se cadastrar previamente uma categoria e uma marca.

As etiquetas podem ser cadastradas e associadas opcionalmente.

## 11. Observações

Os exemplos apresentados são ilustrativos e demonstram o formato esperado das requisições e respostas.

Os códigos HTTP específicos de erros de validação e de regras de negócio dependem do tratamento de exceções configurado na aplicação.

Este documento deverá ser atualizado quando novos endpoints forem implementados.