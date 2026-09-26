CREATE TABLE identity.roles (
    id   BIGSERIAL   PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE  identity.roles      IS 'Roles de acesso disponíveis no sistema — contexto Identity';
COMMENT ON COLUMN identity.roles.name IS 'Nome da role: MASTER, ADMINISTRATIVE, INVENTORY, etc.';

INSERT INTO identity.roles (name) VALUES
    ('MASTER'),
    ('ADMINISTRATIVE'),
    ('INVENTORY');
