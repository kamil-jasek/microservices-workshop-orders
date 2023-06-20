create table orders
(
    id            uuid primary key,
    customer_id   uuid                     not null,
    create_time   timestamp with time zone not null,
    status        text                     not null,
    currency      varchar(3)               not null,
    discount      money                    not null,
    delivery_cost money                    not null,
    sent_time     timestamp with time zone,
    cancel_time   timestamp with time zone,
    cancel_reason text
);

create table order_items
(
    id                uuid primary key,
    order_id          uuid references orders (id),
    product_id        uuid       not null,
    original_price    money      not null,
    original_currency varchar(3) not null,
    exchanged_price   money      not null,
    weight            float      not null,
    weight_unit       varchar(2) not null,
    quantity          int        not null
);
