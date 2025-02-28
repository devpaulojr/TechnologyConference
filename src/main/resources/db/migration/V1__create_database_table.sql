CREATE TABLE users(
    id_user UUID PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(80) NOT NULL,
    confirm_password VARCHAR(80) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    vip BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE company(
    id_company UUID PRIMARY KEY,
    id_user UUID NOT NULL,
    name VARCHAR(80) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    address VARCHAR(80) NOT NULL,
    neighborhood VARCHAR(80) NOT NULL,
    city VARCHAR(80) NOT NULL,
    state VARCHAR(2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users(id_user)
);

CREATE TABLE room(
    id_room UUID PRIMARY KEY,
    number_rooms INTEGER NOT NULL UNIQUE,
    vip_rooms BOOLEAN DEFAULT FALSE NOT NULL,
    seat_capacity INTEGER DEFAULT 0 NOT NULL
);

CREATE TABLE presentation(
    id_presentation UUID PRIMARY KEY,
    id_company UUID NOT NULL,
    id_room UUID NOT NULL,
    name VARCHAR(80) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (id_company) REFERENCES company(id_company),
    CONSTRAINT fk_room FOREIGN KEY (id_room) REFERENCES room(id_room)
);

CREATE TABLE presentation_ticket(
    id_presentation_ticket UUID PRIMARY KEY,
    id_presentation UUID NOT NULL,
    id_company UUID NOT NULL,
    id_room UUID NOT NULL,
    vip_ticket BOOLEAN DEFAULT FALSE NOT NULL,
    validation_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_presentation FOREIGN KEY (id_presentation) REFERENCES presentation(id_presentation)
);