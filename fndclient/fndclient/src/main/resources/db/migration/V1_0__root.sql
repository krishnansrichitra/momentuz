create table applications ( app_code varchar(50) primary key, app_description varchar(255) , app_name varchar(100));
create table modules (module_code varchar(50) primary key, description varchar(255) , module_name varchar(100));
create table app_modules(id bigint primary key AUTO_INCREMENT, app_code varchar(50), module_code varchar(50));


insert into applications(app_code,app_description,app_name) values ('FNDC','Foundation Client', 'Foundation Client');
insert into modules (module_code,description,module_name) values ('ADM','ADMIN','ADMIN') ;
insert into app_modules(id,app_code,module_code) values (1,'FNDC','ADM');

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
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
  foreign key (industry_code)  references industry(code),
  foreign key (sector_code)  references sector(code)

);

insert into industry(code, name ) VALUES ('ALL','ALL');
insert into sector(code, name ) VALUES ('ALL','ALL');
insert into organization(id,org_code,organization_name,address1,email,phone) values (1,'momentuz','Momentuz Pvt Ltd','Bhive ','admin@momentuz.com','988497723');


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
  foreign key(org_id) references organization(id)

);

insert into users (user_id,org_id,email,first_name,last_name,password,phone) values ('admin@momentuz.com',1,'admin@momentuz.com','Admin','Momentus','$2a$10$0w.63IKihn65p1s1hZS12Omx7KmjVB/Xp3nUJMMdN3urxqJAHB0cO','908889');

CREATE TABLE roles (
  id bigint   PRIMARY KEY  AUTO_INCREMENT,
  org_id bigint NOT NULL,
  access_codes blob,
  description varchar(255) DEFAULT NULL,
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
  foreign key(org_id) references organization(id)
);

insert into roles (id,org_id,access_codes,description) values (1,1,'adm','Admin');

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
full_package varchar(255) not null,
profile_group_code varchar(100),
active tinyint(1) DEFAULT '1',
support_import tinyint(1) DEFAULT '1',
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
 last_updated_time datetime(6) DEFAULT NULL,
  version bigint DEFAULT 0,
foreign key (profile_group_code) references profile_group(profile_group_code)
);



