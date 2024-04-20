<div align="center">

![tasknest](https://github.com/FabianaFSantana/tasknest-api/assets/161942930/a6d11317-67e5-4688-ac2d-c0af0bcdfa0e)

# tasknest-api
APIs REST com Java e Spring Boot como backend de um sistema de gerenciador de tarefas.

</div>

## Descrição do Projeto
O TaskNest é uma API REST desenvolvida com Spring Boot para servir como backend de um sistema gerenciador de tarefas, o qual pode enviar mensagens sobre as tarefas a serem realizadas pelo usuário via email e SMS. Ele oferece recursos para a manipulação de usuáros e tarefas, assim como seu envio, proporcionando uma interface para interação com o banco de dados MySQL.

## Configuração do Ambiente

### Requisitos
Certifique-se de ter as seguintes dependências instaladas em seu ambiente de desenvolvimento:

* [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven](https://maven.apache.org/download.cgi)
* [MySQL](https://dev.mysql.com/downloads/installer/)

### Requisitos Adicionais
Será necessário para o envio das notificações e para testá-las criar contas no:
* [Twilio](https://www.twilio.com/en-us)
* [Mailtrap](https://mailtrap.io)

### Instalação
1. Clone o repositório:
```
https://github.com/FabianaFSantana/tasknest-api.git
```
2. No terminal, navegue até o diretório do projeto:
```
cd tasknest-api
```
3. Configure o banco de dados:
Certifique-se de que um servidor MySQL esteja em execução e crie um banco de dados chamado "tasknest".
```
CREATE DATABASE tasknest;
```
4. Configure as propriedades do banco de dados:
Se for o caso, edite o arquivo `src/main/resources/application.properties` e ajuste as configurações de conexão com o servidor MySQL:
```
spring.datasource.url=jdbc:mysql://localhost:3306/notifyme
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```
Modifique, também, as configurações da conexão com o Mailtrap de acordo com as credenciais presentes na sua conta do Mailtrap:
```
spring.mail.port=PORT
spring.mail.username=USERNAME
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=AUTH
```
Configure as configurações do código de conexão do Twilio com as credenciais da sua conta:
```
spring.mail.port=PORT
spring.mail.username=USERNAME
spring.mail.password=PASSWORD
spring.mail.properties.mail.smtp.auth=AUTH
```
5. Execute a aplicação:
```
mvn spring-boot:run
```
O servidor estará acessível em `http://localhost:8080` por padrão.

## Estrutura do Projeto
O projeto é estruturado da seguinte forma:
* `com.tasknest.api.constant`: Constant para definir o status e a prioridade da tarefa.
* `com.tasknest.api.controller`: Controladores para manipular as requisições HTTP.
* `com.tasknest.api.model`: Modelos de dados para representar Usuário, Tarefa, Comentario, Endereco e ViaCepEndereco.
* `com.tasknest.api.repository`: Repositórios para interação com o banco de dados.
* `com.tasknest .api.service`: Serviços para acessar endereço pela API ViaCep, para enviar email pelo Mailtrap, sms pelo Twilio e para criar métodos da entidade Notificação.

## Uso da API
A API possui os seguintes endpoints:

### Usuario:
* `POST /usuario`: Cria um novo usuario.
* `POST /usuario/cadastrarEndereco/{idUsuario}`: Cadastrar endereço por meio do cep fornecido através da API externa ViaCep.
* `POST /usuario/{idUsuario}/adicionarTarefaNaLista/{id}`: Adiciona tarefa na lista de tarefas do usuário.
* `GET /usuario`: Exibe lista com todos os usuários.
* `GET /usuario/{idUsuario}`: Obtém informações de um usuário específico.
* `GET /usuario/exibirListaDeTarefas/{idUsuario}`: Exibe lista de tarefas do usuário.
* `PUT /usuario/{idUsuario}`: Atualiza as informações de um usuário.
* `DELETE /usuario/{idUsuario}`: Exclui um usuário.
* `DELETE /usuario/{idUsuario}/removerTarefaDaListaDeUsuario/{id}`: Remove uma tarefa da lista de tarefas do usuário.

### Tarefa:
* `POST /tarefa`: Cria uma nova tarefa.
* `POST /tarefa/{id}/adicionarComentario/{idComent}`: Adiciona um comentário na lista de comentários da tarefa.
* `GET /tarefa`: Exibe lista com todas as tarefas criadas.
* `GET /tarefa/{id}`: Obtém informações de uma tarefa específica.
* `GET /tarefa/buscarPelaDataCriacao/{dataDeCriacao}`: Obtém uma lista de tarefas criadas em uma data específica.
* `GET /tarefa/buscarPelaDataVencimento/{dataDeVencimento}`: Obtém uma lista com todas as notificaçõs criadas com base na data de vencimento.
* `GET /tarefa/buscarPelaPrioridade/{prioridade}`: Obtém uma lista com todas as notificaçõs criadas com base na prioridade.
* `GET /tarefa/buscarPeloStatus/{status}`: Obtém uma lista com todas as notificaçõs criadas com base no status da tarefa.
* `GET /tarefa/buscarPeloTitulo/{titulo}`: Obtem uma tarefa específica com base no título.
* `GET /tarefa/exibirComentarios/{id}`: Obtém uma lista com todos os comentários adicionados na tarefa.
* `GET /tarefa/{idUsuario}/enviarSmsTarefa/{id}`: Envia notificação de tarefas pendentes para o usuário por SMS.
* `GET /tarefa/{idUsuario}/enviarEmailTarefa/{id}`: Envia notificação de tarefas pendentes para o usuário por email.
* `PUT /tarefa/{id}`: Atualiza os dados da tarefa.
* `DELETE /tarefa/{id}`: Exclui uma tarefa.
* `DELETE /tarefa/{id}/removerComentarioDaTarefa/{idComent}`: Remove um comentário da lista de comentários da tarefa.

### Comentario:
* `POST /comentario`: Cria um novo comentário.
* `GET /comentario`: Exibe lista de comentários.
* `GET /comentario/{idComent}`: Exibe um comentário específico com base no id.
* `PUT /comentario/{idComent}`: Atualiza um comentário específico.
* `DELETE /comentario/{idComent}`: Exclui um comnetário específico.

## Chamando os Endpoints via Postman
Após iniciar a aplicação, você pode acessar a documentação interativa da API por meio Postman. Lá, você encontrará uma interface fácil de usar para explorar e testar os endpoints da API.



