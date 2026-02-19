create table applications ( app_code varchar(50) primary key, app_description varchar(255) , app_name varchar(100));
create table modules (module_code varchar(50) primary key, description varchar(255) , module_name varchar(100));
create table app_modules(id bigint primary key AUTO_INCREMENT, app_code varchar(50), module_code varchar(50));


insert into applications(app_code,app_description,app_name) values ('PRJMGMT','Momentus Projects', 'Project Management') on duplicate key update app_code = app_code;
insert into modules (module_code,description,module_name) values ('ADM','ADMIN','ADMIN')  on duplicate key update module_code = module_code;
insert into modules (module_code,description,module_name) values ('ASMT','Asset Management','Asset Management')  on duplicate key update module_code = module_code;
insert into modules (module_code,description,module_name) values ('PRJ','Project Management','Project Management')  on duplicate key update module_code = module_code;
insert into modules (module_code,description,module_name) values ('LTMMT','Leave and Time Management','Leave and Time Management') on duplicate key update module_code =module_code;
insert into modules (module_code,description,module_name) values ('KNCT','Knowledge Centre','Knowledge Centre')  on duplicate key update module_code = module_code;
insert into modules (module_code,description,module_name) values ('ORCT','Orchestration Centre','Orchestration Centre')  on duplicate key update module_code = module_code;
insert into modules (module_code,description,module_name) values ('FIN','Finance Administration','Finance Administration')  on duplicate key update module_code = module_code;





insert into app_modules(id,app_code,module_code) values (1,'PRJMGMT','ADM') on duplicate key update id = id;
insert into app_modules(id,app_code,module_code) values (2,'PRJMGMT','ASMT') on duplicate key update id = id;
insert into app_modules(id,app_code,module_code) values (3,'PRJMGMT','LTMMT') on duplicate key update id = id;
insert into app_modules(id,app_code,module_code) values (4,'PRJMGMT','KNCT') on duplicate key update id = id;
insert into app_modules(id,app_code,module_code) values (5,'PRJMGMT','ORCT') on duplicate key update id = id;
insert into app_modules(id,app_code,module_code) values (5,'PRJMGMT','PRJ') on duplicate key update id = id;



 create table module_access_codes (
        access_code varchar(50) PRIMARY KEY,
        description varchar(255),
        module_code varchar(50) not null
  );
--         foreign key (sector_code)  references sector(code)

insert into module_access_codes (access_code,description,module_code) values ('ADM-ADM','Administration access to manage users , roles and company','ADM') on duplicate key update access_code = access_code;

