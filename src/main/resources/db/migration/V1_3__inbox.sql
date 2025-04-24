-- From a performance standpoint there should be one inbox table per aggregate that
-- will reduce the number of change feeds per range.

create table if not exists inbox
(
    id             uuid as ((payload ->> 'id')::UUID) stored,
    aggregate_type varchar(32) not null,
    payload        jsonb       not null,

    primary key (id)
);

alter table inbox set (ttl_expire_after = '1 hour');