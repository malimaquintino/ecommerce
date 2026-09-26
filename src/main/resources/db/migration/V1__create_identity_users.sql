CREATE SCHEMA IF NOT EXISTS identity;

CREATE TABLE identity.users (
    id         BIGSERIAL    PRIMARY KEY,
    user_code  UUID         NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    nome       VARCHAR(255) NOT NULL,
    documento  VARCHAR(20)  NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    senha      VARCHAR(255) NOT NULL,
    status     VARCHAR(30)  NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at TIMESTAMP    NOT NULL DEFAULT now()
);

COMMENT ON SCHEMA identity                 IS 'Schema do contexto Identity';
COMMENT ON TABLE  identity.users           IS 'Usuários do sistema — contexto Identity';
COMMENT ON COLUMN identity.users.user_code IS 'Identificador público do usuário (UUID)';
COMMENT ON COLUMN identity.users.documento IS 'CPF ou CNPJ do usuário';
COMMENT ON COLUMN identity.users.status    IS 'Status do usuário: ACTIVE, INACTIVE, BLOCKED';
