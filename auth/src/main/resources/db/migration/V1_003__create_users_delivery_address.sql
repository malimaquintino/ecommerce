create sequence if not exists delivery_addresses_sequence start 1 increment 1;

create table if not exists delivery_addresses (
    id bigint not null primary key default nextval('delivery_addresses_sequence'),
    user_id BIGINT NOT NULL,
    recipient_name VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(20) NOT NULL,
    complement VARCHAR(100),
    neighborhood VARCHAR(100),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) DEFAULT 'Brasil',
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_delivery_address FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);