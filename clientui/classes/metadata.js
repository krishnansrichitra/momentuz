class ListMetadata {
  constructor({ entity, filterFields = [], listColumns = [] } = {}) {
    this.entity = entity;
    this.filterFields = filterFields.map(f => new FilterField(f));
    this.listColumns = listColumns.map(c => new ListColumn(c));
  }
}
class FilterField {
  constructor({
    fieldKey,
    fieldLabel,
    control,
    param = null,
    accessor
  } = {}) {
    this.fieldKey = fieldKey;
    this.fieldLabel = fieldLabel;
    this.control = control;
    this.param = param;
    this.accessor = accessor;
  }
}
class ListColumn {
  constructor({
    fieldkey,      // note: API uses `fieldkey`
    fieldLabel,
    accessor
  } = {}) {
    this.fieldKey = fieldkey; // normalize naming
    this.fieldLabel = fieldLabel;
    this.accessor = accessor;
  }
}
