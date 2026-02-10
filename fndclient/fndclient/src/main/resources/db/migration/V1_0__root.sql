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
  org_owner tinyint(1) DEFAULT 0,
  system_created tinyint(1) DEFAULT 0,
  foreign key(org_id) references organization(id)

);

insert into users (user_id,org_id,email,first_name,last_name,password,phone,org_owner,system_created) values
 ('admin@momentuz.com',1,'admin@momentuz.com','Admin','Momentus','$2a$10$0w.63IKihn65p1s1hZS12Omx7KmjVB/Xp3nUJMMdN3urxqJAHB0cO','908889',true,true);

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

insert into finite_group(group_code,group_name) values ('uom_type','UOM Type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_WGHT','WEIGHT','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_NOS','NOs','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_VOLU','Volume','uom_type');
insert into finite_value(fv_code,fv_value,group_code) values ('UOM_CRTN','Cartons','uom_type');

insert into finite_group(group_code,group_name) values ('item_group','Item Group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_merch','Merchandise Item','item_group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_raw','Raw Material','item_group');
insert into finite_value(fv_code,fv_value,group_code) values ('itmgrp_inhs','In house Item','item_group');


insert into finite_group(group_code,group_name) values ('po_status','PO Status');
insert into finite_value(fv_code,fv_value,group_code) values ('po_crt','Order Created','po_status');
insert into finite_value(fv_code,fv_value,group_code) values ('po_inprg','In Progress','po_status');
insert into finite_value(fv_code,fv_value,group_code) values ('po_dlvd','Delivered','po_status');
insert into finite_value(fv_code,fv_value,group_code) values ('po_clsd','Closed','po_status');
insert into finite_value(fv_code,fv_value,group_code) values ('po_cncld','Cancelled','po_status');

insert into finite_group(group_code,group_name) values ('po_line_status','PO Line Status');
insert into finite_value(fv_code,fv_value,group_code) values ('pl_crt','PO Line Created','po_line_status');
insert into finite_value(fv_code,fv_value,group_code) values ('pl_prtdlv','Partially Delivered','po_line_status');
insert into finite_value(fv_code,fv_value,group_code) values ('pl_dlvd','Delivered','po_line_status');
insert into finite_value(fv_code,fv_value,group_code) values ('pl_cncld','Cancelled','po_line_status');


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
role_description  varchar(255) primary key,
profile_code varchar(255) ,
access_codes blob,
foreign key (profile_code) references profile (profile_code)
);




insert into profile_group(profile_group_code,profile_group_description,created_by,created_time) values ('GNL','General','seed',now());
insert into profile(profile_code,profile_description,full_profile_code,profile_group_code,created_by,created_time) values ('ROOT','Base Profile','ROOT','GNL','seed',now());

insert into sector_profile(id,sector,profile_code)values ('ROOT-ALL','ALL','ROOT');
insert into primary_user_role(role_description,profile_code,access_codes) values ('PRIMARY-ROOT','ROOT','extn,adm');


insert into entity (entity_name,full_package,profile_group_code,created_by,created_time,support_import)
values ('Customer','com.momentus.fndclient.customer.model.Customer', 'GNL','seed',now(),0);
insert into fndclient.entity(entity_name,full_package,profile_group_code,active,support_import,sequence) values
('Supplier','com.momentus.fndclient.supplier.model.Supplier','GNL',1,1,1);
insert into fndclient.entity(entity_name,full_package,profile_group_code,active,support_import,sequence) values
('Item','com.momentus.fndclient.item.model.Item','GNL',1,1,2);
insert into fndclient.entity(entity_name,full_package,profile_group_code,active,support_import) values
('Purchase Order','com.momentus.fndclient.purchase.model.PurchaseOrder','GNL',1,0);

INSERT INTO nextup_config(id,profile_code,entity,field_1,field_2,field_3,date_format,reset_seq_per_day,sequence_width,prefix)
values ('PO-ROOT','ROOT','Purchase Order','nxtup_prfx','nxtup_dt','nxtup_seq','ddMMyyyy',0,3,'PO');

insert into menu_set(id,profile_code,description) values ('DFLT','ROOT','Default Menu');
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('DFLT-MSTR','Master','DFLT',null,1);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('DFLT-TRNS','Transactions','DFLT',null,2);
insert into menu_group(id,menu_key,menu_set_id,access_code,seq_no) values('DFLT-RPTS','Reports', 'DFLT',null,3);


insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('DFLT-MSTR-SUP','suppliers','adm','./general/genericList.html?entity=Supplier','DFLT-MSTR',1);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('DFLT-MSTR-ITM','items','adm','./general/genericList.html?entity=Item','DFLT-MSTR',2);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('DFLT-MSTR-DTIMP','dataImport','adm','./general/dataImport.html','DFLT-MSTR',3);

insert into updateview_metadata(id,profile_code,entity) values ('SUP','ROOT','Supplier');
insert into updateview_metadata(id,profile_code,entity) values ('ITM','ROOT','Item');

insert into updateview_metadata(id,profile_code,entity) values ('USPRF','ROOT','UserProfile');




