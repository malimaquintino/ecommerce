create sequence if not exists users_sequence start 1 increment 1;
create sequence if not exists roles_sequence start 1 increment 1;

create table if not exists users (
	id                  bigint not null primary key default nextval('users_sequence'),
	name                varchar(150) not null,
	email               varchar(150) not null,
	password            varchar(128) not null,
	type                varchar(10) not null,
	enabled             boolean,
    created_at          timestamp not null,
    updated_at          timestamp not null
);

create table if not exists roles (
	id                  bigint not null primary key default nextval('roles_sequence'),
	name                varchar(150) not null,
	description         varchar(255) not null
);

create table if not exists users_roles (
	user_id             bigint not null,
	role_id             bigint not null,
	constraint users_roles_pkey primary key (user_id, role_id),
	constraint users_roles_key_user_id foreign key (user_id) references users(id),
	constraint users_roles_key_role_id foreign key (role_id) references roles(id)
);
