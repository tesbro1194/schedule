1. 스파르타 강의를 바탕으로 `복사 + 붙여놓기` 하면서 과제를 하고 있습니다.

과제 요구 사항에 따라 붙여놓은 코드를 수정하고, 거기에 필요한 부분을 제가 추가하고, 모르는 부분은 ChatGPT에게 물어보고 있습니다.

ChatGPT를 쓸 때는 코드 흐름(진행 방향 및 설계)는 제가 정했는데, 구체적인 구현 코드가 떠오르지 않을 때 입니다.

이러한 방식으로 해도 되는지 궁금합니다.

코드를 보면서 해당 코드가 무슨 의미인지는 아는 수준 입니다.


2. 레벨 별로 순차적으로 과제를 수행하면서, 바꿔야되는 부분이 많았습니다.

수정이 필요한 부분을 놓치고 지나가기도 해서 나중에야 문제를 발견하고 해결했습니다.

추가 요구 사항에 따른 수정 사항을 놓치지 않을 방법이나 요령이 있는지 궁금합니다.



![3](https://github.com/user-attachments/assets/2c67f20a-8628-41d3-8fe0-43c80df6194e)



![2](https://github.com/user-attachments/assets/324af811-339d-4e0d-b38e-5eb5d98af029)
![1](https://github.com/user-attachments/assets/0afd7533-dfda-4215-99da-d45995ab21ca)

![ERD](https://github.com/user-attachments/assets/c84b48ef-cca8-49e5-8230-a0cfdb69bf07)

테이블 생성 쿼리문:

    create table comment (
        id bigint not null auto_increment,
        created_at datetime(6),
        modified_at datetime(6),
        contents varchar(255),
        username varchar(255),
        plan_id bigint,
        primary key (id)
    );

    create table plan (
        id bigint not null auto_increment,
        created_at datetime(6),
        modified_at datetime(6),
        contents varchar(255),
        title varchar(255),
        weather varchar(255),
        user bigint,
        primary key (id)
    );

    create table sharer (
        id bigint not null auto_increment,
        plan_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table user (
        id bigint not null auto_increment,
        created_at datetime(6),
        modified_at datetime(6),
        email varchar(255) not null,
        password varchar(255) not null,
        role enum ('ROLE_ADMIN','ROLE_USER') not null,
        username varchar(255) not null,
        primary key (id)
    );

    alter table user 
       drop index UKob8kqyqqgmefl0aco34akdtpe;
       
    alter table user 
       add constraint UKob8kqyqqgmefl0aco34akdtpe unique (email);

    alter table user 
       drop index UKsb8bbouer5wak8vyiiy4pf2bx;

    alter table user 
       add constraint UKsb8bbouer5wak8vyiiy4pf2bx unique (username);

    alter table comment 
       add constraint FKp18l0cr84nk867f6s6mjhnosr 
       foreign key (plan_id) 
       references plan (id)

       alter table plan 
       add constraint FKgjr1ht0x609xg03kqg8yiy7u8 
       foreign key (user) 
       references user (id);

    alter table sharer 
       add constraint FKenfwq7kmb9a48swbh0r8i1gif 
       foreign key (plan_id) 
       references plan (id);

    alter table sharer 
       add constraint FKt2lii2n1t600lu5yrwcnjacle 
       foreign key (user_id) 
       references user (id);
