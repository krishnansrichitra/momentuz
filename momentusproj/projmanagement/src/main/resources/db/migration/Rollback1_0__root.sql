
delete from updateview_metadata where id  ='RLCR';
delete from updateview_fields where updateview_metadata_id  ='RLCR';
delete from updateview_buttons where updateview_metadata_id  ='RLCR';


delete from entity where entity_name = 'Role' ;

delete from flyway_schema_history where version=1.1 and installed_rank=2;
