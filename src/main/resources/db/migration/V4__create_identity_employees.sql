CREATE TABLE identity.employees (
    id           BIGSERIAL    PRIMARY KEY,
    user_id      BIGINT       NOT NULL UNIQUE,
    employee_code UUID        NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    status       SMALLINT     NOT NULL DEFAULT 1,
    created_at   TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT now(),

    CONSTRAINT fk_employees_user FOREIGN KEY (user_id) REFERENCES identity.users (id)
);

COMMENT ON TABLE  identity.employees               IS 'Funcionários do sistema — contexto Identity';
COMMENT ON COLUMN identity.employees.employee_code IS 'Identificador público do funcionário (UUID)';
COMMENT ON COLUMN identity.employees.status        IS 'Status do funcionário: 1 = ativo, 0 = inativo';
