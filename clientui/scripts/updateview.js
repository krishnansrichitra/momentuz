const entity = params.get("entity");


async function loadMetadata() {
  const url =
    urlPrefix + "api/metadata/getUpdateViewMetadata" +
    "?entity="+entity;

  try {
    const response = await axios.get(url);
    let met = response.data
    console.log("metadata response:", met);
    const updateMetData = new UpdateViewMetadata(met);
       await renderUpdateViewForm(updateMetData,'A');
   //       loadDropdownOptions();
   /* filterFields=listMetadata.filterFields
    renderFilterFields(filterFields, "filter-container");

    const fullData = await loadData(0);   // ✅ works
    console.log("fullData =", fullData);
    listColumns = listMetadata.listColumns;
    renderListTitles(
      listColumns,
      "tbl-listcontent",
      fullData
    );*/
    //   renderPagination();
    //renderButtons(listMetadata.listButtons,"btnListDiv");

  } catch (error) {
    console.error("Error loading metadata or list:", error);
  }
}


async function renderUpdateViewForm(metadata, mode = 'E') {
    const form = document.getElementById('genericForm');
    form.innerHTML = '';

    const visibleFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && !f.isHidden());

    const hiddenFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && f.isHidden());

    let row = createRow();
    let colCount = 0;

    visibleFields.forEach(field => {

        if (colCount === 4) {
            form.appendChild(row);
            row = createRow();
            colCount = 0;
        }

        const col = document.createElement('div');
        col.className = 'col-lg-3 col-md-6 col-sm-12';

        const label = document.createElement('label');
        label.className = 'form-label';
        label.textContent = field.fieldLabel;

        const control = renderControl(field);

        col.appendChild(label);
        col.appendChild(control);

        row.appendChild(col);
        colCount++;
    });

    // append last row
    if (row.children.length > 0) {
        form.appendChild(row);
    }

    // hidden fields
    hiddenFields.forEach(field => {
        form.appendChild(renderControl(field));
    });

    // action buttons
    form.appendChild(renderButtons(metadata, mode));
}


function createRow() {
    const row = document.createElement('div');
    row.className = 'row g-3 mt-2';
    return row;
}

function renderControl(field) {
    let el;

    switch (field.control) {

        case 'lookup':
        el = document.createElement('div');

        let inpel = document.createElement("input");
        inpel.type = "text";
        inpel.className="form-control";
        inpel.dataset.lookup = field.param; // e.g. supplier
        inpel.id = field.id;
        inpel.name = field.fieldKey;
        inpel.dataset.accessor = field.accessor;
        inpel.dataset.dtype = field.dType;
        inpel.placeholder = field.fieldLabel;
       

        let datactrl = document.createElement("datalist");
        datactrl.id = 'sgst' + field.fieldKey;
        inpel.setAttribute("list", "sgst" + field.fieldKey);
        inpel.addEventListener(
         "input",
            createTypeaheadHandler(urlPrefix,field.param, field.fieldKey, inpel, datactrl)
            );
        el.appendChild(inpel);
        el.appendChild(datactrl);
        return el;
            break;

        case 'text':
            el = document.createElement('input');
            el.type = 'text';
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


function onCancel()
{

    window.location.href = './genericList.html?entity=' + entity ;

}

function onSave()
{
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
     window.location.href = './genericList.html?entity=' + entity ;
})
.catch(error => {
    console.error('Error:', error);
});


}