create table profile(
id bigint PRIMARY KEY  AUTO_INCREMENT ,
profile_code varchar(255) UNIQUE,
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

insert into finite_group(group_code,group_name) values ('uom_type','UOM Type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_WGHT','WEIGHT','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_NOS','NOs','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_VOLU','Volume','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_CRTN','Cartons','uom_type');

insert into finite_group(group_code,group_name) values ('item_group','Item Group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_merch','Merchandise Item','item_group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_raw','Raw Material','item_group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_inhs','In house Item','item_group');





    create table menu_group (
        id bigint not null auto_increment,
        menu_key varchar(255),
        menu_set_id bigint,
        access_code varchar(255),
        primary key (id)
    ) ;
    create table menu_item (
        id bigint not null auto_increment,
        menu_key varchar(255),
        access_code varchar(255),
        page varchar(255),
        menu_group_id bigint,
        primary key (id)
    ) ;
    create table menu_set (
        id bigint not null auto_increment,
        created_by varchar(255),
        created_time datetime(6),
        deleted BOOLEAN DEFAULT FALSE,
        last_updated_by varchar(255),
        last_updated_time datetime(6),
        version bigint,
        profile_code varchar(255),
        description varchar(255),
        profile_id bigint not null,
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
       foreign key (profile_id)
       references profile (id) ;






create table list_metadata (
        id bigint not null auto_increment,
        created_by varchar(255),
        created_time datetime(6),
        deleted BOOLEAN DEFAULT FALSE,
        last_updated_by varchar(255),
        last_updated_time datetime(6),
        version bigint,
        profile_code varchar(255),
        description varchar(255),
        entity varchar(255),
        profile_id bigint not null,
        primary key (id)
    ) ;

create table list_columns (
        id bigint not null,
        field_key varchar(255),
        accessor varchar(255),
        list_metadata_id bigint,
        primary key (id)
    ) ;

    create table filter_field (
            id bigint not null,
            control varchar(255),
            field_key varchar(255),
            param varchar(255),
            accessor varchar(255),
            list_metadata_id bigint,
            primary key (id)
        ) ;

    create table list_buttons (
            id bigint not null,
            button_class varchar(255),
            inner_text varchar(255),
            js_method varchar(255),
            list_metadata_id bigint,
            primary key (id)
        ) ;



alter table list_metadata
       add constraint FKmvjb81uqom19b0ugawohdhlph
       foreign key (profile_id)
       references profile (id);


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


insert into profile_group(profile_group_code,profile_group_description,created_by,created_time) values ('GNL','General','seed',now());
insert into profile(id,profile_code,profile_description,full_profile_code,profile_group_code,created_by,created_time) values (1,'ROOT','Base Profile','ROOT','GNL','seed',now());



insert into entity (entity_name,full_package,profile_group_code,created_by,created_time)
values ('Customer','com.momentus.fndclient.customer.model.Customer', 'GNL','seed',now());
insert into fndclient.entity(entity_name,full_package,profile_group_code,active) values
('Supplier','com.momentus.fndclient.supplier.model.Supplier','GNL',1);
insert into fndclient.entity(entity_name,full_package,profile_group_code,active) values
('Item','com.momentus.fndclient.item.model.Item','GNL',1);


insert into menu_set(id,profile_id,profile_code,description) values (1,1,'ROOT','Default Menu');
insert into menu_group(id,menu_key,menu_set_id,access_code) values(1,'Master',1,null);
insert into menu_group(id,menu_key,menu_set_id,access_code) values(2,'Transactions',1,null);
insert into menu_group(id,menu_key,menu_set_id,access_code) values(3,'Reports',1,null);


insert into menu_item (id,menu_key,access_code,page,menu_group_id) values (1,'suppliers','adm','./general/genericList.html?entity=Supplier',1);
insert into menu_item (id,menu_key,access_code,page,menu_group_id) values (2,'items','adm','./general/genericList.html?entity=Item',1);
insert into menu_item (id,menu_key,access_code,page,menu_group_id) values (3,'dataImport','adm','./general/dataImport.html',1);


insert into list_metadata(id,profile_id,profile_code,entity) values(1,1,'ROOT','Supplier');
insert into list_metadata(id,profile_id,profile_code,entity) values(2,1,'ROOT','Item');
insert into list_metadata(id,profile_id,profile_code,entity) values(3,1,'ROOT','Customer');

insert into filter_field(id,list_metadata_id,field_key,control,accessor) values(1,1,'supplierName','text','supplierName');
insert into filter_field(id,list_metadata_id,field_key,control,accessor) values(2,1,'phoneNumber','text','address["phoneNumber"]');
insert into filter_field(id,list_metadata_id,field_key,control,accessor) values(3,1,'city','text','address["city"]');

insert into list_columns(id,list_metadata_id,field_key,accessor) values (1,1,'supplierName','supplierName');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (2,1,'phoneNumber','address["phoneNumber"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (3,1,'city','address["city"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (4,1,'state','address["state"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (5,1,'zipCode','address["zipCode"]');


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(1,'btn btn-primary','Create','onCreate',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(2,'btn btn-info','View','onView',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(3,'btn btn-warning','Edit','onEdit',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(4,'btn btn-danger','Delete','onDelete',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(5,'btn btn-success','Export','onExport',1);

insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(6,'btn btn-primary','Create','onCreate',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(7,'btn btn-info','View','onView',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(8,'btn btn-warning','Edit','onEdit',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(9,'btn btn-danger','Delete','onDelete',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id) values(10,'btn btn-success','Export','onExport',2);

insert into filter_field(id,list_metadata_id,field_key,control,accessor) values(4,2,'itemName','text','itemName');
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor) values(5,2,'supplierName','lookup','supplier','supplier["supplierName"]');
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor) values(6,2,'itemGroup','dropdown','fv::item_group','itemGroup["fvCode"]');


insert into list_columns(id,list_metadata_id,field_key,accessor) values (6,2,'itemName','itemName');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (7,2,'itemCode','itemCode');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (8,2,'barcode','barcode');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (9,2,'supplierName','supplier["supplierName"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (10,2,'itemGroup','itemGroup["fvValue"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (11,2,'uomType','uomType["fvValue"]');
insert into list_columns(id,list_metadata_id,field_key,accessor) values (12,2,'supplierPhoneNumber','supplier.address.phoneNumber');
