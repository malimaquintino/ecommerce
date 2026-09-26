CREATE TABLE identity.permissions (
    id          BIGSERIAL    PRIMARY KEY,
    permission  VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

COMMENT ON TABLE  identity.permissions             IS 'Permissões granulares do sistema — contexto Identity';
COMMENT ON COLUMN identity.permissions.permission  IS 'Chave da permissão, ex: USER_CREATE, ORDER_VIEW';
COMMENT ON COLUMN identity.permissions.description IS 'Descrição legível da permissão';