insert into list_metadata(id,profile_code,entity) values('SUP','ROOT','Supplier');
insert into list_metadata(id,profile_code,entity) values('ITM','ROOT','Item');
insert into list_metadata(id,profile_code,entity) values('CUST','ROOT','Customer');

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('SUP-NAME','SUP','supplierName','text','supplierName',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('SUP-PHONE','SUP','phoneNumber','text','address["phoneNumber"]',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('SUP-CITY','SUP','city','text','address["city"]',3);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('SUP-NAME','SUP','supplierName','supplierName',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('SUP-PHONE','SUP','phoneNumber','address["phoneNumber"]',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('SUP-CITY','SUP','city','address["city"]',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('SUP-STATE','SUP','state','address["state"]',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('SUP-ZIP','SUP','zipCode','address["zipcode"]',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('SUP-CRT','btn btn-primary','Create','onCreate','SUP',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('SUP-VIEW','btn btn-info','View','onView','SUP',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('SUP-EDT','btn btn-warning','Edit','onEdit','SUP',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('SUP-DEL','btn btn-danger','Delete','onDelete','SUP',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('SUP-EXP','btn btn-success','Export','onExport','SUP',5);



insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('ITM-ITMNAME','ITM','itemName','text','itemName',1);
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor,seq_no) values('ITEM-SUPNAME','ITM','supplierName','lookup','supplier','supplier["supplierName"]',2);
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor,seq_no) values('ITM-ITMGRP','ITM','itemGroup','dropdown','fv::item_group','itemGroup["fvCode"]',3);


insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITM-ITMNAME','ITM','itemName','itemName',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITM-ITCD','ITM','itemCode','itemCode',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITM-BRCD','ITM','barcode','barcode',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITEM-SUP','ITM','supplierName','supplier["supplierName"]',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITM-ITMGRP','ITM','itemGroup','itemGroup["fvValue"]',5);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITM-UM-TYPE','ITM','uomType','uomType["fvValue"]',6);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('ITEM-SUP-PH','ITM','supplierPhoneNumber','supplier.address.phoneNumber',7);

insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('ITM-CRT','btn btn-primary','Create','onCreate','ITM',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('ITEM-VIW','btn btn-info','View','onView','ITM',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('ITM-EDT','btn btn-warning','Edit','onEdit','ITM',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('ITM-DEL','btn btn-danger','Delete','onDelete','ITM',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('ITM-EXP','btn btn-success','Export','onExport','ITM',5);

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-ID','id','hidden','id',null,1,'AEV','SUP','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-NAME','supplierName','text','supplierName',null,1,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-CRDLMT','creditLimit','text','creditLimit',null,2,'AEV','SUP','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-JNDDT','joiningDate','date','joiningDate',null,3,'AEV','SUP','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-RNK','supplierRank','text','supplierRank',null,4,'AEV','SUP','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-ADD1','address.address1','text','address1',null,5,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-ADD2','address.address2','text','address2',null,6,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-CTY','address.city','text','city',null,7,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-ST','address.state','text','state','st',8,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-CNTY','address.country','dropdown','country','cntry',9,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-ZPCD','address.zipcode','text','zipcode',null,10,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-PHNO','address.phoneNumber','text','phoneNumber',null,11,'AEV','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-CRTDBY','createdBy','text','createdBy',null,100,'V','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-CRTDDT','createdTime','text','createdTime',null,101,'V','SUP','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-LSUPBY','lastUpdatedBy','text','lastUpdatedBy',null,102,'V','SUP','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-LSUPDT','lastUpdatedTime','text','lastUpdatedTime',null,103,'V','SUP','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('SUP-VERSION','version','hidden','version',null,104,'AEV','SUP','Numeric');


insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('SUP-SAVE','btn btn-primary','save','onSave',1,'AE','SUP');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('SUP-EDT','btn btn-info','edit','onEdit',2,'V','SUP');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('SUP-CNCL','btn btn-secondary','cancel','onCancel',1,'AEV','SUP');


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-ID','id','hidden','id',null,1,'AEV','ITM','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-itemName','itemName','text','itemName',null,2,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-barcode','barcode','text','barcode',null,3,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-itemCode','itemCode','text','itemCode',null,4,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-supplierName','supplier.supplierName','lookup','supplierName','supplier',5,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-itemGroup','itemGroup.fvCode','dropdown','itemGroup','fv::item_group',6,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-uomType','uomType.fvCode','dropdown','uomType','fv::uom_type',7,'AEV','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-CRTDBY','createdBy','text','createdBy',null,100,'V','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-CRTDDT','createdTime','text','createdTime',null,101,'V','ITM','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-LSUPBY','lastUpdatedBy','text','lastUpdatedBy',null,102,'V','ITM','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-LSUPDT','lastUpdatedTime','text','lastUpdatedTime',null,103,'V','ITM','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('ITM-VERSION','version','hidden','version',null,104,'AEV','ITM','Numeric');

insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('ITM-SAVE','btn btn-primary','save','onSave',1,'AE','ITM');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('ITM-EDT','btn btn-info','edit','onEdit',2,'V','ITM');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('ITM-CNCL','btn btn-secondary','cancel','onCancel',1,'AEV','ITM');



insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-CRUPWD','currentPassword','password','currentPassword',null,1,'E','USPRF','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-PWD','password','password','password',null,2,'E','USPRF','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USPRF-CNFPWD','confirmPassword','password','confirmPassword',null,3,'E','USPRF','String');

insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USPRF-UPD','btn btn-primary','updatePassword','onUpdate',1,'E','USPRF');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USPRF-CLS','btn btn-secondary','close','onClose',1,'E','USPRF');
