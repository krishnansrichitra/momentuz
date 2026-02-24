insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Role','Role','com.momentus.foundation.accessgroup.model.Role', 'GNL','seed',now(),0);


insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Division','Division','com.momentus.foundation.organization.model.Division', 'GNL','seed',now(),0);

insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('User','User','com.momentus.foundation.accessgroup.model.User', 'GNL','seed',now(),0);

-- Start Purchase  --
insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Project','Project','com.momentus.projmanagement.project.model.Project', 'GNL','seed',now(),1);

insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-RPTS-PRJ','Projects','PRJ-ADM','./general/genericList.html?entity=Project','IT_MNU-RPTS',5);



insert into list_metadata(id,profile_code,entity,profile_level) values('PRJT','ROOT','Project',1);
insert into updateview_metadata(id,profile_code,entity,profile_level) values ('PRJT','ROOT','Project',1);

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PRJT-FCD','PRJT','Code','text','projectCode',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PRJT-FTIE','PRJT','Title','text','projectTitle',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PRJT-PROWN','PRJT','Primary Owner','lookup','projectPrimaryOwner["userId"]',3);
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor,seq_no) values('PRJT-STATFL','PRJT','Status','dropdown','fv::proj_status','status["fvCode"]',3);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PRJT-CD','PRJT','Code','projectCode',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PRJT-NAME','PRJT','Title','projectTitle',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PRJT-PHONE','PRJT','Primary Owner','projectPrimaryOwner["userId"]',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PRJT-CITY','PRJT','Status','status["fvValue"]',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PRJT-CMCDT','PRJT','Commence Date','commenceDate',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PRJT-CRT','btn btn-primary','Create','onCreate','PRJT',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PRJT-VIEW','btn btn-info','View','onView','PRJT',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PRJT-EDT','btn btn-warning','Edit','onEdit','PRJT',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PRJT-DEL','btn btn-danger','Delete','onDelete','PRJT',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PRJT-EXP','btn btn-success','Export','onExport','PRJT',5);


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-ID','id','hidden','Id',null,1,'AEV','PRJT','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-CODE','projectCode','text','Project Code',null,2,'AEV','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-TITLE','projectTitle','text','Project Title',null,3,'AEV','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-PRMUSR','projectPrimaryOwner.userId','lookup','Primary Owner','User',5,'AEV','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-SECUSR','projectSecondaryOwner.userId','lookup','Secondary Owner','User',6,'AEV','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-STATS','status.fvCode','dropdown','Status','fv::proj_status',7,'AEV','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-CMNDate','commenceDate','date','Commence Date',null,8,'AEV','PRJT','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-TRGNDDate','targetEndDate','date','Target End Date',null,9,'AEV','PRJT','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-ACTNDate','actualEndDate','date','Actual End Date',null,10,'AEV','PRJT','Date');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-CRTDBY','createdBy','text','Created By',null,100,'V','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-CRTDDT','createdTime','text','Created Time',null,101,'V','PRJT','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-LSUPBY','lastUpdatedBy','text','Last Updated By',null,102,'V','PRJT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-LSUPDT','lastUpdatedTime','text','Last Updated Time',null,103,'V','PRJT','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PRJT-VERSION','version','hidden','Version',null,104,'AEV','PRJT','Numeric');


insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PRJT-SAVE','btn btn-primary','Save','onSave',1,'AE','PRJT');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PRJT-EDT','btn btn-info','Edit','onEdit',2,'V','PRJT');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PRJT-CNCL','btn btn-secondary','Cancel','onCancel',3,'AEV','PRJT');


-- End Purchase  --
-- Starting Client  --
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values ('IT_MNU-ADM-CLNT','Clients','ADM-MST','./general/genericList.html?entity=Client','IT_MNU-ADM',5);
insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Client','Client','com.momentus.projmanagement.basedata.client.model.Client', 'GNL','seed',now(),1);


