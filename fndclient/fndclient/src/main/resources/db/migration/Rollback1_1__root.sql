delete from menu_item where id = 'DFLT-TRNS-PO' ;

delete from filter_field where list_metadata_id = 'PO';
delete from list_buttons where list_metadata_id = 'PO';
delete from list_columns where list_metadata_id ='PO';
delete from list_metadata where id = 'PO';



delete from updateview_fields where updateview_metadata_id ='PO';
delete from updateview_buttons where updateview_metadata_id ='PO';
delete from updateview_metadata where id ='PO';



delete from flyway_schema_history where version=1.1 and installed_rank=2;
