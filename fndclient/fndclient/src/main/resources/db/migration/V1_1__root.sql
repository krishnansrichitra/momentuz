insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no) values
 ('DFLT-TRNS-PO','PurchaseOrders','adm','./general/genericList.html?entity=Purchase Order','DFLT-TRNS',1);
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,has_children) values
 ('DFLT-TRNS-SO','SalesOrders','adm','./general/genericList.html?entity=SalesOrders','DFLT-TRNS',2,true) on duplicate key update id = id;
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,parent_item) values
 ('DFLT-TRNS-SO-ECOM','Ecom','adm','./general/dataImport.html','DFLT-TRNS',3,'DFLT-TRNS-SO') on duplicate key update id = id;
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,parent_item) values
 ('DFLT-TRNS-SO-STORE','StoreSale','adm','./general/genericList.html?entity=SalesOrders','DFLT-TRNS',4,'DFLT-TRNS-SO') on duplicate key update id = id;

insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,has_children) values
 ('DFLT-TRNS-RO','ReturnOrders','adm','./general/genericList.html?entity=SalesOrders','DFLT-TRNS',5,true)  on duplicate key update id = id;
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,parent_item) values
 ('DFLT-TRNS-RO-ECOM','EcomRET','adm','./general/dataImport.html','DFLT-TRNS',6,'DFLT-TRNS-RO')  on duplicate key update id = id;
insert into menu_item (id,menu_key,access_code,page,menu_group_id,seq_no,parent_item) values
 ('DFLT-TRNS-RO-STORE','StoreSaleRET','adm','./general/genericList.html?entity=SalesOrders','DFLT-TRNS',7,'DFLT-TRNS-RO')  on duplicate key update id = id;


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

insert into updateview_metadata(id,profile_code,entity,js_file) values ('PO','ROOT','Purchase Order','../scripts/po.js');


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-ID','id','hidden','id',null,1,'AEV','PO','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-docNumber','docNumber','text','docNumber',null,2,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-billNo','billNo','text','billNo',null,2.1,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-poDate','purchaseDate','date','purchaseDate',null,3,'AEV','PO','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-expDate','expectedDeliveryDate','date','expectedDeliveryDate',null,4,'AEV','PO','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-supplierName','supplier.supplierName','lookup','supplierName','supplier',5,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-actdelDate','actualDeliveryDate','date','actualDeliveryDate',null,6,'AEV','PO','Date');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-status','status.fvCode','dropdown','status','fv::po_status',7,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-comments','comments','text','comments',null,8,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-tabset','','tabset','',null,9,'AEV','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-tabLine','','tabset','poLines',null,10,'AEV','PO','List','PO-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-tabNote','','tabset','poNotes',null,11,'AEV','PO','List','PO-tabset');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-tabSupInfo','','tabset','poSupInfo',null,12,'AEV','PO','Set','PO-tabset');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines','poLines','table','poLines','cols=5;colTitles=["barcode","Item","Qty","Price","Total"];colWidth=["25","25","15","15","15"];',13,'AEV','PO','String','PO-tabLine');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-barcode','poLines.item.barcode','text','barcode',null,14,'AEV','PO','String','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-item','poLines.item.itemName','lookup','itemName','item',15,'AEV','PO','String','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-price','poLines.price','text','price',null,16,'AEV','PO','Numeric','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-qty','poLines.qty','text','qty',null,17,'AEV','PO','Numeric','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-total','poLines.total','text','total',null,18,'AEV','PO','Numeric','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-id','poLines.id','hidden','id',null,19,'AEV','PO','Numeric','PO-Lines');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Lines-version','poLines.version','hidden','version',null,20,'AEV','PO','Numeric','PO-Lines');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent,style) values ('PO-Notes','poNotes','table','poNotes','cols=2;colTitles=["noteType","Note"];colWidth=["15","30"];',21,'AEV','PO','String','PO-tabNote','width: 50%;');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Notes-noteType','poNotes.noteType.fvCode','dropdown','noteType','fv::po_note_type',22,'AEV','PO','String','PO-Notes');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Notes-note','poNotes.note','text','note',null,23,'AEV','PO','String','PO-Notes');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Notes-id','poNotes.id','hidden','id',null,24,'AEV','PO','Numeric','PO-Notes');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-Notes-version','poNotes.version','hidden','version',null,25,'AEV','PO','Numeric','PO-Notes');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-tabSupInfo-prodDate','poSupplierInfo.productionDate','date','productionDate',null,30,'AEV','PO','Date','PO-tabSupInfo');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type,parent) values ('PO-tabSupInfo-supplierComments','poSupplierInfo.supplierComments','text','supplierComments',null,31,'AEV','PO','String','PO-tabSupInfo');


insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-gross','grossTotal','text','grossTotal',null,50,'AEV','PO','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-taxAmount','taxAmount','text','taxAmount',null,51,'AEV','PO','Numeric');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-total','total','text','total',null,52,'AEV','PO','Numeric');

insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-CRTDBY','createdBy','text','createdBy',null,100,'V','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-CRTDDT','createdTime','text','createdTime',null,101,'V','PO','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-LSUPBY','lastUpdatedBy','text','lastUpdatedBy',null,102,'V','PO','String');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-LSUPDT','lastUpdatedTime','text','lastUpdatedTime',null,103,'V','PO','DateTime');
insert into updateview_fields(id,accessor,control,field_key,param,seq_no,visibility,updateview_metadata_id,data_type) values ('PO-VERSION','version','hidden','version',null,104,'AEV','PO','Numeric');

insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PO-CRT','btn btn-primary','Create','onCreate','PO',1);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PO-VIW','btn btn-info','View','onView','PO',2);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PO-EDT','btn btn-warning','Edit','onEdit','PO',3);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PO-DEL','btn btn-danger','Delete','onDelete','PO',4);
insert into  list_buttons(id,button_class,inner_text,js_method,list_metadata_id,seq_no) values('PO-EXP','btn btn-success','Export','onExport','PO',5);

insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PO-SAVE','btn btn-primary','save','onSave',1,'AE','PO');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PO-EDT','btn btn-info','edit','onEdit',2,'V','PO');
insert into updateview_buttons(id,button_class,inner_text,js_method,seq_no,visibility,updateview_metadata_id) values ('PO-CNCL','btn btn-secondary','cancel','onCancel',1,'AEV','PO');


insert into finite_group(group_code,group_name) values ('po_note_type','PO Note Type') on duplicate key update group_name = group_name;
insert into finite_value(fv_code,fv_value,group_code) values ('po_note_type_pl_dev','Plan deviation','po_note_type')  on duplicate key update fv_code = fv_code;
insert into finite_value(fv_code,fv_value,group_code) values ('po_note_type_pr_neg','Price Negotiaton','po_note_type') on duplicate key update fv_code = fv_code;
insert into finite_value(fv_code,fv_value,group_code) values ('po_note_type_tr_ag','Transportation agreement','po_note_type') on duplicate key update fv_code = fv_code;
