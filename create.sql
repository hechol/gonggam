
    create table board_entity (
        id bigint not null auto_increment,
        reg_time datetime(6),
        update_time datetime(6),
        writer_id bigint not null,
        title varchar(500),
        content varchar(2000),
        category enum ('gonggam','qna','request'),
        primary key (id)
    ) engine=InnoDB;

    create table member (
        id bigint not null,
        reg_time datetime(6),
        update_time datetime(6),
        member_id varchar(255),
        name varchar(255),
        oauth_id varchar(255),
        password varchar(255),
        resource_server_name varchar(255),
        role enum ('ADMIN','GUEST','USER'),
        primary key (id)
    ) engine=InnoDB;

    create table member_seq (
        next_val bigint
    ) engine=InnoDB;

    insert into member_seq values ( 1 );

    alter table board_entity 
       add constraint FK573pdlyjihtmoa9rpwbuc2nvl 
       foreign key (writer_id) 
       references member (id);
