# 🖥️ VM Manager (Backend)

Sistema backend para gerenciamento de máquinas virtuais com suporte a
CRUD, controle de status e logs.\
Tecnologias principais: **Java 21**, **Spring Boot**, **PostgreSQL** e
**Flyway**.

## ⚙️ Requisitos

  Tecnologia   Versão / Configuração
  ------------ ----------------------------------------
  Java 21\
  PostgreSQL   db: `vms` --- user: `app`, pass: `app`\
  Porta API    `8080`

## 🧩 Configuração da Aplicação

**Arquivo:** `src/main/resources/application.properties`

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vms
spring.datasource.username=app
spring.datasource.password=app
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
server.port=8080

spring.web.cors.allowed-origins=http://localhost:4200
spring.web.cors.allowed-methods=GET,POST,PUT,PATCH,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

## 🚀 Como executar

### Usando Maven Wrapper

``` bash
./mvnw -DskipTests package
java -jar target/vm-manager-0.0.1-SNAPSHOT.jar
```

### Rodando com Spring Boot

``` bash
./mvnw spring-boot:run
```

## 🔗 Endpoints principais

-   Swagger UI: **http://localhost:8080/swagger-ui/index.html**
-   Base da API: **http://localhost:8080/api/v1**

  --------------------------------------------------------------------------------------
  Método               Endpoint                              Descrição
  -------------------- ------------------------------------- ---------------------------
  POST                 `/vms`                                Cria uma VM

  GET                  `/vms?page=0&size=10&name=&status=`   Lista VMs com filtro e
                                                             paginação

  GET                  `/vms/{id}`                           Busca VM por ID

  PUT                  `/vms/{id}`                           Atualiza uma VM

  DELETE               `/vms/{id}`                           Remove uma VM

  PATCH                `/vms/{id}/status`                    Altera status (start, stop,
                                                             suspend)

  GET                  `/logs?page=0&size=10`                Lista logs de ações
  --------------------------------------------------------------------------------------

## 🧪 Exemplos rápidos (curl)

### Criar VM

``` bash
curl -X POST http://localhost:8080/api/v1/vms \
-H "Content-Type: application/json" \
-d '{"name":"vm-app-01","cpu":2,"memoryGb":4,"diskGb":50}'
```

### Listar VMs

``` bash
curl "http://localhost:8080/api/v1/vms?page=0&size=10"
```

### Alterar status

``` bash
curl -X PATCH http://localhost:8080/api/v1/vms/1/status \
-H "Content-Type: application/json" \
-d '{"action":"START"}'
```

### Listar logs

``` bash
curl "http://localhost:8080/api/v1/logs?page=0&size=10"
```

## 🧱 Estrutura do projeto

    src/
    ├── main/java/com/acme/vm_manager/
    │   ├── domain/        # Entidades JPA
    │   ├── repo/          # Repositórios JPA
    │   ├── service/       # Regras de negócio
    │   ├── web/           # Controllers + ExceptionHandler
    │   └── VmManagerApplication.java
    └── resources/
        ├── db/migration/  # Scripts Flyway
        └── application.properties

## ✅ Status do Projeto

✔️ CRUD de VMs\
✔️ Ações de status (Start/Stop/Suspend)\
✔️ Logs persistidos\
✔️ Documentação via Swagger\
✔️ Integração com frontend Angular