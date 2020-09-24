# API de campanhas
> Os projetos contemplam um desafio de java.

- Maven
- Java 8+

### Instalação

## Api-campanhas

- **Swagger:** http://localhost:9090/swagger-ui.html
- **h2-database:** http://localhost:9090/h2-console


#### Objeto de criação esperado pelo backend:

```
{ 
	"nome": string, 
	"time": Integer, 
	"vigencia": string["dd/MM/yyyy"],
}
```

#### Rotas

```
GET => "/desafio/v1/campanhas" (Obter todos)
POST => "/desafio/v1/campanhas" (Cadastrar)
GET => "/desafio/v1/campanhas/{id}" (Obter por id)
GET => "/desafio/v1/campanhas/page" (Obter todos paginado)
DELETE => "/desafio/v1/campanhas/{id}" (Deletar por id)
PUT => "/desafio/v1/campanhas/{id}" (Alterar por id)
POST => "/desafio/v1/campanhas/associar" (Associa uma ou mais campanhas)
GET => "/desafio/v1/campanhas/novas-por-time/{id}" (Obter as novas campanhas por time)
GET => "/desafio/v1/campanhas/por-socio/{id}" (Obter as novas campanhas por socio)
```
> Todas as rotas são facilmente acessadas no swagger.
