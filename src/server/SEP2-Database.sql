DROP SCHEMA If EXISTS sep2database cascade;
create schema sep2database;
set schema 'sep2database';


CREATE TABLE Room
(
    id          serial PRIMARY KEY,
    room_no           varchar(10),
    floor            int,
    building_address varchar(70),
    number_of_seats  int
);

CREATE TABLE Room_Equipment
(
    room_ID   int,
    equipment varchar(50),
    PRIMARY KEY (room_ID, equipment),
    FOREIGN KEY (room_ID) references sep2database.Room (id)
);

CREATE TABLE Employee
(
    id          serial PRIMARY KEY,
    username    varchar(20),
    surname     varchar(30),
    name        varchar(30),
    password    varchar(64),
    role        varchar(20)
);

CREATE TABLE Permission
(
    employee_ID int,
    permission  varchar(100),
    PRIMARY KEY (employee_ID, permission),
    FOREIGN KEY (employee_ID) references Employee (id)
);

CREATE TABLE Message_Room
(
    id              serial PRIMARY KEY,
    name            varchar(30),
    is_private       bool
);

CREATE TABLE Message
(
    message_room_ID int,
    user_ID         int,
    timestamp       bigint,
    message         varchar(1000),
    FOREIGN KEY (message_room_ID) references Message_Room (id),
    FOREIGN KEY (user_ID) references Employee (id)
);

CREATE TABLE Message_Room_Participant
(
    message_room_ID int,
    employee_ID     int,
    PRIMARY KEY (message_room_ID, employee_ID),
    FOREIGN KEY (message_room_ID) references Message_Room (id),
    FOREIGN KEY (employee_ID) references Employee (id)
);

CREATE TABLE Event
(
    id              serial PRIMARY KEY,
    room_ID         int,
    creator         int,
    message_room_ID int,
    create_time     bigint,
    start_time      bigint,
    end_time        bigint,
    title           varchar(50),
    description     varchar(200),
    is_online       bool,
    platform        varchar(15),
    url             varchar(200),
    FOREIGN KEY (room_ID) references Room (id),
    FOREIGN KEY (creator) references Employee (id),
    FOREIGN KEY (message_room_ID) references Message_Room (id)
);

CREATE TABLE Event_Participant
(
    event_ID    int,
    employee_ID int,
    PRIMARY KEY (event_ID, employee_ID),
    FOREIGN KEY (event_ID) references Event (id),
    FOREIGN KEY (employee_ID) references Employee (id)
);

