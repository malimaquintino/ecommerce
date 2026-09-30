CREATE TABLE identity.login_attempts (
    id          BIGSERIAL    PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    success     BOOLEAN      NOT NULL,
    ip_address  VARCHAR(45),
    attempted_at TIMESTAMP   NOT NULL DEFAULT now(),

    CONSTRAINT fk_login_attempts_user FOREIGN KEY (user_id) REFERENCES identity.users (id)
);

COMMENT ON TABLE  identity.login_attempts              IS 'Registro de tentativas de login — contexto Identity';
COMMENT ON COLUMN identity.login_attempts.success      IS 'true = login bem-sucedido, false = falhou';
COMMENT ON COLUMN identity.login_attempts.ip_address   IS 'IP do cliente no momento da tentativa';
