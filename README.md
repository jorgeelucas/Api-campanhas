# API de campanhas
> Os projetos contemplam um desafio de java.

- Maven
- Java 8+

### Instalação

- git clone 
- mvn test
- mvn clean install
- mvn spring-boot:run

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

## Descrição


#### Para comunicação foi usado a arquitetura REST, baseado no Restful. Os serviços recebem e respondem JSON

#### Na arquitetura foi usado a abordagem Package by Layer do Clean Arch (controllers, services, repositories)

#### Para banco de dados foi usado banco em memória h2, para os dois projetos e para os testes.

#### Na documentação foi pensado no swagger por ser uma ferramenta de facil implementação e usabilidade.

#### Para cache nas API's foi usado spring-boot-starter-cache, juntamente com o JCache e a API de cache padrão para Java, como provider. Para uma melhor performance nas chamadas idepotentes.

#### Para fallbacks, foi usado o Retry do próprio spring, pensei em usar RabbitMQ (uma boa oportunidade de aprender mais a fundo e implementar) para as filas após os retries mas o projeto não ficaria tão autonomo assim por ter que subir um RabbitMq server para funcionar, então quando há uma falha no projeto consumer, no caso a api de campanhas, ele tenta mais 1 vez a cada segundo (configurei assim para testes mais rapidos) por 5 vezes. Se o projeto não estiver UP durante todas essas tentativas ele chama o Recover como fallback.

#### Para testes foi usado Junit4, fazendo testes diretamente nos endpoints, testes de integração, para tentar simular mais uma chamada e as respostas mockados.

#### Nas responstas foi escolhido o objeto ResponseEntity do spring para gerenciar todo o objeto da resposta.

#### Para testes eu estava usando o POSTMAN com os endpoints informados acima.
