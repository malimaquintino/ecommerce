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

## 5. Leitura obrigatória de contexto

Antes de criar ou alterar qualquer código, sempre leia:
- `.kiro/steering/project.md` — arquitetura, contextos, regras e roadmap do projeto
- `.kiro/steering/conventions.md` — este arquivo, normas de desenvolvimento

Isso garante consistência com a arquitetura e os acordos do time.
