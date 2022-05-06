create table category
(
    category_id   int auto_increment
        primary key,
    category_name varchar(50)  null,
    description   varchar(100) null,
    created_at    datetime(6)  null,
    updated_at    datetime(6)  null,
    constraint category_category_id_uindex
        unique (category_id)
);

create table orders
(
    order_id           binary(16)  not null primary key,
    order_status       varchar(50) not null,
    created_at         datetime(6) null,
    order_table_number int         not null,
    constraint order_order_id_uindex
        unique (order_id)
);

create table product
(
    product_id   int auto_increment primary key,
    product_name varchar(50) not null,
    description  varchar(50) null,
    price        int         not null,
    category_id  int         not null,
    updated_at   datetime(6) null,
    created_at   datetime(6) null,
    constraint product_product_id_uindex
        unique (product_id),
    constraint product_product_name_uindex
        unique (product_name),
    constraint product_category_id_fk
        foreign key (category_id) references category (category_id)
)
    comment '음식 메뉴';

create table order_item
(
    order_id   binary(16)  null,
    product_id int         null,
    quantity   int         null,
    created_at datetime(6) null,
    constraint order_item_order_id_fk
        foreign key (order_id) references orders (order_id),
    constraint order_item_product_id_fk
        foreign key (product_id) references product (product_id)
)
    comment '주문별 제품 목록';

