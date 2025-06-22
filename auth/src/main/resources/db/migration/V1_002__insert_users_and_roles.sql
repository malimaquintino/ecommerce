INSERT INTO roles (id, name, description)
VALUES
    (1, 'ADMIN', 'Administrador geral do sistema'),
    (2, 'CUSTOMER', 'Cliente do e-commerce'),
    (3, 'FINANCIAL_ADMIN', 'Administrador financeiro');
ALTER SEQUENCE roles_sequence RESTART WITH 4;

INSERT INTO users (id, document, name, email, password, type, enabled, created_at, updated_at)
VALUES
    (1, '000000000000', 'Admin', 'admin@ecommerce.com', '123456', 'ADMIN', true, now(), now()),
    (2, '123456789101', 'Matheus Quintino', 'matheusquintino@ecommerce.com', '123456', 'CUSTOMER', true, now(), now());
ALTER SEQUENCE users_sequence RESTART WITH 3;

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);