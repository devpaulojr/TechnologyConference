-- create type enums
CREATE TYPE room_type AS ENUM('NORMAL', 'VIP');
CREATE TYPE room_status AS ENUM('DISPONIVEL', 'EM_MANUTENCAO', 'RESERVADO', 'OCUPADO');

CREATE TABLE company (
    id_company UUID PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    address VARCHAR(80) NOT NULL,
    neighborhood VARCHAR(80) NOT NULL,
    city VARCHAR(80) NOT NULL,
    state VARCHAR(2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id_user UUID PRIMARY KEY,
    id_company UUID,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    confirm_password VARCHAR(100) NOT NULL,
    phone_number VARCHAR(11) NOT NULL UNIQUE,
    vip BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (id_company) REFERENCES company(id_company)
);

CREATE TABLE room (
    id_room UUID PRIMARY KEY,
    company_id UUID NOT NULL,
    number_rooms INTEGER NOT NULL UNIQUE,
    seat_capacity INTEGER DEFAULT 0 NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE NOT NULL,
    room_type room_type NOT NULL,
    room_status room_status NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(id_company)
);

CREATE TABLE presentation (
    id_presentation UUID PRIMARY KEY,
    id_room UUID NOT NULL,
    name VARCHAR(80) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_room FOREIGN KEY (id_room) REFERENCES room(id_room)
);
