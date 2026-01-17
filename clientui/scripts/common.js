async function loadFvDropdown(urlPrefix,selectId, fvGroup) {
  const url =
    urlPrefix + "api/lookup/fvdropdowns?fvGroup=" + encodeURIComponent(fvGroup);

  try {
    const response = await axios.get(url);
    const data = response.data;

    const select = document.getElementById(selectId);
    select.innerHTML = "";

    // Default option
    const defaultOption = document.createElement("option");
    defaultOption.value = "-1";
    defaultOption.textContent = "Select";
    defaultOption.disabled =false;
    defaultOption.selected=true;
    select.appendChild(defaultOption);

    // Populate from response
    Object.entries(data).forEach(([key, value]) => {
      const option = document.createElement("option");
      option.value = key;     // itmgrp_raw
      option.textContent = value; // Raw Material
      select.appendChild(option);
    });

  } catch (error) {
    console.error("Error loading dropdown:", error);
  }
}

async function fetchLookupData(lookupKey) {
    if (lookupKey == 'country' || lookupKey == 'cntry') {
        const url =
            urlPrefix + "api/common/getAllCountries";
      const response = await axios.get(url);
      const data = response.data;
    
        return data;

    }else if(lookupKey.startsWith('fv::')) {
      const fvGroup=lookupKey.substring(4);
      const url =
    urlPrefix + "api/lookup/fvdropdowns?fvGroup=" + encodeURIComponent(fvGroup);
     const response = await axios.get(url);
      const data = response.data;
    
        return data;
         console.log(data);


    }

    return null;
    /*const res = await fetch(`/api/lookups/${lookupKey}`);
    return await res.json();*/
}

function createTypeaheadHandler(urlPrefix, entity, field, input, datalist) {
  let timer = null;

  return function () {
    const value = input.value.trim();
    datalist.innerHTML = "";
   console.log('calling search');
    if (value.length < 3) return;

    clearTimeout(timer);

    timer = setTimeout(async () => {
      const url =
        urlPrefix + 'api/lookup/typeaheadsearch'
        + `?entity=${entity}`
        + `&field=${field}`
        + `&value=${encodeURIComponent(value)}`;

      const response = await axios.get(url);

      response.data.forEach(item => {
        const option = document.createElement("option");
        option.value = item;
        datalist.appendChild(option);
      });
    }, 300);
  };
}


function buildJsonFromForm(formEl) {
    const result = {};

    const controls = formEl.querySelectorAll('[data-accessor]');

    controls.forEach(control => {
        const accessor = control.dataset.accessor;
        if (!accessor) return;

        const value = getControlValue(control);

        //if (value === undefined || value === null) return;
        
        const typedValue = applyDataType(control, value);
        //if (typedValue === undefined || typedValue === null) return;


        setNestedValue(result, accessor, typedValue);
    });

    return result;
}

function applyDataType(control, value) {
    const dtype = control.dataset.dtype;

    if (value === null || value === '') return null;

    switch (dtype) {

        case 'Numeric': {
            const num = Number(value);
            return isNaN(num) ? null : num;
        }

        case 'Date':
            // Keep date-only (maps to LocalDate in backend)
           // return value; // yyyy-MM-dd

            return  value
                ? (value.includes('T') ? value : `${value}T00:00:00`)
                : null;

        case 'DateTime':
            // Convert date-only to full ISO datetime
            // HTML date inputs give yyyy-MM-dd
            return  value
                ? (value.includes('T') ? value : `${value}T00:00:00`)
                : null;

        default:
            return value; // String
    }
}




function getControlValue(control) {
    const tag = control.tagName.toLowerCase();
    const type = control.type;

    if (tag === 'input') {
        if (type === 'checkbox') {
            return control.checked;
        }
        if (type === 'radio') {
            return control.checked ? control.value : undefined;
        }
        return control.value;
    }

    if (tag === 'select' || tag === 'textarea') {
        return control.value;
    }

    return undefined;
}


function setNestedValue(obj, path, value) {
    const keys = path.split('.');
    let current = obj;

    keys.forEach((key, index) => {
        if (index === keys.length - 1) {
            current[key] = value;
        } else {
            if (!current[key] || typeof current[key] !== 'object') {
                current[key] = {};
            }
            current = current[key];
        }
    });
}

function showSuccessMessage(messages) {
    const container = document.getElementById('errorContainer');
    const spanError = document.getElementById('errorMessage');
    spanError.innerHTML='';

    const spanMessage = document.getElementById('infoMessage');    

    spanMessage.innerHTML = `
        <ul class="mb-0 ps-3">
            ${messages.map(m => `<li>${m}</li>`).join('')}
        </ul>
    `;
    container.style.display = 'block';
}


function showErrors(messages) {
    const container = document.getElementById('errorContainer');
    const span = document.getElementById('errorMessage');

    span.innerHTML = `
        <ul class="mb-0 ps-3">
            ${messages.map(m => `<li>${m}</li>`).join('')}
        </ul>
    `;
     container.style.display = 'block';
    const spanMessage = document.getElementById('infoMessage');    
    spanMessage.innerHTML='';
   
}

function showErrorFromUI(message) {
    const container = document.getElementById('errorContainer');
    const span = document.getElementById('errorMessage');

    span.innerHTML = `
        <ul class="mb-0 ps-3">
            ${message}
        </ul>
    `;
    container.style.display = 'block';
}
function clearErrors() {
    document.getElementById('errorMessage').innerHTML = '';
    document.getElementById('errorContainer').style.display = 'none';
}

async function fetchDataByEntityAndId(entity, id)
{
   const url =
    urlPrefix + "api/generic/getById?entityType=" + encodeURIComponent(entity) + "&id=" + id ;
    try {
    const response = await axios.get(url);
    const data = response.data;
    return data;
    }catch (error) {
    console.error("Error loading dropdown:", error);
  }
}


