create table domain_events
(
    event_id       uuid primary key,
    event_time     timestamp with time zone not null,
    correlation_id uuid,
    name           text      not null,
    data           jsonb     not null
);
