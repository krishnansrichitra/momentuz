delete from updateview_fields where updateview_metadata_id  in ('RLCR','DIVS','USR','CLNT','PRJT','NEWWTST-IT','WTMN-IT');
delete from updateview_buttons where updateview_metadata_id  in( 'RLCR','DIVS','USR','CLNT','PRJT','NEWWTST-IT','WTMN-IT');
delete from updateview_metadata where id  in ('RLCR','DIVS','USR','CLNT','PRJT','NEWWTST-IT','WTMN-IT');



delete from filter_field where list_metadata_id  in ('DIVS','USR','CLNT','PRJT');
delete from list_columns where list_metadata_id  in ('DIVS','USR','CLNT','PRJT');
delete from list_buttons where list_metadata_id  in ('DIVS','USR','CLNT','PRJT');
delete from list_metadata where id  in ('DIVS','USR','CLNT','PRJT');

delete from menu_item where id in ( 'IT_MNU-ADM-CLNT','IT_MNU-RPTS-PRJ','IT_MNU-RPTS-ADM','IT_MNU-RPTS-RLSP','IT_MNU-RPTS-OPS','IT_MNU-RPTS-NWIT','IT_MNU-RPTS-WITL','IT_MNU-RPTS-WBNCH');

delete from entity where entity_name in ( 'Division' ,'Role' ,'User' ,'Client','Project', 'WorkItem','Release','Team','Sprint');


drop table  wi_task_status_mapping;


delete from flyway_schema_history where version=1.1 and installed_rank=2;
