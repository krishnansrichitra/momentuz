delete from updateview_fields where updateview_metadata_id  in ('RLCR','DIVS','USR','CLNT');
delete from updateview_buttons where updateview_metadata_id  in( 'RLCR','DIVS','USR','CLNT');
delete from updateview_metadata where id  in ('RLCR','DIVS','USR','CLNT');



delete from filter_field where list_metadata_id  in ('DIVS','USR','CLNT');
delete from list_columns where list_metadata_id  in ('DIVS','USR','CLNT');
delete from list_buttons where list_metadata_id  in ('DIVS','USR','CLNT');
delete from list_metadata where id  in ('DIVS','USR','CLNT');

delete from menu_item where id in ( 'IT_MNU-ADM-CLNT');

delete from entity where entity_name in ( 'Division' ,'Role' ,'User' ,'Client');


delete from flyway_schema_history where version=1.1 and installed_rank=2;
