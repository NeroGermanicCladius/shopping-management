create table tb_user
(
    id       bigserial     not null,
    name     varchar(255)  not null,
    type     varchar(10)   not null,
    email    varchar(255)  not null,
    password varchar(1023) not null,
    is_active boolean      not null,
    constraint tb_user_id_pk primary key (id),
    constraint tb_user_email_uc unique (email)
);

create table tb_product
(
    id    bigserial         not null,
    name  varchar(255)      not null,
    price decimal(19, 4)    not null,
    overview  varchar(255)  not null,

    constraint tb_product_id_pk primary key (id),
    constraint tb_product_name_uc unique (name)
);

create table tb_rating
(
    id          bigserial     not null,
    rate        integer       not null,
    user_id     varchar(10)   not null,
    product_id  varchar(255)  not null,
    constraint tb_rating_id_pk primary key (id),
    constraint tb_rating_user_id_fk foreign key (user_id) references tb_user (id),
    constraint tb_rating_product_id_fk foreign key (product_id) references tb_product (id)
);

create index tb_table_user_id_index on tb_rating (user_id);
create index tb_table_product_id_index on c_table (product_id);


create table tb_comment
(
    id              bigserial     not null,
    comment_text    varchar (255) not null,
    user_id         varchar(10)   not null,
    product_id      varchar(255)  not null,
    constraint tb_comment_id_pk primary key (id),
    constraint tb_comment_user_id_fk foreign key (user_id) references tb_user (id),
    constraint tb_comment_product_id_fk foreign key (product_id) references tb_product (id)
);