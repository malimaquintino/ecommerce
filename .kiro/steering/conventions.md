---
inclusion: auto
name: conventions
description: Normas e acordos de desenvolvimento do projeto ecommerce. Ative sempre que criar ou alterar código Java.
---

# Normas e Acordos de Desenvolvimento

## 1. Services — Interface + Implementação

Todo service deve ter uma **interface** e uma **classe de implementação** separadas.
A **interface é sempre usada como tipo de injeção** — nunca a implementação concreta.

```
application/
├── UserService.java          ← interface
└── impl/
    └── UserServiceImpl.java  ← implementação
```

```java
// ✅ Correto — injeta pela interface
private final UserService userService;

// ❌ Errado — injeta pela implementação
private final UserServiceImpl userService;
```

A implementação recebe a anotação `@Service`. A interface não recebe nenhuma anotação Spring.

---

## 2. Sem Lombok

**Lombok é proibido neste projeto.**

Escreva todos os construtores, getters e setters manualmente.
Para objetos de transferência de dados, prefira `record` do Java — são imutáveis e geram os acessores automaticamente pela linguagem, sem dependência externa.

```java
// ✅ Correto — record para DTOs
public record CreateUserRequest(String name, String document, String email, String password) {}

// ✅ Correto — getters manuais em entidades
public String getName() { return name; }

// ❌ Errado
@Getter
@Setter
public class User { ... }
```

---

## 3. Entity, Service e Repository

Toda entidade JPA deve ter:
- Um `Repository` correspondente (interface que estende `JpaRepository`)
- Um `Service` correspondente (interface + implementação)

Estrutura esperada dentro de um contexto:
```
domain/
└── User.java                        ← entidade JPA

application/
├── UserService.java                 ← interface do service
├── impl/
│   └── UserServiceImpl.java         ← implementação do service
└── dto/
    ├── CreateUserRequest.java
    └── CreateUserResponse.java

infrastructure/
└── persistence/
    └── UserRepository.java          ← interface JpaRepository
```

---

## 4. Idioma dos campos — sempre inglês

Todos os campos de classes Java e colunas de banco de dados devem estar em **inglês**.
Caso o usuário escreva um nome em português, traduza para inglês antes de criar o código.

Exemplos de tradução:
| Português  | Inglês     |
|------------|------------|
| nome       | name       |
| documento  | document   |
| senha      | password   |
| criado_em  | created_at |
| atualizado | updated_at |
| situacao   | status     |
| codigo     | code       |
| endereço   | address    |
| telefone   | phone      |

---

## 5. Sem mapeamentos JPA entre entidades de tabelas relacionadas

**Evite anotações de relacionamento JPA** (`@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`) para modelar vínculos entre entidades.

Prefira **armazenar apenas o ID** da entidade relacionada e coordenar a persistência via injeção de service.

```java
// ✅ Correto — referência via ID, coordenação via service
public class Employee {
    private Long userId;  // só o ID, sem a entidade User
}

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final UserService userService;  // injeta o service, não o repositório

    @Transactional
    public CreateEmployeeResponse create(CreateEmployeeRequest request) {
        User user         = userService.create(...);    // salva o User
        Employee employee = new Employee(user.getId()); // usa apenas o ID
        employeeRepository.save(employee);              // salva o Employee
    }
}

// ❌ Evitar — acoplamento via mapeamento JPA
public class Employee {
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
}
```

**Motivo:** mapeamentos JPA com cascade criam dependências implícitas entre entidades, dificultam o controle transacional explícito e complicam a extração futura de contextos para serviços independentes.

---

## 6. Queries de consulta — preferir native queries

Ao criar métodos de consulta em repositórios, **prefira native queries** em vez de JPQL ou query methods derivados do nome.

Use a anotação `@Query` com `nativeQuery = true` e referencie sempre as tabelas com o schema qualificado.

```java
// ✅ Correto — native query com schema qualificado
@Query(value = "SELECT * FROM identity.users WHERE email = :email LIMIT 1", nativeQuery = true)
Optional<User> findByEmail(@Param("email") String email);

// ❌ Evitar — query method derivado (oculta o SQL real)
Optional<User> findByEmail(String email);

// ❌ Evitar — JPQL (não usa schema qualificado, abstrai demais)
@Query("SELECT u FROM User u WHERE u.email = :email")
Optional<User> findByEmail(@Param("email") String email);
```

**Motivo:** native queries são explícitas, usam o SQL real do banco, respeitam os schemas por contexto e evitam surpresas de tradução do ORM.

---

## 7. Repositories são privados aos seus services

**Repositories só devem ser injetados no service correspondente à sua entidade.**
Outros services nunca injetam um repository diretamente — sempre passam pelo service responsável.

```java
// ✅ Correto — AuthService consulta usuários pelo UserService
@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    public LoginResponse login(LoginRequest request, String ip) {
        User user = userService.findByEmail(request.email())...;
    }
}

// ❌ Errado — AuthService acessa o banco de User diretamente
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository; // viola a regra
}
```

**Motivo:** centraliza a lógica de acesso e regras de negócio no service dono da entidade. Facilita testes, manutenção e a futura extração de contextos em serviços independentes.

---

## 8. Leitura obrigatória de contexto

Antes de criar ou alterar qualquer código, sempre leia:
- `.kiro/steering/project.md` — arquitetura, contextos, regras e roadmap do projeto
- `.kiro/steering/conventions.md` — este arquivo, normas de desenvolvimento

Isso garante consistência com a arquitetura e os acordos do time.
