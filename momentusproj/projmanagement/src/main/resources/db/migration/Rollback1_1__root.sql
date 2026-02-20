delete from updateview_fields where updateview_metadata_id  ='RLCR';
delete from updateview_buttons where updateview_metadata_id  ='RLCR';
delete from updateview_metadata where id  ='RLCR';




delete from updateview_fields where updateview_metadata_id  ='DIVS';
delete from updateview_buttons where updateview_metadata_id  ='DIVS';
delete from updateview_metadata where id  ='DIVS';

delete from filter_field where list_metadata_id  ='DIVS';
delete from list_columns where list_metadata_id  ='DIVS';
delete from list_buttons where list_metadata_id  ='DIVS';
delete from list_metadata where id  ='DIVS';


delete from updateview_fields where updateview_metadata_id  ='USR';
delete from updateview_buttons where updateview_metadata_id  ='USR';
delete from updateview_metadata where id  ='USR';

delete from filter_field where list_metadata_id  ='USR';
delete from list_columns where list_metadata_id  ='USR';
delete from list_buttons where list_metadata_id  ='USR';
delete from list_metadata where id  ='USR';

delete from entity where entity_name = 'Division' ;
delete from entity where entity_name = 'Role' ;
delete from entity where entity_name = 'User' ;

delete from flyway_schema_history where version=1.1 and installed_rank=2;
