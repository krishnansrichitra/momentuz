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
          loadDropdownOptions();
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
    form.innerHTML = ''; // clear existing content

    const visibleFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && !f.isHidden());

    const hiddenFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && f.isHidden());

    let html = '';
    let colCount = 0;

    // Start first row
    html += `<div class="row g-3">`;

    visibleFields.forEach((field, index) => {

        // 4 fixed columns per row
        if (colCount === 4) {
            html += `</div><div class="row g-3 mt-2">`;
            colCount = 0;
        }

        html += `
            <div class="col-lg-3 col-md-6 col-sm-12">
                <label class="form-label">${field.fieldLabel}</label>
                ${renderControl(field)}
            </div>
        `;

        colCount++;
    });

    html += `</div>`; // close last row

    // Hidden fields
    hiddenFields.forEach(field => {
        html += renderControl(field);
    });

    // Action buttons
    html += renderButtons(metadata, mode);

    form.innerHTML = html;
}


function renderControl(field) {

    switch (field.control) {

        case 'text':
            return `
                <input type="text"
                       class="form-control"
                       id="${field.id}"
                       name="${field.fieldKey}"
                       data-dType="${field.dType}"
                       data-accessor="${field.accessor}">
            `;

        case 'date':
            return `
                <input type="date"
                       class="form-control"
                       id="${field.id}"
                       name="${field.fieldKey}"
                       data-dType="${field.dType}"
                       data-accessor="${field.accessor}">
            `;

        case 'dropdown':
            return `
                <select class="form-select"
                        id="${field.id}"
                        name="${field.fieldKey}"
                        data-lookup="${field.param}"
                        data-dType="${field.dType}"
                        data-accessor="${field.accessor}">
                    <option value="">Loading...</option>
                </select>
            `;

        case 'hidden':
            return `
                <input type="hidden"
                       id="${field.id}"
                       name="${field.fieldKey}"
                       data-dType="${field.dType}"
                       data-accessor="${field.accessor}">
            `;

        default:
            return '';
        
        }

    }





function populateSelect(select, items) {
    select.innerHTML = `<option value="">Select</option>`;
   console.log(JSON.stringify(items));
   Object.entries(items).forEach(([key, value]) => {
        const opt = document.createElement('option');
        opt.value = key;
        opt.textContent = value;
        select.appendChild(opt);
    });
}

async function loadDropdownOptions() {
    const selects = document.querySelectorAll('select[data-lookup]');
 console.log('selects =' + selects.length);
    for (const select of selects) {
        const lookupKey = select.dataset.lookup;
         
        try {
            const options = await fetchLookupData(lookupKey);
            populateSelect(select, options);
        } catch (e) {
            console.error('Lookup failed', lookupKey, e);
            select.innerHTML = `<option value="">Error loading</option>`;
        }
    }
}

function renderButtons(metadata, mode) {

    const buttons = metadata.updateViewButtons
        .filter(b => b.isVisible(mode))
        .sort((a, b) => a.seqNo - b.seqNo);

    let html = `
        <div class="row mt-4">
            <div class="col text-end">
    `;

    buttons.forEach(btn => {
        html += `
            <button type="button"
                    id="${btn.id}"
                    class="${btn.buttonClass}"
                    onclick="${btn.jsMethod}()">
                ${btn.innerText}
            </button>
        `;
    });

    html += `
            </div>
        </div>
    `;

    return html;
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
})
.catch(error => {
    console.error('Error:', error);
});


}