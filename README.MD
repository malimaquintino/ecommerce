# E-commerce — Distributed Systems Case

Projeto de e-commerce desenvolvido com o objetivo de estudar, na prática, **arquitetura de software, sistemas distribuídos, mensageria, consistência, concorrência, resiliência e escalabilidade**.

O projeto será desenvolvido inicialmente como um **monólito modular**, evoluindo gradualmente para uma arquitetura distribuída.

A ideia não é começar com microsserviços por obrigação. O objetivo é construir uma aplicação funcional e, conforme novos problemas surgirem, utilizar esses problemas como motivação para evoluir a arquitetura.

---

## 🎯 Objetivo

Construir um e-commerce utilizando Java e Spring Boot como base para estudos de:

* Arquitetura de software
* Domain-Driven Design (DDD)
* Modularização
* Sistemas distribuídos
* Arquitetura orientada a eventos
* Apache Kafka
* Concorrência
* Consistência de dados
* Idempotência
* Resiliência
* Observabilidade
* Cache
* Escalabilidade
* Comunicação síncrona e assíncrona
* Microserviços

O projeto nascerá simples e será incrementado gradualmente.

---

# 🏗️ Arquitetura

A primeira versão será um **Monólito Modular**.

Embora todos os contextos sejam executados dentro da mesma aplicação e inicialmente compartilhem o mesmo banco de dados, cada contexto terá:

* responsabilidade própria;
* regras de negócio próprias;
* modelos próprios;
* serviços próprios;
* repositórios próprios;
* APIs bem definidas;
* baixo acoplamento com os demais contextos.

Estrutura inicial:

```text
src/main/java/com/example/ecommerce

├── identity
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── customer
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── catalog
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── inventory
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── cart
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── order
│   ├── domain
│   ├── application
│   └── infrastructure
│
├── notification
│   ├── domain
│   ├── application
│   └── infrastructure
│
└── audit
    ├── domain
    ├── application
    └── infrastructure
```

A estrutura interna dos contextos poderá evoluir conforme o projeto amadurecer.

---

# 🧩 Contextos do sistema

## 1. Identity Context

Responsável pela **identidade, autenticação e autorização** dos usuários.

Este contexto não deve conhecer as regras específicas de clientes, pedidos, produtos ou estoque.

### Responsabilidades

* Cadastro de usuários
* Login
* Autenticação
* Geração de JWT
* Controle de sessão
* Roles
* Permissões
* Controle de acesso
* Registro de tentativas de login

### Exemplos de roles

```text
MASTER
ADMINISTRATIVE
INVENTORY
```

Um usuário poderá possuir múltiplas roles.

Exemplo:

```text
User
 ├── ADMINISTRATIVE
 └── INVENTORY
```

### Futuramente

Este contexto poderá se transformar em:

```text
identity-service
```

e ser responsável pela autenticação dos demais serviços.

---

# 2. Customer Context

Responsável pelas informações relacionadas ao **cliente do e-commerce**.

### Responsabilidades

* Cadastro de clientes
* Alteração de dados pessoais
* Endereços
* Telefones
* Dados de contato
* Consulta do histórico de compras

Um funcionário também poderá ser cliente.

A identidade do usuário pertence ao `Identity Context`, enquanto as informações específicas do cliente pertencem ao `Customer Context`.

Exemplo:

```text
Identity
   │
   └── User

Customer
   │
   └── CustomerProfile
```

### Futuramente

Poderá se transformar em:

```text
customer-service
```

---

# 3. Catalog Context

Responsável pelo **catálogo comercial do e-commerce**.

### Responsabilidades

* Produtos
* Categorias
* Descrição dos produtos
* Modelo
* Status do produto
* Associação entre produtos e categorias
* Consulta pública do catálogo

O catálogo não deve ser responsável por controlar a quantidade disponível em estoque.

Exemplo:

```text
Product
 ├── id
 ├── name
 ├── model
 ├── description
 ├── category
 └── status
```

