const entity = params.get("entity");
const mode = params.get("mode");
const id = params.get("id");



async function loadMetadata() {
    const url =
        urlPrefix + "api/metadata/getUpdateViewMetadata" +
        "?entity=" + entity + '&mode=' + mode;

    try {
        const response = await axios.get(url);
        let met = response.data;
       // console.log("metadata response:", met);
        const updateMetData = new UpdateViewMetadata(met);
        if (mode == 'Add') {
            await renderUpdateViewForm(updateMetData, 'A');
        } else if (mode == 'Edit') {

            await renderUpdateViewForm(updateMetData, 'E');
            let jsonContent = await fetchDataByEntityAndId(entity, id);
            let formControl = document.getElementById("genericForm");
            if(id!==null)
                traverseJson(formControl, jsonContent);
        } else if (mode == 'View') {

            await renderUpdateViewForm(updateMetData, 'V');
            let jsonContent = await fetchDataByEntityAndId(entity, id);
            let formControl = document.getElementById("genericForm");
            traverseJson(formControl, jsonContent);
        }
        console.log(updateMetData.jsFile );
        if (updateMetData.jsFile !== null) {
            const fileName = updateMetData.jsFile;
            console.log(fileName);
            loadScript(fileName)
                .then(() => {
                    console.log("Script loaded");
                    // you can safely call functions from the loaded file here
                })
                .catch(err => console.error(err));
        }

    } catch (error) {
        console.error("Error loading metadata or list:", error);
    }
}

function getChildren(currentField, allFields,mode) {
    let childArr = allFields.filter(field => field.parent === currentField.id  && field.isVisible(mode)  );
    //console.log('childArr=' + JSON.stringify(childArr));
    return childArr;

}