insert into list_metadata(id,profile_code,entity,profile_level) values('CLNT','ROOT','Client',1);
insert into updateview_metadata(id,profile_code,entity,profile_level) values ('CLNT','ROOT','Client',1);

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('CLNT-NAME','CLNT','Client','text','title',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('CLNT-PHONE','CLNT','Phone Number','text','address["phoneNumber"]',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('CLNT-CITY','CLNT','City','text','address["city"]',3);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('CLNT-NAME','CLNT','Title','title',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('CLNT-PHONE','CLNT','Phone Number','address["phoneNumber"]',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('CLNT-CITY','CLNT','City','address["city"]',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('CLNT-STATE','CLNT','State','address["state"]',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('CLNT-ZIP','CLNT','ZipCode','address["zipcode"]',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('CLNT-CRT','btn btn-primary','Create','onCreate','CLNT',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('CLNT-VIEW','btn btn-info','View','onView','CLNT',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('CLNT-EDT','btn btn-warning','Edit','onEdit','CLNT',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('CLNT-DEL','btn btn-danger','Delete','onDelete','CLNT',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('CLNT-EXP','btn btn-success','Export','onExport','CLNT',5);


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-ID','id','hidden','Id',null,1,'AEV','CLNT','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-TITLE','title','text','Title',null,3,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-PRMCT','primaryContactName','text','Primary Contact Name',null,4,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-PRMPH','primaryContactPhone','text','Primary Contact Phone',null,5,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-SNDCT','secondaryContactName','text','Secondary Contact Name',null,6,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-SNDPH','secondaryContactPhone','text','Secondary Contact Phone',null,7,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-ADD1','address.address1','text','Address1',null,8,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-ADD2','address.address2','text','Address2',null,9,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-CTY','address.city','text','City',null,10,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-ST','address.state','text','State','st',11,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-CNTY','address.country','dropdown','Country','cntry',12,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-ZPCD','address.zipcode','text','ZipCode',null,13,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-PHNO','address.phoneNumber','text','Phone Number',null,14,'AEV','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-CRTDBY','createdBy','text','Created By',null,100,'V','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-CRTDDT','createdTime','text','Created Time',null,101,'V','CLNT','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-LSUPBY','lastUpdatedBy','text','Last Updated By',null,102,'V','CLNT','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-LSUPDT','lastUpdatedTime','text','Last Updated Time',null,103,'V','CLNT','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('CLNT-VERSION','version','hidden','Version',null,104,'AEV','CLNT','Numeric');


insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('CLNT-SAVE','btn btn-primary','Save','onSave',1,'AE','CLNT');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('CLNT-EDT','btn btn-info','Edit','onEdit',2,'V','CLNT');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('CLNT-CNCL','btn btn-secondary','Cancel','onCancel',3,'AEV','CLNT');



-- end client

-- divs start
insert into list_metadata(id,profile_code,entity,profile_level) values('DIVS','ROOT','Division',1);
insert into updateview_metadata(id,profile_code,entity,profile_level) values ('DIVS','ROOT','Division',1);

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

-- divs end



-- start user




insert into list_metadata(id,profile_code,entity,js_file,profile_level) values('USR','ROOT','User','../scripts/user.js',1);
insert into updateview_metadata(id,profile_code,entity,js_file,profile_level) values ('USR','ROOT','User','../scripts/user.js',1);

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-USID','USR','Email','text','userId',1);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-FNAME','USR','First Name','text','firstName',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-LNAME','USR','Last Name','text','lastName',3);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('USR-PHN','USR','Phone','text','phone',4);

insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-UID','USR','Email','userId',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-CITY','USR','First Name','firstName',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-FNAME','USR','Last Name','lastName',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-LNAME','USR','Phone Number','phone',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('USR-DIV','USR','Division','division.title',5);


insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-CRT','btn btn-primary','Create','onUserCreate','USR',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-VIEW','btn btn-info','View','onUserView','USR',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-EDT','btn btn-warning','Edit','onUserEdit','USR',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-DEL','btn btn-danger','Delete','onUserDelete','USR',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('USR-EXP','btn btn-success','Export','onExport','USR',5);


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-ID','userId','text','Email',null,1,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-FNAME','firstName','text','First Name',null,2,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LNAME','lastName','text','Last Name',null,3,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-PHN','phone','text','Phone',null,4,'AEV','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-PRNTDIVID','division.id','dropdown','Division','Division',5.1,'AE','USR','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-PRNTDIVTT','division.title','text','Division',null,5.2,'V','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-tabset','','tabset','',null,7,'AEV','USR','String');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-TBAC','','tab','Access',null,8,'AEV','USR','List','USR-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent,style) values ('USR-ACCLIN','userRoles','table','Roles','cols=1;colTitles=["Role"];colWidth=["25"];',9,'AEV','USR','String','USR-TBAC','width: 30%');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-ACCLIN-RL','userRoles.role.id','dropdown','Role','Role',10,'AE','USR','Numeric','USR-ACCLIN');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-ACCLIN-RLTX','userRoles.role.title','text','Role','Role',11,'V','USR','String','USR-ACCLIN');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-ACCLIN-ID','userRoles.id','hidden','Id',null,12,'AEV','USR','Numeric','USR-ACCLIN');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('USR-ACCLIN-version','userRoles.version','hidden','Version',null,13,'AEV','USR','Numeric','USR-ACCLIN');




insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-CRTDBY','createdBy','hidden','Created By',null,100,'V','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-CRTDDT','createdTime','hidden','Created Time',null,101,'V','USR','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LSUPBY','lastUpdatedBy','hidden','Last Updated By',null,102,'V','USR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-LSUPDT','lastUpdatedTime','hidden','Last Updated Time',null,103,'V','USR','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('USR-VERSION','version','hidden','Version',null,104,'AEV','USR','Numeric');





insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-SAVE','btn btn-primary','Save','onUserSave',1,'AE','USR');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-EDT','btn btn-info','Edit','onEditMode',2,'V','USR');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('USR-CNCL','btn btn-secondary','Cancel','onCancel',3,'AEV','USR');

-- end usr
 -- start role
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

-- end role