A quantidade disponível pertence ao contexto de estoque.

### Futuramente

Poderá se transformar em:

```text
catalog-service
```

---

# 4. Inventory Context

Responsável pelo **controle de estoque**.

### Responsabilidades

* Quantidade disponível
* Estoque mínimo
* Entrada de produtos
* Saída de produtos
* Ajustes de estoque
* Movimentações
* Validação de disponibilidade
* Detecção de estoque baixo

Exemplo:

```text
Inventory
 ├── productId
 ├── quantity
 ├── minimumQuantity
 └── updatedAt
```

### Movimentações

O estoque deverá registrar operações como:

```text
PURCHASE
SALE
CANCELLATION
ADJUSTMENT
ENTRY
```

Isso permitirá estudar posteriormente o histórico e a auditoria das operações.

### Futuramente

Poderá se transformar em:

```text
inventory-service
```

Este será um dos contextos mais interessantes para estudar:

* concorrência;
* optimistic locking;
* pessimistic locking;
* consistência;
* idempotência;
* processamento assíncrono.

---

# 5. Cart Context

Responsável pelo **carrinho de compras**.

### Responsabilidades

* Criar carrinho
* Adicionar produtos
* Remover produtos
* Alterar quantidade
* Consultar carrinho
* Calcular subtotal

Exemplo:

```text
Cart
 ├── customerId
 │
 └── items
      ├── productId
      ├── quantity
      └── unitPrice
```

O carrinho não representa uma compra realizada.

Quando o cliente finalizar a compra, o carrinho será utilizado para criação de um pedido.

### Futuramente

Poderá se transformar em:

```text
cart-service
```

Esse contexto também poderá ser utilizado para estudar:

* Redis;
* cache;
* expiração de carrinho;
* concorrência;
* escalabilidade horizontal.

---

# 6. Order Context

Responsável pelo **processo de compra e pedidos**.

Este é um dos principais contextos do sistema.

### Responsabilidades

* Criar pedido
* Validar carrinho
* Registrar itens
* Registrar preços
* Registrar endereço de entrega
* Controlar status do pedido
* Consultar pedidos
* Histórico de pedidos

### Pedido

```text
Order
 ├── id
 ├── customerId
 ├── status
 ├── total
 ├── createdAt
 └── shippingAddress
```

### Itens

```text
OrderItem
 ├── productId
 ├── productName
 ├── quantity
 ├── unitPrice
 └── totalPrice
```

O pedido deverá armazenar o preço praticado no momento da compra.

Alterações futuras no preço do produto não devem modificar pedidos antigos.

### Status inicial

```text
CREATED
CONFIRMED
CANCELLED
```

Posteriormente:

```text
CREATED
   ↓
CONFIRMED
   ↓
PREPARING
   ↓
SHIPPED
   ↓
DELIVERED
```

### Futuramente

Poderá se transformar em:

```text
order-service
```

Este contexto será utilizado para estudar:

* transações;
* consistência;
* eventos;
* Saga;
* Outbox Pattern;
* idempotência;
* comunicação com Inventory;
* comunicação com Notification.

---

# 7. Notification Context

Responsável pelo envio de **notificações aos usuários**.

Inicialmente:

```text
Email
```

Posteriormente:

```text
Email
Push Notification
WebSocket
SMS
```

### Responsabilidades

* Enviar email de boas-vindas
* Enviar confirmação de compra
* Notificar estoque baixo
* Controlar tentativas de envio
* Registrar notificações

### Eventos

O contexto deverá trabalhar preferencialmente com eventos de negócio.

Exemplo:

```text
CustomerCreated
        ↓
Notification
        ↓
Welcome Email
```

E:

```text
OrderConfirmed
        ↓
Notification
        ↓
Purchase Confirmation Email
```

### Futuramente

Poderá se transformar em:

```text
notification-service
```

Este será um dos primeiros candidatos a utilizar Kafka.

---

# 8. Audit Context

Responsável pelo **registro de atividades importantes do sistema**.

