const entity = params.get("entity");
const mode = params.get("mode");
const id = params.get("id");



async function loadMetadata() {
    const url =
        urlPrefix + "api/metadata/getUpdateViewMetadata" +
        "?entity=" + entity + '&mode=' + mode;

    try {
        const response = await axios.get(url);
        let met = response.data
        console.log("metadata response:", met);
        const updateMetData = new UpdateViewMetadata(met);
        if (mode == 'Add') {
            await renderUpdateViewForm(updateMetData, 'A');
        } else if (mode == 'Edit') {

            await renderUpdateViewForm(updateMetData, 'E');
            let jsonContent = await fetchDataByEntityAndId(entity, id);
            let formControl = document.getElementById("genericForm");
            traverseJson(formControl, jsonContent);
        } else if (mode == 'View') {

            await renderUpdateViewForm(updateMetData, 'V');
            let jsonContent = await fetchDataByEntityAndId(entity, id);
            let formControl = document.getElementById("genericForm");
            traverseJson(formControl, jsonContent);
        }


    } catch (error) {
        console.error("Error loading metadata or list:", error);
    }
}

function getChildren(currentField, allFields)
{
    let childArr = allFields.filter(field => field.parent === currentField.id);
    console.log('childArr=' + childArr);
    return childArr;

}

async function renderUpdateViewForm(metadata, mode = 'E') {
    const form = document.getElementById('genericForm');
    form.innerHTML = '';
    console.log('metadsta title=' + metadata.title);
    document.getElementById("hpageTitle").innerHTML = metadata.title;

    const visibleFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && !f.isHidden());

    const hiddenFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && f.isHidden());

    let row = createRow();
    let colCount = 0;

    for (let field of visibleFields){
        if (field.parent != null ) continue;
        if (colCount === 4) {
            form.appendChild(row);
            row = createRow();
            colCount = 0;
        }
        if (field.control == 'table') {
            console.log('creating table');
            if (colCount != 0) {
                form.appendChild(row);
                row = createRow();
            }
            const col = document.createElement('div');
            col.className = 'col-lg-12 col-md-12 col-sm-12';
            let childFields = getChildren(field, visibleFields);
            const control = createTable(field, childFields);
            colCount = 4;
            console.log('table =' + control);
            col.appendChild(control);
            row.appendChild(col);
            continue;
        } 
            const col = document.createElement('div');
            col.className = 'col-lg-3 col-md-6 col-sm-12';
            const label = document.createElement('label');
            label.className = 'form-label';
            if (typeof field.fieldLabel === 'string' && field.fieldLabel.trim() !== '')
                label.textContent = field.fieldLabel + ":";
            if (mode != 'V') {
                const control = renderControl(field);
                label.classList.add('form-label', 'mb-1');
                col.appendChild(label);
                col.appendChild(control);
            } else {
                const control = renderViewControl(field);
                label.classList.add('form-label', 'mb-1');
                control.classList.add('form-control');
                col.appendChild(label);
                col.appendChild(control);
            }
            row.appendChild(col);
            colCount++;
    
    }

    // append last row
    if (row.children.length > 0) {
        form.appendChild(row);
    }

    // hidden fields
    hiddenFields.forEach(field => {
        form.appendChild(renderControl(field));
    });

    let hr1 = document.createElement('hr');
    hr1.className = 'border border-secondary border-2 my-3';

    let hr2 = document.createElement('hr');
    hr2.className = 'border border-secondary border-2 my-3';

    form.appendChild(hr1);
    //  form.appendChild(hr2)


    // action buttons
    form.appendChild(renderButtons(metadata, mode));
}


function createRow() {
    const row = document.createElement('div');
    row.className = 'row g-3 mt-2';
    return row;
}

function renderViewControl(field) {
    let inpel = document.createElement("span");
    inpel.className = "fw-bold";
    inpel.id = field.id;
    inpel.name = field.fieldKey;
    let accessor = field.accessor.includes('.fvCode') ? field.accessor.replace('.fvCode', '.fvValue') : field.accessor;
    inpel.dataset.accessor = accessor;
    inpel.dataset.dtype = field.dType;
    return inpel;

}