async function drawTabsAndChildren(container,tabCtrl, tabs, visibleFields,mode )
{
    const tabset = document.createElement("ul");
    tabset.className = "nav nav-tabs";
    tabset.role = "tablist";
    tabset.id = tabCtrl.id;
   
     const tabContent = document.createElement("div");
    tabContent.className = "tab-content mt-3";

     for (const [index, tab] of tabs.entries()) {

        const tabId = "tab-" + tab.id;

        /* ---------------- TAB HEADER ---------------- */
        const li = document.createElement("li");
        li.className = "nav-item";

        const btn = document.createElement("button");
        btn.className = "nav-link" + (index === 0 ? " active" : "");
        btn.setAttribute("data-bs-toggle", "tab");
        btn.setAttribute("data-bs-target", "#" + tabId);
        btn.type = "button";
        btn.role = "tab";
        btn.textContent = tab.fieldLabel;

        li.appendChild(btn);
        tabset.appendChild(li);

        /* ---------------- TAB BODY ---------------- */
        const pane = document.createElement("div");
        pane.className = "tab-pane fade" + (index === 0 ? " show active" : "");
        pane.id = tabId;
        pane.role = "tabpanel";

        // placeholder content (you will fill dynamically later)
       // pane.innerHTML = `<div class="p-2">Content for ${tab.fieldLabel}</div>`;
        let childFields = getChildren(tab, visibleFields,mode);
        let row = createRow();
        let colCount = 0;
        for (let field of childFields) {
            if (colCount === 4) {
                tabContent.appendChild(row);
                row = createRow();
                colCount = 0;
            }
            if (field.control == 'table') {
                console.log('creating table inside Tab');
                const col = document.createElement('div');
                col.className = 'col-lg-12 col-md-12 col-sm-12';
                let childFields = getChildren(field, visibleFields,mode);
                const control = await createTable(field, childFields, mode);
              //  console.log(typeof(control));
                col.appendChild(control);
                pane.append(col);
                continue;
            } else {

                const col = document.createElement('div');
                col.className = 'col-lg-3 col-md-6 col-sm-12';
                const label = document.createElement('label');
                label.className = 'form-label';
                if (typeof field.fieldLabel === 'string' && field.fieldLabel.trim() !== '')
                    label.textContent = field.fieldLabel + ":";
                if (mode != 'V') {
                    const control = await renderControl(field);
                    label.classList.add('form-label', 'mb-1');
                    if(field.control !== 'button'){
                      col.appendChild(label);
                    }else {
                        const spacer = document.createElement("div");
                        spacer.className = "field-spacer";
                        col.appendChild(spacer);
                    }
                    col.appendChild(control);
                } else {
                    const control = await renderViewControl(field);
                    label.classList.add('form-label', 'mb-1');
                    control.classList.add('form-control');
                    if(field.control !== 'button'){
                      col.appendChild(label);
                    }else {
                        const spacer = document.createElement("div");
                        spacer.className = "field-spacer";
                        col.appendChild(spacer);
                    }
                    col.appendChild(control);
                }
                row.appendChild(col);
                colCount++;
            }

        }

        tabContent.appendChild(pane);
        if (row.children.length > 0) {
            tabContent.appendChild(row);
        }
    }

    container.appendChild(tabset);
    container.appendChild(tabContent);


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

    for (let field of visibleFields) {
        if (field.parent != null) continue;
        if (colCount === 4) {
            form.appendChild(row);
            row = createRow();
            colCount = 0;
        }

        if (field.control == 'tabset'){
            console.log('creating tabset');
            if (colCount != 0) {
                form.appendChild(row);
                row = createRow();
            }
            const col = document.createElement('div');
            col.className = 'col-lg-12 col-md-12 col-sm-12';
             let childFields = getChildren(field, metadata.updateViewFields,mode);
             await drawTabsAndChildren(col,field,childFields ,metadata.updateViewFields,mode);
            colCount = 4;
            console.log('tabset =' + col);
            row.appendChild(col);
            continue;
        }

        if (field.control == 'table') {
            console.log('creating table');
            if (colCount != 0) {
                form.appendChild(row);
                row = createRow();
            }
            const col = document.createElement('div');
            col.className = 'col-lg-12 col-md-12 col-sm-12';
            let childFields = getChildren(field, metadata.updateViewFields,mode);
            const control = await createTable(field, childFields, mode);
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
            const control =await  renderControl(field);
            label.classList.add('form-label', 'mb-1');
            if(field.control !== 'button'){
                      col.appendChild(label);
            }else {
                const spacer = document.createElement("div");
                spacer.className = "field-spacer";
                col.appendChild(spacer);
            }

            col.appendChild(control);
        } else {
            const control = renderViewControl(field);
            label.classList.add('form-label', 'mb-1');
            control.classList.add('form-control');
            if(field.control !== 'button'){
                      col.appendChild(label);
            }else {
                const spacer = document.createElement("div");
                spacer.className = "field-spacer";
                col.appendChild(spacer);
            }
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
   await  hiddenFields.forEach(async field => {
        form.appendChild(await renderControl(field));
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

async function createTable(field, childFields, mode) {

    let params = field.param;
    let noCols;
    let colTitles = [];
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
    table.id = field.id;
    table.className = "table table-bordered table-striped table-compact"; // optional bootstrap styling 
    table.style =field.style;
    table.dataset.accessor = field.accessor;
    
    const colgroup = document.createElement("colgroup");
    for (let i = 0; i < noCols; i++) {
        const col = document.createElement("col");
        col.style.width =colWidths[i] + "%";
        colgroup.append(col);
    }
      const col = document.createElement("col");
       col.style.width = "5%";  
       colgroup.append(col);
      table.appendChild(colgroup);

    let thead = table.createTHead();
    let headerRow = thead.insertRow();

    for (let i = 0; i < noCols; i++) {
        let th = document.createElement("th");
        th.textContent = colTitles[i] ?? ""; // safe fallback
        if (colWidths[i]) {
            th.style.width = colWidths[i] + "%";
            th.classList.add("table-primary");
        }
        headerRow.appendChild(th);
    }
    let th = document.createElement("th");
    th.textContent = ""; // final column
    th.style.width = "5%";
    th.classList.add("table-primary");
    headerRow.appendChild(th);

    let tbody = table.createTBody();
    let emptyRow = tbody.insertRow();
    let hiddenCtrl=[];

    for (let i = 0; i < childFields.length; i++) {
        if (childFields[i].control !== 'hidden') {
            let td = emptyRow.insertCell();
            console.log(mode);
            if (mode != 'V') {
                let ctrl =await renderControl(childFields[i], true);
                td.appendChild(ctrl);
            } else {
                let ctrl = await renderViewControl(childFields[i], true);
                td.appendChild(ctrl);
            }
        }else{
            hiddenCtrl.push(childFields[i]);
        }
    }


    let td = emptyRow.insertCell();
    if (mode != 'V') {
        while (hiddenCtrl.length > 0) {
            const childField = hiddenCtrl.pop();
            if (childField) {
                let ctrl = await renderControl(childField, true);
                td.appendChild(ctrl);
            }
        }

        td.innerHTML = td.innerHTML +   `
    <button type="button"  class="btn btn-sm btn-outline-success me-1" title="Add" onclick="addRow('`+ field.id + `')">
        <i class="bi bi-plus-lg"></i>
    </button>
    <button type="button" class="btn btn-sm btn-outline-danger" title="Remove" onclick="removeRow('`+ field.id + `',this)">
        <i class="bi bi-dash-lg"></i>
    </button>
    `;
    }


    return table;


}

async function renderControl(field, partofTable=false) {
    let el;

    switch (field.control) {
       

        case 'button':
            const btn = document.createElement('button');
            btn.type = 'button';
            btn.id =field.id;
            btn.className ='btn btn-info';
            btn.textContent = field.fieldLabel;
            console.log(field.param);
            btn.addEventListener('click', () => {
                window[field.param]?.();
            });
            return btn;





        case 'lookup':
            el = document.createElement('div');

            let inpel = document.createElement("input");
            inpel.type = "text";
            inpel.className = "form-control";
            inpel.dataset.lookup = field.param; // e.g. supplier
            inpel.id = field.id;
            inpel.name = field.id;
            inpel.dataset.accessor = field.accessor;
            inpel.dataset.dtype = field.dType;
            inpel.placeholder = field.fieldLabel;
            inpel.dataset.typeahead = "1";
            inpel.dataset.param = field.param ?? ""; // store param
            inpel.dataset.fieldKey = field.fieldKey;
            if (partofTable == true) {
                inpel.dataset.subobject=true;
            }

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

       case 'msdropdown':
           el = document.createElement('div');
           el.className='dropdown';
           el.style="position: static; display: block;";
           let innerul = document.createElement('ul')
           innerul.id= field.id;
           innerul.className='dropdown-menu w-100 p-2 show';
           innerul.style=field.style
           innerul.dataset.accessor = field.accessor;
           innerul.dataset.dtype = field.dType;
           el.appendChild(innerul);
           console.log('adding sub iuio' +  innerul.id);
           return el;
         
        case 'dropdown':
            el = document.createElement('select');
            el.className = 'form-select';

            // async lookup
            await populateSelectOptions(el, field.param);
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
    el.name = field.id;
    el.dataset.accessor = field.accessor;
    el.dataset.dtype = field.dType;
    el.placeholder = field.fieldLabel;
    if (partofTable == true) {
        el.dataset.subobject = true;
    }



    return el;
}





async function populateSelectOptions(select, param) {
    const items = await fetchLookupData(param);

    const empty = document.createElement('option');
    empty.value = '';
    empty.textContent = 'Select';
    select.appendChild(empty);
    if (items !== null) {

        Object.entries(items).forEach(([key, label]) => {
            const opt = document.createElement('option');
            opt.value = key;
            opt.textContent = label;
            select.appendChild(opt);
        });
    }
   console.trace();

    console.log('Added options');
}




function renderButtons(metadata, mode) {
    const container = document.createElement('div');
    container.className = 'mt-4 d-flex justify-content-end gap-2';

    metadata.updateViewButtons
        .filter(b => b.isVisible(mode))
        .forEach(btnMeta => {

            const btn = document.createElement('button');
            btn.type = 'button';
            btn.id=btnMeta.id;
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


/**
 * Iterates all controls in a table row that have data-accessor,
 * finds matching values in jsonData, and (optionally) writes them into the controls.
 *
 * @param {HTMLTableRowElement} rowEl - <tr> element
 * @param {object} jsonData - source JSON to look values up from
 * @param {boolean} setValues - if true, writes found values into controls
 */
function applyRowFromJson(rowEl, jsonData, setValues = true) {
    // Iterate columns (cells)
    for (let c = 0; c < rowEl.cells.length; c++) {
        const cell = rowEl.cells[c];

        // Find any form controls inside this cell with data-accessor
        const controls = cell.querySelectorAll("input[data-accessor], select[data-accessor], textarea[data-accessor], span[data-accessor]");

        controls.forEach(ctrl => {
            let accessor = ctrl.dataset.accessor; // value of data-accessor="..."
           // console.log('accessor=' + accessor);
            if (!accessor) return;
            accessor = accessor.substring(accessor.indexOf(".") + 1);


            // Look up value in jsonData (supports dot paths like "address.city")
            const value = getByPath(jsonData, accessor);
             
            //console.log(`col=${c}, accessor=${accessor}, value=`, value);

            if (!setValues) return;
            //console.log(ctrl.tagName);
            // Apply value to control based on type
            if (ctrl.tagName === "SPAN") {
                //console.log('span control ' + value);
                ctrl.innerHTML = value;
            } else if (ctrl.type === "checkbox") {
                ctrl.checked = Boolean(value);
            } else if (ctrl.type === "radio") {
                ctrl.checked = String(ctrl.value) === String(value);
            } else {
               console.log('value=' + value);

                ctrl.value = value ?? "";
            }
        });
    }
}

/**
 * Gets nested value from object using dot-path keys.
 * Example: getByPath(obj, "a.b.c")
 */
function getByPath(obj, path) {
    if (!obj || !path) return undefined;
    return path.split(".").reduce((acc, key) => (acc != null ? acc[key] : undefined), obj);
}


function traverseJson(formEl, obj, prefix = '') {
     for (const [key, value] of Object.entries(obj || {}))  {
        const path = prefix ? `${prefix}.${key}` : key;

        if (value !== null && typeof value === 'object' && !Array.isArray(value)) {
            traverseJson(formEl, value, path);
        } else {
            if (Array.isArray(value)) {
                console.trace();
                console.log('can be an array' + path + " and " + value.length);
                
                let tabl = formEl.querySelector(`[data-accessor="${path}"]`);
                if (value.length === 0 || tabl == null){
                   // console.log('table lenght = ' + tabl.rows.length );
                    return;
                }
                for (let i = 0; i < value.length; i++) {
                    const obj = value[i];
                    applyRowFromJson(tabl.rows[i + 1], obj);
                    addRow(tabl.id);
                }
                if (tabl.rows.length > 2) {
                    tabl.deleteRow(tabl.rows.length - 1);
                    return;
                }
            }
            setValueByAccessor(formEl, path, value);
        }
    }
}


function setValueByAccessor(formEl, accessor, value) {
    const control = formEl.querySelector(`[data-accessor="${accessor}"]`);

    if (!control) return; // field not present on form

    const normalized = normalizeValueForControl(control, value);
    if (!(control instanceof HTMLInputElement) &&
        !(control instanceof HTMLSelectElement) &&
        !(control instanceof HTMLTextAreaElement) &&
        !(control instanceof HTMLUListElement)) {

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
        console.log(control.options.length);
        control.value = value;
    } else if (control.tagName === 'UL') {
        console.log(normalized);
        applyMultiSelectFromSemicolon(control, normalized);

    } else {
        control.value = normalized;
    }
}



function applyMultiSelectFromSemicolon(ulEl, semicolonValue) {
  // Build a Set of selected values (fast lookup)
  const selected = new Set(
    (semicolonValue || "")
      .split(",")
      .map(s => s.trim())
      .filter(s => s.length > 0)
  );
  console.log(selected);
  // Iterate checkboxes under the UL
  ulEl.querySelectorAll('input[type="checkbox"]').forEach(cb => {
    cb.checked = selected.has(cb.value);
  });
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