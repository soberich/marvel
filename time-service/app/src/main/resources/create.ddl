
    SET_CONNECTION_PROPERTY AutoBatchDdlOperations=true

    create table employees (
       id  INT64 not null,
        email STRING(255) not null,
        name STRING(255) not null,
        primary key (id)
    )

    create table employees_seq (
       next_val INT64
    )

    insert into employees_seq values ( 1 )

    create table projects (
       name STRING(50) not null,
        primary key (name)
    )

    create table record_collections (
       id INT64 not null,
        month STRING(255) not null,
        year BYTES(255) not null,
        employee_id  INT64 not null,
        project_name STRING(50) not null,
        primary key (id)
    )

    create table record_collections_seq (
       next_val INT64
    )

    insert into record_collections_seq values ( 1 )

    create table records (
       date DATE not null,
        type STRING(255) not null,
        desc STRING(255),
        hours_submitted FLOAT64,
        record_collection_id  INT64 not null,
        primary key (date, record_collection_id, type)
    )
create index IDX_employees_with_email_name on employees (email asc, name desc)
create unique index UK_employees_with_email on employees (email)
create unique index UK_employees_with_name on employees (name)

    EXECUTE_DDL_BATCH

    RESET_CONNECTION_PROPERTY AutoBatchDdlOperations
