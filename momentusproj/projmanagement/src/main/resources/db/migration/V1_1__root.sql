insert into entity (entity_name,full_package,profile_group_code,created_by,created_time,support_import)
values ('Role','com.momentus.foundation.accessgroup.model.Role', 'GNL','seed',now(),0);


insert into updateview_metadata(id,profile_code,entity,js_file) values ('RLCR','ROOT','Role','role.js');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESC','description','dropdown','selectRole','roles',1,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-FETBTN','','button','fetchRoles',null,2,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN1','','blank',null,null,3,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-BLN2','','blank',null,null,4,'EV','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-DESCTXT','description','txt','roleName',null,5,'E','RLCR','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('RLCR-tabset','','tabset','Access codes',null,6,'AEV','RLCR','String');




insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('RLCR-SV','btn btn-primary','save','saveRole',1,'E','RLCR');





