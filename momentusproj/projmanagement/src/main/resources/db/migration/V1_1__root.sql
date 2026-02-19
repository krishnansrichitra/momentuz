insert into entity (entity_name,entity_display,full_package,profile_group_code,created_by,created_time,support_import)
values ('Role','Role','com.momentus.foundation.accessgroup.model.Role', 'GNL','seed',now(),0);


insert into updateview_metadata(id,profile_code,entity,js_file) values ('RLCR','ROOT','Role','../scripts/role.js');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESC','','dropdown','Select Role','roles',1,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-FETBTN','','button','Fetch','fetchDetails',2,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN1','','blank',null,null,3,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN2','','blank',null,null,4,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESCTIL','title','txt','Role Title',null,5,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESCTXT','description','txt','Role Description',null,6,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-tabset','','tabset','',null,7,'AEV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('RLCR-TBAC','','tab','Access',null,8,'AEV','RLCR','List','RLCR-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent,style) values ('RLCR-ACC','accessCodes','msdropdown','Permissions','accessCodes',9,'AEV','RLCR','String','RLCR-TBAC','position: static; display: block; max-height: 250px; min-width: 600px; overflow-y: auto;');





insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('RLCR-SV','btn btn-primary','Save Role','save',1,'E','RLCR');





