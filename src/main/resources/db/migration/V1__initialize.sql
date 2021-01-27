drop table if exists users;
create table users (
                       id INT IDENTITY PRIMARY KEY not null,
                       nm_name VARCHAR(30) not null UNIQUE,
                       nm_password VARCHAR(80) not null,
                       nm_email  VARCHAR(50) UNIQUE,
                       nm_first_name VARCHAR(50),
                       nm_second_name VARCHAR(50)
);

insert into users (id, nm_name, nm_password, nm_first_name, nm_second_name, nm_email)
values
(1, '111','$2a$10$CYE6o4lcq27hDz0NCqQL9OZMn2v2OArA2sddxcMVDBNpxMXZhSkWi','Сергей','Фролов','s.frolov@gmail.com'), --пароль 111
(2, '222','$2a$10$wU/9J7LTMBopI2YCfJ.05uUhKsOvMmHrisReMQdgs8uQdaInAEoiC','Андрей','Иванов','a.ivanov@gmail.com'), --пароль 222
(3, '333','$2a$10$wIc7SDbFMiC2reLgmaWXkunr9sTkVoqS29NQyvhRuyuIG3SaDO4Di','Иван','Сергеев','i.sergeev@gmail.com'); --пароль 333

drop table if exists balance;
create table balance(
    id INT IDENTITY not null,
    id_user INT not null,
    nn_balance float,
    PRIMARY KEY (id),
    FOREIGN KEY (id_user)
    REFERENCES users (id),
);

insert into balance(id, id_user, nn_balance)
values (1,1,123.56),
       (2,2,345.53),
       (3,3,456.12);