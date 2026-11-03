# Comércio Eletrônico — API REST

API REST desenvolvida com Java 17 e Spring Boot 4.1.2 para gerenciamento de um catálogo de produtos.

O projeto foi desenvolvido como parte de um portfólio de desenvolvimento backend, com foco na aplicação de conceitos de arquitetura em camadas, persistência de dados, validações, relacionamentos entre entidades e testes automatizados.

## Tecnologias utilizadas

- Java 17
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA
- PostgreSQL 17
- Docker Compose
- Flyway
- Maven
- Jakarta Bean Validation
- JUnit 5 e Mockito

## Funcionalidades

A primeira versão da aplicação oferece:

- Gerenciamento de categorias, marcas, etiquetas e produtos.
- Operações de cadastro, consulta, atualização e exclusão.
- Associação de produtos a categorias e marcas.
- Associação de múltiplas etiquetas aos produtos.
- Pesquisa de produtos por nome, categoria e marca.
- Combinação de filtros de pesquisa.
- Paginação dos resultados.
- Validação dos dados recebidos pela API.

A aplicação disponibiliza 20 endpoints REST.

## Arquitetura

O projeto utiliza uma arquitetura monolítica organizada em camadas:

- **Controller:** recebe requisições HTTP e devolve respostas.
- **DTO:** representa os dados de entrada e saída.
- **Service:** concentra as regras de negócio.
- **Repository:** realiza o acesso aos dados.
- **Entity:** representa as entidades persistidas.
- **Exception:** organiza o tratamento de exceções.

O PostgreSQL é utilizado para persistência e o Flyway gerencia as migrações do banco de dados.

## Documentação técnica

A documentação detalhada está organizada na pasta `docs`.

| Documento | Conteúdo |
|---|---|
| [Requisitos funcionais](docs/requisitos/requisitos-funcionais.md) | Funcionalidades e regras do sistema |
| [Requisitos não funcionais](docs/requisitos/requisitos-nao-funcionais.md) | Características técnicas e de qualidade |
| [Diagrama entidade-relacionamento](docs/diagramas/entidade-relacionamento.md) | Estrutura e relacionamentos das entidades |
| [Arquitetura do sistema](docs/diagramas/arquitetura.md) | Organização dos componentes |
| [Documentação da API](docs/api/endpoints.md) | Endpoints, parâmetros e exemplos |

Os diagramas são representados utilizando Mermaid e podem ser visualizados diretamente no GitHub.

## Pré-requisitos

Para executar o projeto localmente:

- Java JDK 17
- Docker com Docker Compose
- Maven ou Maven Wrapper

## Configuração do ambiente

Clone o repositório e acesse a pasta do projeto.

Na raiz, crie um arquivo `.env` com a variável:

```env
DB_PASSWORD=defina_uma_senha_local
```

O arquivo `.env` contém configurações locais e não deve ser enviado ao GitHub.

### Iniciar o PostgreSQL

Execute:

```powershell
docker compose up -d
```

O banco estará disponível em:

```text
localhost:5434
```

Configurações utilizadas:

- Banco: `comercioeletronico`
- Usuário: `comercioeletronico`
- Imagem Docker: `postgres:17`

### Configurar a variável de ambiente

O Docker Compose utiliza o arquivo `.env` para configurar o contêiner.

A aplicação Spring Boot também precisa receber a variável `DB_PASSWORD`.

No PowerShell, execute:

```powershell
$env:DB_PASSWORD = (Get-Content .env | Where-Object { $_ -match '^DB_PASSWORD=' } | Select-Object -First 1) -replace '^DB_PASSWORD=', ''
```

Esse comando considera que o arquivo `.env` possui a variável `DB_PASSWORD` em uma única linha, sem aspas ou espaços adicionais.

### Executar a aplicação

No PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Por padrão, a API estará disponível em:

```text
http://localhost:8080
```

Durante a inicialização, o Flyway verifica e executa as migrações pendentes.

## Endpoints principais

| Recurso | Endpoint | Métodos |
|---|---|---|
| Categorias | `/categorias` | GET, POST |
| Categorias | `/categorias/{id}` | GET, PUT, DELETE |
| Marcas | `/marcas` | GET, POST |
| Marcas | `/marcas/{id}` | GET, PUT, DELETE |
| Etiquetas | `/etiquetas` | GET, POST |
| Etiquetas | `/etiquetas/{id}` | GET, PUT, DELETE |
| Produtos | `/produtos` | GET, POST |
| Produtos | `/produtos/{id}` | GET, PUT, DELETE |

### Exemplo de cadastro de produto

**POST `/produtos`**

```json
{
  "nome": "Teclado Mecânico",
  "preco": 199.90,
  "categoriaId": 1,
  "marcaId": 1,
  "etiquetasIds": [1]
}
```

Os identificadores de categoria, marca e etiquetas devem corresponder a registros existentes.

### Pesquisa de produtos

Exemplo de pesquisa combinando filtros e paginação:

```http
GET /produtos?nome=teclado&categoriaId=1&marcaId=1&page=0&size=5
```

A documentação completa dos endpoints está disponível em [Documentação da API](docs/api/endpoints.md).

## Testes automatizados

O projeto possui testes automatizados utilizando JUnit 5 e Mockito.

Os testes unitários do serviço de produtos verificam operações de cadastro, consulta, atualização, exclusão, filtros e associações entre entidades.

Para executar os testes:

```powershell
.\mvnw.cmd test
```

## Escopo da primeira versão

A versão inicial concentra-se no gerenciamento do catálogo de produtos.

Funcionalidades como autenticação, clientes, carrinho de compras, pedidos e pagamentos não fazem parte do escopo atual.

Esses recursos poderão ser implementados em versões futuras.

## Objetivo do projeto

Demonstrar conhecimentos práticos de desenvolvimento backend com Java e Spring Boot, incluindo organização do código, integração com banco de dados relacional, construção de APIs REST, validações, testes e documentação técnica.