create table applications ( app_code varchar(50) primary key, app_description varchar(255) , app_name varchar(100));
create table modules (module_code varchar(50) primary key, description varchar(255) , module_name varchar(100));
create table app_modules(id bigint primary key AUTO_INCREMENT, app_code varchar(50), module_code varchar(50));


insert into applications(app_code,app_description,app_name) values ('FNDC','Foundation Client', 'Foundation Client');
insert into modules (module_code,description,module_name) values ('ADM','ADMIN','ADMIN') ;
insert into app_modules(id,app_code,module_code) values (1,'FNDC','ADM');

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
  active tinyint(1) DEFAULT '1',
  created_by varchar(255) DEFAULT NULL,
  created_time datetime(6) DEFAULT NULL,
  deleted tinyint(1) DEFAULT '0',
  last_updated_by varchar(255) DEFAULT NULL,
  last_updated_time datetime(6) DEFAULT NULL

);

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
 last_updated_time datetime(6) DEFAULT NULL
);


create table entity (
entity_name varchar(100) PRIMARY KEY,
full_package varchar(255) not null,
profile_group_code varchar(100),
active tinyint(1) DEFAULT '1',
created_by varchar(255) DEFAULT NULL,
created_time datetime(6) DEFAULT NULL,
deleted tinyint(1) DEFAULT '0',
last_updated_by varchar(255) DEFAULT NULL,
 last_updated_time datetime(6) DEFAULT NULL,
foreign key (profile_group_code) references profile_group(profile_group_code)
);

insert into profile_group(profile_group_code,profile_group_description,created_by,created_time) values ('base','Base','seed',now());
insert into entity (entity_name,full_package,profile_group_code,created_by,created_time) values ('Customer','com.momentus.fndclient.customer.model.Customer', 'base','seed',now());