function createTable(field,childFields) {

    let params = field.param;
    let noCols;
    let colTitles =[];
   let colWidths = [];

    params.split(";").forEach(part => {
        const [key, value] = part.split("=");

        if (key === "cols") {
            noCols = Number(value);
        }

        if (key === "colTitles") {
            colTitles = JSON.parse(value); // clean & safe now
        }
        if (key === "colWidth") {
        colWidths = JSON.parse(value); // percentages as strings
    }
    });


    let table = document.createElement("table");
    table.id=field.id;
    table.className = "table table-bordered"; // optional bootstrap styling

    let thead = table.createTHead();
    let headerRow = thead.insertRow();

    for (let i = 0; i < noCols; i++) {
        let th = document.createElement("th");
        th.textContent = colTitles[i] ?? ""; // safe fallback
         if (colWidths[i]) {
        th.style.width = colWidths[i] + "%";
        }
        headerRow.appendChild(th);
    }
    let th = document.createElement("th");
    th.textContent =  ""; // final column
    th.style.width =  "5%";
    headerRow.appendChild(th);

    let tbody = table.createTBody();
    let emptyRow = tbody.insertRow();

    for (let i = 0; i < noCols; i++) {
        let td = emptyRow.insertCell();
        let ctrl = renderControl (childFields[i]);
       // td.innerHTML = "&nbsp;";   // keeps row visible
       td.appendChild(ctrl);
    }

    let td = emptyRow.insertCell();
    td.innerHTML = `
  <button type="button"  class="btn btn-sm btn-outline-success me-1" title="Add" onclick="addRow('`+ field.id+`')">
    <i class="bi bi-plus-lg"></i>
  </button>
  <button type="button" class="btn btn-sm btn-outline-danger" title="Remove" onclick="removeRow('`+ field.id+`',this)">
    <i class="bi bi-dash-lg"></i>
  </button>
`;


    return table;


}

function renderControl(field) {
    let el;

    switch (field.control) {

        case 'lookup':
            el = document.createElement('div');

            let inpel = document.createElement("input");
            inpel.type = "text";
            inpel.className = "form-control";
            inpel.dataset.lookup = field.param; // e.g. supplier
            inpel.id = field.id;
            inpel.name = field.fieldKey;
            inpel.dataset.accessor = field.accessor;
            inpel.dataset.dtype = field.dType;
            inpel.placeholder = field.fieldLabel;
            inpel.dataset.typeahead = "1";
            inpel.dataset.param = field.param ?? ""; // store param
            inpel.dataset.fieldKey = field.fieldKey; 


            let datactrl = document.createElement("datalist");
            datactrl.id = 'sgst' + field.fieldKey;
            inpel.setAttribute("list", "sgst" + field.fieldKey);
            inpel.addEventListener(
                "input",
                createTypeaheadHandler(urlPrefix, field.param, field.fieldKey, inpel, datactrl)
            );
            el.appendChild(inpel);
            el.appendChild(datactrl);
            return el;
            break;

        case 'blank':
            el = document.createElement('div');
            return el;
        case 'text':
            el = document.createElement('input');
            el.type = 'text';
            el.className = 'form-control';
           
            if (field.dType === 'Numeric') {
                el.inputMode = 'numeric';
                el.pattern = '[0-9]*';
                el.addEventListener('input', function () {
                    this.value = this.value.replace(/[^0-9]/g, '');
                });
                el.maxLength = 10;
                el.style.width = '25ch';
            }
            break;


        case 'password':
            el = document.createElement('input');
            el.type = 'password';
            el.className = 'form-control';
            break;

        case 'date':
            el = document.createElement('input');
            el.type = 'date';
            el.className = 'form-control';
            break;

        case 'dropdown':
            el = document.createElement('select');
            el.className = 'form-select';

            // async lookup
            populateSelectOptions(el, field.param);
            break;

        case 'hidden':
            el = document.createElement('input');
            el.type = 'hidden';
            break;


        default:
            el = document.createElement('input');
            el.type = 'text';
            el.className = 'form-control';
    }

    // common attributes
    el.id = field.id;
    el.name = field.fieldKey;
    el.dataset.accessor = field.accessor;
    el.dataset.dtype = field.dType;
    el.placeholder = field.fieldLabel;



    return el;
}





