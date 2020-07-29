# API Java Springboot padrão para novos pequenos projetos
Essa api tem como objetivo agilizar inicio de novos projetos, nela contém:

* Controle de usuários
* Sistema de status
* Sistema de perfis de usuários
* Autenticação com JWT auth
* Recuperação de senha via email
 

# Configurações a serem feitas

### Banco de dados

    * Abrir o arquivo application.properties
    * Editar as propriedades abaixo de acordo com seu banco de dados
    *
    * spring.datasource.url=jdbc:postgresql://localhost:5432/productcontrol
    * spring.datasource.username=postgres
    * spring.datasource.password=root
    
    * Alterar a propriedade abaixo para true
    *
    * spring.jpa.hibernate.ddl-auto=none
    *
    * Após executar 1 vez a api, alterar novamente para none
    * Isso irá criar as tabelas de controle de usuários no seu banco de dados

### Chave de segurança JWT

    * Abrir a classe "JwtServiceImpl.Java"
    * Alterar o valor do atributo "secret" para sua chave secreta
    
### Configuração email SMTP

    * Abrir o arquivo application.properties
    * Editar as propriedades abaixo de acordo com seu banco de dados
    *
    * spring.mail.host=smtp.gmail.com
    * spring.mail.port=587
    * spring.mail.username=${MAILSENDER} #Criar variável de ambiente com email ou trocar para o email de autenticação
    * spring.mail.password=${MAILSENDERPW} #Criar variável de ambiente com senha ou trocar para a senha de autenticação