insert into module_access_codes (access_code,description,module_code) values ('ASMT-ADM','Create and Manage assets','ASMT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('ASMT-RQST','Request and raise support tickets','ASMT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('ASMT-AUD','Audit assets','ASMT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('ASMT-RSLV','Resolve support tickets','ASMT') on duplicate key update access_code = access_code;

insert into module_access_codes (access_code,description,module_code) values ('LTMMT-ADM','Administer Leave policies','LTMMT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('LTMMT-RQLV','Request Leaves and time entry','LTMMT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('LTMMT-APLV','Approve Leaves and timesheet','LTMMT') on duplicate key update access_code = access_code;

insert into module_access_codes (access_code,description,module_code) values ('PRJ-ADM','Manage Projects','PRJ') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('PRJ-LD','Lead Projects, plan and assign ','PRJ') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('PRJ-TM','Create and execute work items','PRJ') on duplicate key update access_code = access_code;

insert into module_access_codes (access_code,description,module_code) values ('KNCT-ADM','Manage knowledge center topics','KNCT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('KNCT-LD','Collaborate on knowledge centre ','KNCT') on duplicate key update access_code = access_code;

insert into module_access_codes (access_code,description,module_code) values ('ORCT-ADM','Configure Alerts and Exceptions','ORCT') on duplicate key update access_code = access_code;
insert into module_access_codes (access_code,description,module_code) values ('ORCT-RCV','Configure Alerts and Exceptions','ORCT') on duplicate key update access_code = access_code;



create table industry (
code varchar(20) PRIMARY KEY ,
name varchar(255) NOT NULL,
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
last_updated_time datetime(6) DEFAULT NULL,
version bigint DEFAULT 0
);

create table sector (
code varchar(20) PRIMARY KEY ,
name varchar(255) NOT NULL,
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
last_updated_time datetime(6) DEFAULT NULL,
version bigint DEFAULT 0
);



CREATE TABLE organization (
  id bigint   PRIMARY KEY  AUTO_INCREMENT,
  org_code varchar(100) NOT NULL ,
  organization_name varchar(255) NOT NULL,
  address1 varchar(255) NOT NULL,
  address2 varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  state varchar(255) DEFAULT NULL,
  zip_code varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  email varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  primary_contact varchar(255) DEFAULT NULL,
  industry_code varchar(20) DEFAULT NULL,
  sector_code  varchar(20) DEFAULT NULL,
  active tinyint(1) DEFAULT '1',
  registration_date DATE DEFAULT NULL,
  supplimentary_info BLOB DEFAULT NULL,
  trial_period tinyint(1) DEFAULT '0',
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
  foreign key (industry_code)  references industry(code),
  foreign key (sector_code)  references sector(code)

);

insert into industry(code, name ) VALUES ('ALL','ALL')  on duplicate key update code = code;
insert into sector(code, name ) VALUES ('ALL','ALL') on duplicate key update code = code;
insert into sector(code, name ) VALUES ('IT','Information Technology Consultancy ') on duplicate key update code = code;
insert into sector(code, name ) VALUES ('MNF','Manufacturing') on duplicate key update code = code;
insert into sector(code, name ) VALUES ('CND','Construction & Development') on duplicate key update code = code;
insert into organization(id,org_code,organization_name,address1,email,phone) values (1,'momentuz','Momentuz Pvt Ltd','Bhive ','admin@momentuz.com','988497723') on duplicate key update id=id;


CREATE TABLE users(
  user_id varchar(255) PRIMARY KEY,
  org_id bigint NOT NULL,
  email varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  phone varchar(255) NOT NULL,
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
  org_owner tinyint(1) DEFAULT 0,
  system_created tinyint(1) DEFAULT 0,
  foreign key(org_id) references organization(id)

);
insert into users (user_id,org_id,email,first_name,last_name,password,phone,org_owner,system_created) values
 ('admin@momentuz.com',1,'admin@momentuz.com','Admin','Momentus','$2a$04$bKqtQw1pECimXJtGnZ7l9uwajLLHBHGWf5HuKXHwxbP0lJ05ZaqWe','908889',true,true);

CREATE TABLE roles (
  id bigint   PRIMARY KEY  AUTO_INCREMENT,
  org_id bigint NOT NULL,
  access_codes blob,
  title varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
  foreign key(org_id) references organization(id)
);

insert into roles (id,org_id,access_codes,description,title) values (1,1,'ADM-ADM','Admin','Admin');

CREATE TABLE user_roles (
  id bigint PRIMARY KEY  AUTO_INCREMENT ,
  role_id bigint NOT NULL,
  org_id bigint NOT NULL,
  user_id varchar(255) NOT NULL,
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
   version bigint DEFAULT 0,
  foreign key (role_id) references roles (id),
  foreign key (user_id) references users(user_id),
  foreign key(org_id) references organization(id)
);

insert into user_roles(id,org_id,role_id,user_id) values (1,1,1,'admin@momentuz.com');

create table profile_group(
profile_group_code varchar(100) primary key,
profile_group_description  varchar(255) NOT NULL,
active tinyint(1) DEFAULT '1',
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
 last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0
);


create table entity (
entity_name varchar(100) PRIMARY KEY,
entity_display varchar(100),
full_package varchar(255) not null,
profile_group_code varchar(100),
active tinyint(1) DEFAULT '1',
support_import tinyint(1) DEFAULT '1',
sequence numeric(4),
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
 last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
foreign key (profile_group_code) references profile_group(profile_group_code)
);



create table profile(
profile_code varchar(255) PRIMARY KEY,
profile_description varchar(255),
parent_profile_code varchar(255),
full_profile_code varchar(255),
parent_profile_id bigint,
profile_group_code varchar(100) ,
active tinyint(1) DEFAULT '1',
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
 last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
 foreign key (profile_group_code) references profile_group(profile_group_code)
);




create table finite_group(
group_code varchar(100) primary key,
group_name varchar(255)
);

create table finite_value(
fv_code varchar(100) primary key,
fv_value varchar(255),
group_code varchar(100),
foreign key  (group_code) references finite_group(group_code)
);


insert into finite_group(group_code,group_name) values ('nextup_comp','Nextup Components');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_dt','Date','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_prfx','Entity Prefix','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_BK','Parent Object Business Key','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_seq','Sequence','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_comp1','Component 1','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_comp2','Component 2','nextup_comp');
insert into finite_value(fv_code,fv_value,group_code) values ('nxtup_comp3','Component 3','nextup_comp');


create table nextup_config(
 id varchar(100) primary key ,
 profile_code varchar(255),
 entity varchar(255),
 field_1 varchar(100),
 field_2 varchar(100),
 field_3 varchar(100),
 field_4 varchar(100),
 field_5 varchar(100),
 date_format varchar(100),
 reset_seq_per_day tinyint(1),
 prefix varchar(20),
 sequence_width Numeric(3),
 foreign key (profile_code) references profile (profile_code),
 foreign key (field_1) references finite_value (fv_code),
 foreign key (field_2) references finite_value (fv_code),
 foreign key (field_3) references finite_value (fv_code),
 foreign key (field_4) references finite_value (fv_code),
 foreign key (field_5) references finite_value (fv_code)
) ;


    create table menu_group (
        id varchar(100) not null,
        menu_key varchar(255),
        menu_set_id varchar(100) ,
        access_code varchar(255),
        seq_no NUMERIC(10,2),
        primary key (id)
    ) ;
    create table menu_item (
        id varchar(100) not null ,
        menu_key varchar(255),
        access_code varchar(255),
        page varchar(255),
        menu_group_id varchar(100) ,
        seq_no NUMERIC(10,2),
        primary key (id)
    ) ;
    create table menu_set (
        id varchar(100) not null ,
        created_by varchar(255),
        created_time datetime(6),
        deleted BOOLEAN DEFAULT FALSE,
        last_updated_by varchar(255),
        last_updated_time datetime(6),
        version bigint,
        profile_code varchar(255),
        description varchar(255),
        primary key (id)
    ) ;

    alter table menu_group
       add constraint FKrhs5tphmdqtb9pax9qv4byslb
       foreign key (menu_set_id)
       references menu_set (id);

    alter table menu_item
       add constraint FKop2h6vibgw1xhscv0602litss
       foreign key (menu_group_id)
       references menu_group (id) ;

    alter table menu_set
       add constraint FKitfu1nkb9m9opksviepldll9a
       foreign key (profile_code)
       references profile (profile_code) ;






create table list_metadata (
        id varchar(100) not null,
        created_by varchar(255),
        created_time datetime(6),
        deleted BOOLEAN DEFAULT FALSE,
        last_updated_by varchar(255),
        last_updated_time datetime(6),
        version bigint,
        profile_code varchar(255),
        description varchar(255),
        entity varchar(255),
        primary key (id)
    ) ;

create table list_columns (
        id varchar(100) not null,
        field_key varchar(255),
        accessor varchar(255),
        list_metadata_id varchar(100),
        seq_no NUMERIC(10,2),
        primary key (id)
    ) ;

    create table filter_field (
            id varchar(100) not null,
            control varchar(255),
            field_key varchar(255),
            param varchar(255),
            accessor varchar(255),
            list_metadata_id varchar(100),
            seq_no NUMERIC(10,2),
            primary key (id)
        ) ;

    create table list_buttons (
            id varchar(100) not null,
            button_class varchar(255),
            inner_text varchar(255),
            js_method varchar(255),
            list_metadata_id varchar(100),
            seq_no NUMERIC(10,2),
            primary key (id)
        ) ;



alter table list_metadata
       add constraint FKmvjb81uqom19b0ugawohdhlph
       foreign key (profile_code)
       references profile (profile_code);


alter table list_columns
      add constraint FKfsbbi7kn7xc5eijun59n4oje6
      foreign key (list_metadata_id)
      references list_metadata (id);

  alter table filter_field
         add constraint FKmv2aqkv45d1ke2mytuu51stto
         foreign key (list_metadata_id)
         references list_metadata (id);

alter table list_buttons
       add constraint FKpp8qgcfilmv5jdnhipkhlu8cp
       foreign key (list_metadata_id)
       references list_metadata (id) ;


    create table updateview_buttons (
        id varchar(255) not null,
        button_class varchar(255),
        inner_text varchar(255),
        js_method varchar(255),
        seq_no decimal(10,2),
        visibility varchar(20),
        updateview_metadata_id varchar(255),
        primary key (id)
    ) ;



    create table updateview_fields (
        id varchar(255) not null,
        accessor varchar(255),
        control varchar(255),
        field_key varchar(255),
        param varchar(750),
        seq_no decimal(10,2),
        visibility varchar(20),
        updateview_metadata_id varchar(255),
        data_type varchar(50),
        parent varchar(255),
        param1 varchar(750),
        style varchar(255),
        primary key (id)
    ) ;


    create table updateview_metadata (
        id varchar(255) not null,
        created_by varchar(255),
        created_time datetime(6),
        deleted BOOLEAN DEFAULT FALSE,
        last_updated_by varchar(255),
        last_updated_time datetime(6),
        version bigint,
        profile_code varchar(255),
        description varchar(255),
        entity varchar(255),
        js_file varchar(255),
        primary key (id)
    ) ;

    alter table updateview_buttons
       add constraint FKjue3hi6i7ula26p36crt285ue
       foreign key (updateview_metadata_id)
       references updateview_metadata (id);

    alter table updateview_fields
       add constraint FK8akf64il7p9rpsckqtf891p9b
       foreign key (updateview_metadata_id)
       references updateview_metadata (id) ;

    alter table updateview_metadata
       add constraint FKj7gpm63mwmdcgflxbakvv7y7a
       foreign key (profile_code)
       references profile (profile_code);


create table sector_profile(
id varchar(255) primary key,
sector varchar(255) ,
profile_code varchar(255),
foreign key (profile_code) references profile (profile_code)
);

create table primary_user_role (
title varchar(255) primary key,
role_description  varchar(255) ,
profile_code varchar(255) ,
access_codes blob,
is_primary tinyint(1),
foreign key (profile_code) references profile (profile_code)
);




insert into profile_group(profile_group_code,profile_group_description,created_by,created_time) values ('GNL','General','seed',now());
insert into profile(profile_code,profile_description,full_profile_code,profile_group_code,created_by,created_time) values ('ROOT','Base Profile','ROOT','GNL','seed',now());
insert into profile(profile_code,profile_description,full_profile_code,parent_profile_code,profile_group_code,created_by,created_time) values ('IT','IT Profile','ROOT-IT','ROOT','GNL','seed',now());


insert into sector_profile(id,sector,profile_code)values ('IT-IT','IT','IT');
insert into primary_user_role(title,role_description,profile_code,access_codes,is_primary) values ('Administrator System','Rights include to do any operation','IT','ADM-ADM,ASMT-RQST,ASMT-AUD,ASMT-RSLV,ASMT-ADM,LTMMT-ADM,LTMMT-RQLV,LTMMT-APLV,PRJ-ADM,PRJ-LD,PRJ-TM,KNCT-ADM,KNCT-LD,ORCT-ADM,ORCT-RCV',true);
insert into primary_user_role(title,role_description,profile_code,access_codes,is_primary) values ('Project Lead','Rights include to Manage Project and workforce','IT','ASMT-RQST,ASMT-AUD,ASMT-RSLV,ASMT-ADM,LTMMT-ADM,LTMMT-RQLV,LTMMT-APLV,PRJ-ADM,PRJ-LD,PRJ-TM,KNCT-ADM,KNCT-LD,ORCT-ADM,ORCT-RCV',false);
insert into primary_user_role(title,role_description,profile_code,access_codes,is_primary) values ('Project Member','Rights include basic workflows to create and execute work items and raise tickets','IT','ASMT-RQST,LTMMT-RQLV,PRJ-TM,KNCT-LD,ORCT-RCV',false);



insert into menu_set(id,profile_code,description) values ('IT_MNU','IT','IT Menu');
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-ADM','Administration','IT_MNU','ADM-ADM',1);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-TRNS','Asset Management','IT_MNU','ASMT-RQST',2);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-RPTS','Projects', 'IT_MNU','PRJ-TM',3);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-LVTM','Leave Time', 'IT_MNU','LTMMT-RQLV',4);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-KCT','Knowledge Centre', 'IT_MNU','KNCT-LDV',5);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('IT_MNU-RPT','Reports & Analytics', 'IT_MNU','PRJ-ADM',6);


insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-ADM-ORG','Organization','adm','./general/genericList.html?entity=Supplier','IT_MNU-ADM',1);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-ADM-RLS','Roles','adm','./general/genericaddview.html?entity=Role&mode=Edit','IT_MNU-ADM',2);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-ADM-LCT','Locations','adm','./general/genericList.html?entity=Item','IT_MNU-ADM',3);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-ADM-USRS','Users','adm','./general/dataImport.html','IT_MNU-ADM',4);


insert into updateview_metadata(id,profile_code,entity) values ('USPRF','ROOT','UserProfile');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-CRUPWD','currentPassword','password','Current Password',null,1,'E','USPRF','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-PWD','password','password','Password',null,2,'E','USPRF','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-CNFPWD','confirmPassword','password','Confirm Password',null,3,'E','USPRF','String');

insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USPRF-UPD','btn btn-primary','Update Password','onUpdate',1,'E','USPRF');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USPRF-CLS','btn btn-secondary','Close','onClose',1,'E','USPRF');






