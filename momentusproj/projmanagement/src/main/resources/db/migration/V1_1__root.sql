insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Role','Role','com.momentus.foundation.accessgroup.model.Role', 'GNL','seed',now(),0);


insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Division','Division','com.momentus.foundation.organization.model.Division', 'GNL','seed',now(),0);

insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('User','User','com.momentus.foundation.accessgroup.model.User', 'GNL','seed',now(),0);


insert into list_metadata(id,profile_code,entity) values('USR','ROOT','User');
insert into updateview_metadata(id,profile_code,entity,js_file) values ('USR','ROOT','User','../scripts/user.js');

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-USID','USR','Email','text','userId',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-FNAME','USR','First Name','text','firstName',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-LNAME','USR','Last Name','text','lastName',3);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-PHN','USR','Phone','text','phone',4);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-UID','USR','Email','userId',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-CITY','USR','First Name','firstName',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-FNAME','USR','Last Name','lastName',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-LNAME','USR','Phone Number','phone',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-DIV','USR','Division','division.title',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-CRT','btn btn-primary','Create','onCreate','USR',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-VIEW','btn btn-info','View','onView','USR',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-EDT','btn btn-warning','Edit','onEdit','USR',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-DEL','btn btn-danger','Delete','onDelete','USR',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-EXP','btn btn-success','Export','onExport','USR',5);


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-ID','userId','text','Email',null,1,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-FNAME','firstName','text','First Name',null,2,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LNAME','lastName','text','Last Name',null,3,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-PHN','phone','text','Phone',null,4,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-TBAC','','tab','Access',null,8,'AEV','USR','List','USR-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent,style) values ('USR-RLS','userRoles','msdropdown','Roles','Roles',9,'AEV','USR','String','USR-TBAC','position: static; display: block; max-height: 250px; min-width: 600px; overflow-y: auto;');


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-CRTDBY','createdBy','text','Created By',null,100,'V','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-CRTDDT','createdTime','text','Created Time',null,101,'V','USR','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LSUPBY','lastUpdatedBy','text','Last Updated By',null,102,'V','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LSUPDT','lastUpdatedTime','text','Last Updated Time',null,103,'V','USR','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-VERSION','version','hidden','Version',null,104,'AEV','USR','Numeric');





insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-SAVE','btn btn-primary','Save','onSave',1,'AE','USR');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-EDT','btn btn-info','Edit','onEdit',2,'V','USR');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-CNCL','btn btn-secondary','Cancel','onCancel',3,'AEV','USR');

insert into list_metadata(id,profile_code,entity) values('DIVS','ROOT','Division');
insert into updateview_metadata(id,profile_code,entity) values ('DIVS','ROOT','Division');

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('DIVS-NAME','DIVS','Division Code','text','divisionCode',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('DIVS-PHONE','DIVS','Phone Number','text','address["phoneNumber"]',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('DIVS-CITY','DIVS','City','text','address["city"]',3);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('DIVS-NAME','DIVS','Title','title',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('DIVS-PHONE','DIVS','Phone Number','address["phoneNumber"]',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('DIVS-CITY','DIVS','City','address["city"]',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('DIVS-STATE','DIVS','State','address["state"]',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('DIVS-ZIP','DIVS','ZipCode','address["zipcode"]',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('DIVS-CRT','btn btn-primary','Create','onCreate','DIVS',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('DIVS-VIEW','btn btn-info','View','onView','DIVS',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('DIVS-EDT','btn btn-warning','Edit','onEdit','DIVS',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('DIVS-DEL','btn btn-danger','Delete','onDelete','DIVS',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('DIVS-EXP','btn btn-success','Export','onExport','DIVS',5);


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-ID','id','hidden','Id',null,1,'AEV','DIVS','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-TDC','divisionCode','text','Code',null,2,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-TITLE','title','text','Title',null,3,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-PRNTDIVID','parentDivision.id','dropdown','Parent Division','Division',3.1,'AE','DIVS','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-PRNTDIVTT','parentDivision.title','text','Parent Division','division',3.2,'V','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-PRMCT','primaryContactName','text','Primary Contact Name',null,4,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-PRMPH','primaryContactPhone','text','Primary Contact Phone',null,5,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-SNDCT','secondaryContactName','text','Secondary Contact Name',null,6,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-SNDPH','secondaryContactPhone','text','Secondary Contact Phone',null,7,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-ADD1','address.address1','text','Address1',null,8,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-ADD2','address.address2','text','Address2',null,9,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-CTY','address.city','text','City',null,10,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-ST','address.state','text','State','st',11,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-CNTY','address.country','dropdown','Country','cntry',12,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-ZPCD','address.zipcode','text','ZipCode',null,13,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-PHNO','address.phoneNumber','text','Phone Number',null,14,'AEV','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-CRTDBY','createdBy','text','Created By',null,100,'V','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-CRTDDT','createdTime','text','Created Time',null,101,'V','DIVS','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-LSUPBY','lastUpdatedBy','text','Last Updated By',null,102,'V','DIVS','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-LSUPDT','lastUpdatedTime','text','Last Updated Time',null,103,'V','DIVS','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('DIVS-VERSION','version','hidden','Version',null,104,'AEV','DIVS','Numeric');


insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('DIVS-SAVE','btn btn-primary','Save','onSave',1,'AE','DIVS');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('DIVS-EDT','btn btn-info','Edit','onEdit',2,'V','DIVS');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('DIVS-CNCL','btn btn-secondary','Cancel','onCancel',3,'AEV','DIVS');




insert into updateview_metadata(id,profile_code,entity,js_file) values ('RLCR','ROOT','Role','../scripts/role.js');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESC','','dropdown','Select Role','Role',1,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-ID','id','hidden','',null,1.1,'EV','RLCR','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-VERSION','version','hidden','',null,1.2,'AEV','RLCR','Numeric');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-FETBTN','','button','Fetch','fetchDetails',2,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN1','','blank',null,null,3,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN2','','blank',null,null,4,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESCTIL','title','txt','Role Title',null,5,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESCTXT','description','txt','Role Description',null,6,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-tabset','','tabset','',null,7,'AEV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('RLCR-TBAC','','tab','Access',null,8,'AEV','RLCR','List','RLCR-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent,style) values ('RLCR-ACC','accessCodes','msdropdown','Permissions','accessCodes',9,'AEV','RLCR','String','RLCR-TBAC','position: static; display: block; max-height: 250px; min-width: 600px; overflow-y: auto;');


insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('RLCR-SV','btn btn-primary','Save Role','saveRole',1,'E','RLCR');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('RLCR-CLR','btn btn-warning','Clear','clearAccessorControls',2,'E','RLCR');





