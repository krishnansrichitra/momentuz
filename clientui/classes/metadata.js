class ListMetadata {
  constructor({ entity, filterFields = [], listColumns = [], listButtons=[] } = {}) {
    this.entity = entity;
    this.filterFields = filterFields.map(f => new FilterField(f));
    this.listColumns = listColumns.map(c => new ListColumn(c));
    this.listButtons = listButtons.map(b => new ListButton(b) );
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

class ListButton{
    constructor({
        buttonClass,
        jsMethod,
        innerText
    } = {}){
        this.buttonClass =buttonClass;
        this.jsMethod = jsMethod;
        this.innerText = innerText;
    };
}


class UpdateViewMetadata {
  constructor(json) {
    this.entity = json.entity;
    this.updateViewButtons = (json.updateViewButtons || [])
      .map(b => new UpdateViewButton(b))
      .sort((a, b) => a.seqNo - b.seqNo);

    this.updateViewFields = (json.updateViewFields || [])
      .map(f => new UpdateViewField(f));
  }
}

class UpdateViewButton {
  constructor(json) {
    this.id = json.id;
    this.buttonClass = json.buttonClass;
    this.jsMethod = json.jsMethod;
    this.innerText = json.innerText;
    this.visibility = json.visibility; // A, E, V
    this.seqNo = Number(json.seqNo ?? 0);
  }

  isVisible(mode) {
    return this.visibility?.includes(mode);
  }
}

class UpdateViewField {
  constructor(json) {
    this.id = json.id;
    this.fieldKey = json.fieldKey;
    this.fieldLabel = json.fieldLabel;
    this.control = json.control;
    this.param = json.param;
    this.accessor = json.accessor;
    this.visibility = json.visibility;
  }

  isVisible(mode) {
    return this.visibility?.includes(mode);
  }

  isHidden() {
    return this.control === "hidden";
  }
}

const ViewMode = Object.freeze({
  ADD: "A",
  EDIT: "E",
  VIEW: "V"
});