Não deve ser confundido com logs técnicos.

### Exemplos

```text
USER_LOGIN
USER_LOGIN_FAILED
PRODUCT_CREATED
PRODUCT_UPDATED
STOCK_CHANGED
ORDER_CREATED
ORDER_CANCELLED
```

Cada registro poderá possuir:

```text
AuditEvent
 ├── id
 ├── userId
 ├── action
 ├── resource
 ├── resourceId
 ├── timestamp
 └── metadata
```

### Futuramente

Este contexto poderá consumir eventos através do Kafka sem que os demais contextos precisem conhecer sua implementação.

Exemplo:

```text
OrderCreated
     ↓
    Kafka
     ↓
Audit Service
```

---

# 🔗 Relação entre os contextos

Uma visão simplificada:

```text
                    ┌──────────────┐
                    │   Identity   │
                    └──────┬───────┘
                           │
            ┌──────────────┼──────────────┐
            ↓              ↓              ↓
       Customer         Catalog       Employee
                           │
                           ↓
                       Inventory
                           │
                           ↓
                         Cart
                           │
                           ↓
                         Order
                           │
                    ┌──────┴──────┐
                    ↓             ↓
              Notification      Audit
```

No monólito, isso estará dentro da mesma aplicação.

A separação lógica será mantida mesmo assim.

---

# 📡 Eventos de domínio

Desde o início, o projeto deverá identificar eventos importantes.

Exemplos:

```text
UserCreated
UserLoggedIn
UserLoginFailed

CustomerCreated

ProductCreated
ProductUpdated

StockUpdated
StockBelowMinimum

CartCreated
CartItemAdded
CartItemRemoved

OrderCreated
OrderConfirmed
OrderCancelled
```

Inicialmente alguns eventos poderão ser processados internamente.

Posteriormente serão publicados no Kafka.

---

# 📨 Kafka

Kafka será utilizado gradualmente.

A primeira versão não precisa transformar todas as operações em mensagens.

A evolução esperada será:

```text
MONÓLITO
   ↓
Eventos internos
   ↓
Kafka
   ↓
Consumers
   ↓
Serviços independentes
```

Exemplo futuro:

```text
Order Service
      │
      │ OrderConfirmed
      ↓
    Kafka
      │
      ├───────────────┐
      ↓               ↓
Inventory         Notification
 Service             Service
```

---

# 🗄️ Banco de dados

Banco inicial:

```text
PostgreSQL
```

Durante a fase de monólito, os contextos poderão compartilhar o mesmo banco.

Entretanto, cada contexto deverá possuir suas próprias tabelas.

Exemplo:

```text
identity_users
identity_roles

customers
customer_addresses
customer_phones

products
categories

inventory
inventory_movements

carts
cart_items

orders
order_items
order_shipping_addresses

notifications

audit_events
```

A aplicação não deverá acessar diretamente tabelas pertencentes a outro contexto.

A comunicação deverá ocorrer através das APIs/interfaces do contexto.

---

# 🔐 Regras importantes de arquitetura

Mesmo sendo um monólito, o projeto deverá seguir algumas regras para facilitar uma futura distribuição.

### Regra 1 — Contextos não acessam repositórios uns dos outros

Evitar:

```text
OrderService
   ↓
InventoryRepository
```

Preferir:

```text
OrderService
   ↓
InventoryApplicationService
```

ou uma interface claramente definida.

---

### Regra 2 — Não compartilhar entidades JPA entre contextos

Evitar:

```java
@Entity
class Product {
    ...
}
```

sendo utilizado diretamente por:

```text
Catalog
Inventory
Cart
Order
```

Cada contexto deve possuir seu próprio modelo.

---

### Regra 3 — IDs entre contextos

Quando um contexto precisar referenciar outro:

```text
productId
customerId
orderId
```

deve preferencialmente armazenar o identificador, e não uma entidade JPA de outro contexto.

---

### Regra 4 — Comunicação explícita

A comunicação entre contextos deve passar por uma interface de aplicação ou evento.

