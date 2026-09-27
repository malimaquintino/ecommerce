CREATE SCHEMA IF NOT EXISTS identity;

CREATE TABLE identity.users (
    id       BIGSERIAL    PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    document VARCHAR(20)  NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

COMMENT ON SCHEMA identity              IS 'Schema do contexto Identity';
COMMENT ON TABLE  identity.users        IS 'Usuários do sistema — contexto Identity';
COMMENT ON COLUMN identity.users.document IS 'CPF ou CNPJ do usuário';