async function populateSelectOptions(select, param) {
    const items = await fetchLookupData(param);

    const empty = document.createElement('option');
    empty.value = '';
    empty.textContent = 'Select';
    select.appendChild(empty);

    Object.entries(items).forEach(([key, label]) => {
        const opt = document.createElement('option');
        opt.value = key;
        opt.textContent = label;
        select.appendChild(opt);
    });
}




function renderButtons(metadata, mode) {
    const container = document.createElement('div');
    container.className = 'mt-4 d-flex justify-content-end gap-2';

    metadata.updateViewButtons
        .filter(b => b.isVisible(mode))
        .forEach(btnMeta => {

            const btn = document.createElement('button');
            btn.type = 'button';
            btn.className = btnMeta.buttonClass;
            btn.textContent = btnMeta.innerText;

            btn.addEventListener('click', () => {
                window[btnMeta.jsMethod]?.();
            });

            container.appendChild(btn);
        });

    return container;
}


function onCancel() {

    window.location.href = './genericList.html?entity=' + entity;

}

function onSave() {
    const form = document.getElementById('genericForm');
    const payload = buildJsonFromForm(form);

    console.log(JSON.stringify(payload, null, 2));
    console.log('urlPrefix=' + urlPrefix);

    axios.post(
        urlPrefix + `api/generic/createOrUpdate`,
        payload,
        {
            params: {
                entityType: entity
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }
    )
        .then(response => {
            console.log('Success:', response.data);
            window.location.href = './genericList.html?entity=' + entity;
        })
        .catch(error => {
            console.error('Error:', error);
            const response = error.response?.data;
            const apiErrors = new ApiErrorResponse(response);
            if (apiErrors.hasErrors()) {
                const messages = apiErrors.getMessages();
                showErrors(messages);
            }

        });


}


function traverseJson(formEl, obj, prefix = '') {
    Object.entries(obj).forEach(([key, value]) => {
        const path = prefix ? `${prefix}.${key}` : key;

        if (value !== null && typeof value === 'object' && !Array.isArray(value)) {
            traverseJson(formEl, value, path);
        } else {
            console.log(path, value);
            setValueByAccessor(formEl, path, value);
        }
    });
}


function setValueByAccessor(formEl, accessor, value) {
    const control = formEl.querySelector(`[data-accessor="${accessor}"]`);

    if (!control) return; // field not present on form

    const normalized = normalizeValueForControl(control, value);
    if (!(control instanceof HTMLInputElement) &&
        !(control instanceof HTMLSelectElement) &&
        !(control instanceof HTMLTextAreaElement)) {
        if (normalized != '') {
            control.textContent = normalized;
        } else {
            control.innerHTML = '&nbsp;&nbsp;';           // nothing shown
            // control.className ='text-muted';
        }
    } else if (control.type === 'checkbox') {
        control.checked = Boolean(normalized);
    }
    else if (control.tagName === 'SELECT') {
        control.value = value;
    }
    else {
        control.value = normalized;
    }
}


function normalizeValueForControl(control, value) {
    if (value == null) return '';

    const dtype = control.dataset.dtype;

    // HTML <input type="date">
    if (control.type === 'date' || dtype === 'Date') {
        return typeof value === 'string' ? value.split('T')[0] : '';
    }

    // HTML <input type="datetime-local">
    if (control.type === 'datetime-local' || dtype === 'DateTime') {
        return typeof value === 'string' ? value.substring(0, 16) : '';
    }

    // Numeric
    if (dtype === 'Numeric') {
        const num = Number(value);
        return isNaN(num) ? '' : num;
    }

    return value;
}


function onEdit() {

    window.location.href = './genericaddview.html?entity=' + entity + '&mode=Edit&id=' + id;
}