Evitar dependências escondidas.

---

### Regra 5 — Eventos representam fatos

Um evento deve representar algo que aconteceu.

Exemplo:

```text
OrderConfirmed
```

e não:

```text
ConfirmOrder
```

O primeiro representa um fato.

O segundo representa um comando.

---

# 🚀 Roadmap

## Fase 1 — Monólito Modular

* [ ] Criar projeto Spring Boot
* [ ] Configurar PostgreSQL
* [ ] Configurar migrations
* [ ] Criar Identity Context
* [ ] Criar Customer Context
* [ ] Criar Catalog Context
* [ ] Criar Inventory Context
* [ ] Criar Cart Context
* [ ] Criar Order Context
* [ ] Criar Notification Context
* [ ] Criar Audit Context

## Fase 2 — Eventos

* [ ] Definir eventos de domínio
* [ ] Implementar eventos internos
* [ ] Introduzir Kafka
* [ ] Criar producers
* [ ] Criar consumers
* [ ] Implementar consumer groups
* [ ] Implementar retry
* [ ] Implementar Dead Letter Topic
* [ ] Estudar idempotência

## Fase 3 — Consistência e concorrência

* [ ] Concorrência no estoque
* [ ] Optimistic Locking
* [ ] Pessimistic Locking
* [ ] Transações
* [ ] Isolation Levels
* [ ] Processamento duplicado
* [ ] Idempotência
* [ ] Outbox Pattern

## Fase 4 — Microserviços

Separar gradualmente:

```text
identity-service
customer-service
catalog-service
inventory-service
cart-service
order-service
notification-service
audit-service
```

Cada serviço deverá possuir seu próprio banco de dados quando fizer sentido.

## Fase 5 — Resiliência

* [ ] Timeout
* [ ] Retry
* [ ] Circuit Breaker
* [ ] Rate Limiting
* [ ] Fallback
* [ ] Dead Letter Queue/Topic

## Fase 6 — Observabilidade

* [ ] Structured Logging
* [ ] Correlation ID
* [ ] Actuator
* [ ] Micrometer
* [ ] Prometheus
* [ ] Grafana
* [ ] OpenTelemetry
* [ ] Distributed Tracing

## Fase 7 — Escalabilidade

* [ ] Redis
* [ ] Cache Aside
* [ ] API Gateway
* [ ] Load Balancing
* [ ] Horizontal Scaling
* [ ] Database Read Replica
* [ ] Kubernetes

---

# 🛠️ Stack

### Backend

* Java 21
* Spring Boot 4
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Messaging

* Apache Kafka

### Infraestrutura

* Docker
* Docker Compose

### Futuro

* Redis
* Prometheus
* Grafana
* OpenTelemetry
* Kubernetes

---

# 📚 Objetivo acadêmico/prático

Este projeto não tem como objetivo apenas construir um e-commerce.

Cada evolução deverá responder a uma pergunta de engenharia.

Exemplos:

> Como controlar concorrência no estoque?

> O que acontece se uma mensagem Kafka for processada duas vezes?

> Como garantir que um pedido não seja criado duas vezes?

> Como manter consistência entre Order e Inventory?

> O que acontece se o Notification Service estiver indisponível?

> Como realizar uma transação que envolve múltiplos serviços?

> Quando utilizar comunicação síncrona?

> Quando utilizar comunicação assíncrona?

> Como rastrear uma requisição passando por vários serviços?

> Como escalar apenas o componente que está sofrendo maior carga?

O objetivo final é que o projeto demonstre não apenas **código**, mas também as decisões e trade-offs envolvidos na construção de sistemas distribuídos.

---

# 📌 Princípio do projeto

> **Começar simples. Medir os problemas. Evoluir a arquitetura quando houver uma razão técnica para isso.**

O sistema deve nascer como um monólito modular bem estruturado e, gradualmente, evoluir para uma arquitetura distribuída baseada nos problemas que forem introduzidos durante o desenvolvimento.
