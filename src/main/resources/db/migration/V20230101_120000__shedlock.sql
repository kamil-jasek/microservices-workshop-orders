create table shedlock
(
    name       text      not null,
    lock_until timestamp not null,
    locked_at  timestamp not null,
    locked_by  text      not null,
    primary key (name)
);
