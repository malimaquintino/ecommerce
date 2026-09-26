# Projeto E-commerce — Contexto Geral

## Objetivo

Projeto de e-commerce de estudo com foco em **arquitetura de software e sistemas distribuídos**. A ideia central é começar simples, identificar problemas reais e evoluir a arquitetura de forma motivada — não por tendência, mas por necessidade técnica.

> **Princípio:** Começar simples. Medir os problemas. Evoluir a arquitetura quando houver uma razão técnica para isso.

---

## Stack

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4
- **Banco de dados:** PostgreSQL
- **Migrations:** Flyway
- **Mensageria (futura):** Apache Kafka
- **Infraestrutura:** Docker / Docker Compose
- **Build:** Gradle
- **Package base:** `quintino.malima.ecommerce`

---

## Arquitetura atual: Monólito Modular

O projeto é estruturado como um **monólito modular**. Todos os contextos rodam na mesma JVM e inicialmente compartilham o mesmo banco de dados, mas cada contexto tem responsabilidade, modelos e APIs próprias.

### Estrutura de pacotes

```
src/main/java/quintino/malima/ecommerce
├── identity/
├── customer/
├── catalog/
├── inventory/
├── cart/
├── order/
├── notification/
└── audit/
```

Cada contexto segue a estrutura interna:
```
{contexto}/
├── domain/
├── application/
└── infrastructure/
```

---

## Contextos do sistema

| Contexto         | Responsabilidade principal                                             |
|------------------|------------------------------------------------------------------------|
| **identity**     | Autenticação, autorização, JWT, roles, controle de acesso              |
| **customer**     | Perfil do cliente, endereços, telefones, histórico de compras          |
| **catalog**      | Produtos, categorias, descrições, status — sem controle de estoque     |
| **inventory**    | Quantidade em estoque, movimentações, estoque mínimo, concorrência     |
| **cart**         | Carrinho de compras, itens, subtotal — não representa compra realizada |
| **order**        | Pedidos, status, preços no momento da compra, endereço de entrega      |
| **notification** | Envio de email, notificações baseadas em eventos de negócio            |
| **audit**        | Registro de atividades importantes do sistema (não é log técnico)      |

---

## Regras de arquitetura (OBRIGATÓRIAS)

### 1. Contextos não acessam repositórios uns dos outros

```java
// ❌ Errado
orderService → inventoryRepository

// ✅ Correto
orderService → inventoryApplicationService (ou interface)
```

### 2. Não compartilhar entidades JPA entre contextos

Cada contexto deve ter seu próprio modelo de domínio. Uma entidade `Product` do contexto `catalog` não deve ser usada diretamente pelo `order` ou `cart`.

### 3. Referências entre contextos via ID

Quando um contexto precisa referenciar outro, armazene apenas o identificador:
```java
// ✅ Correto
private UUID productId;   // não Product product
private UUID customerId;  // não Customer customer
```

### 4. Comunicação explícita entre contextos

A comunicação entre contextos deve ocorrer por interface de aplicação ou evento de domínio. Dependências ocultas são proibidas.

### 5. Eventos representam fatos, não comandos

```
// ✅ OrderConfirmed  (fato — algo que aconteceu)
// ❌ ConfirmOrder    (comando)
```

---

## Estrutura de banco de dados — Schemas por contexto

Cada contexto possui seu próprio **schema PostgreSQL**. A aplicação nunca deve acessar diretamente tabelas pertencentes ao schema de outro contexto.

```
PostgreSQL
│
├── identity
│   ├── users
│   ├── roles
│   ├── permissions
│   └── login_attempts
│
├── customer
│   ├── customers
│   ├── addresses
│   └── phones
│
├── catalog
│   ├── products
│   └── categories
│
├── inventory
│   ├── stocks
│   └── stock_movements
│
├── cart
│   └── carts
│
├── order
│   ├── orders
│   └── order_items
│
├── notification
│   └── notifications
│
└── audit
    └── audit_events
```

### Convenções de migrations

- Arquivos em `src/main/resources/db/migration/`
- Nomeação: `V{numero}__{descricao}.sql`
- O primeiro script de cada contexto deve conter `CREATE SCHEMA IF NOT EXISTS {contexto};`
- Referenciar tabelas sempre com schema qualificado: `identity.users`, `order.orders`, etc.
- Nunca usar o prefixo do contexto no nome da tabela quando o schema já o representa (ex: `identity.users`, não `identity.identity_users`)

---

## Eventos de domínio

O projeto identifica eventos desde o início. Inicialmente processados internamente, futuramente publicados no Kafka.

Exemplos:
```
UserCreated, UserLoggedIn, UserLoginFailed
CustomerCreated
ProductCreated, ProductUpdated
StockUpdated, StockBelowMinimum
CartCreated, CartItemAdded, CartItemRemoved
OrderCreated, OrderConfirmed, OrderCancelled
```

---

## Roadmap resumido

- **Fase 1 (atual):** Monólito Modular — implementar todos os contextos
- **Fase 2:** Eventos internos → introduzir Kafka
- **Fase 3:** Consistência e concorrência (optimistic/pessimistic locking, Outbox Pattern, idempotência)
- **Fase 4:** Extração para microsserviços
- **Fase 5:** Resiliência (Circuit Breaker, Retry, Rate Limiting)
- **Fase 6:** Observabilidade (OpenTelemetry, Prometheus, Grafana)
- **Fase 7:** Escalabilidade (Redis, Cache, Kubernetes)

---

## Orientações para o Kiro

- Respeite sempre as fronteiras dos contextos — nunca crie dependências diretas entre domínios
- Ao criar código em um contexto, verifique se já existe estrutura equivalente antes de criar nova
- Siga a estrutura `domain / application / infrastructure` dentro de cada contexto
- Cada contexto tem seu próprio schema PostgreSQL — sempre use nomes qualificados (`schema.tabela`)
- O primeiro script de um novo contexto sempre cria o schema com `CREATE SCHEMA IF NOT EXISTS`
- Migrations Flyway ficam em `src/main/resources/db/migration/`
- Nomeação de migrations: `V{numero}__{descricao}.sql`
- Ao implementar operações que envolvem estoque (`inventory`), considere concorrência desde o início
- Prefira interfaces explícitas para comunicação entre contextos — nunca injete repositórios de outros contextos
- Eventos de domínio devem ser nomeados no passado (fatos), não como comandos
