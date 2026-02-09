insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values
 ('DFLT-TRNS-PO','PurchaseOrders','adm','./general/genericList.html?entity=Purchase Order','DFLT-TRNS',1);

insert into list_metadata(id,profile_code,entity) values('PO','ROOT','Purchase Order');

insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PO-DOCNO','PO','docNumber','text','docNumber',1);
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor,seq_no) values('PO-SUPNAME','PO','supplierName','lookup','supplier','supplier["supplierName"]',2);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PO-BILLNO','PO','billNo','text','billNo',3);
insert into filter_field(id,list_metadata_id,field_key,control,accessor,seq_no) values('PO-DATE','PO','poDate','date','purchaseDate',4);
insert into filter_field(id,list_metadata_id,field_key,control,param,accessor,seq_no) values('PO-STATUS','PO','poStatus','dropdown','fv::po_status','status["fvCode"]',5);


insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PO-DOCNO','PO','docNumber','docNumber',1);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PO-SUPNAME','PO','supplierName','supplier["supplierName"]',2);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PO-BILLNO','PO','billNo','billNo',3);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PO-DATE','PO','purchaseDate','purchaseDate',4);
insert into list_columns(id,list_metadata_id,field_key,accessor,seq_no) values ('PO-STATUS','PO','poStatus','status["fvValue"]',5);