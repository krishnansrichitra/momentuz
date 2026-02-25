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


    }else {
      const url =
       urlPrefix + "api/lookup/getObjectValues?entity=" + encodeURIComponent(lookupKey);
     const response = await axios.get(url);
      const data = response.data;
    
        return data;

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

function ctrlsInTableRow(row)
{
    if (!row) return [];
    return Array.from(
        row.querySelectorAll(
            'input, select, textarea, span[data-accessor]'
        )
    )


}

function buildJsonFromForm(formEl) {
    const result = {};

    const controls = formEl.querySelectorAll('[data-accessor]');

    controls.forEach(control => {
        const accessor = control.dataset.accessor;
        if (!accessor) return;
        if(control.tagName === "TABLE") {
            let table = control;
            console.log('inside subTable' + table.rows.length) ;
            
            let subObjectList =[];
            for (let i = 0; i < table.rows.length-1; i++ ){
                console.log(i);
                let tablerow = table.rows[i+1];
                let innerCtrls = ctrlsInTableRow(tablerow) ;
                let subObject= {};
                for ( let innerCtrl of innerCtrls){
                  
                    const value = getControlValue(innerCtrl);
                    const inncerAccessor = innerCtrl.dataset.accessor;
                    const inncerAccessorstr = inncerAccessor.substring(inncerAccessor.indexOf('.') + 1);
                    const typedValue = applyDataType(innerCtrl, value);
                     console.log(typedValue);
                    setNestedValue(subObject, inncerAccessorstr, typedValue);
                   
                }
                
            subObjectList.push(subObject);
            }
            setNestedValue(result, accessor, subObjectList);
            return;
             
              // getRows 
              // iterate on row 
              // getControl and apply dataType
              //return ; 

        }else if (control.tagName === "UL") {
            let typedValue =  getCheckedValuesAsCSV(control);
            setNestedValue(result, accessor, typedValue);
            return;
    
        }
        
        if (control.dataset.subobject !== undefined && control.dataset.subobject  === 'true'){
                    return;
        }

        const value = getControlValue(control);
        //if (value === undefined || value === null) return;
        const typedValue = applyDataType(control, value);
        //if (typedValue === undefined || typedValue === null) return;


        setNestedValue(result, accessor, typedValue);
    });

    return result;
}


function getCheckedValuesAsCSV(ulEl) {
    return Array.from(
        ulEl.querySelectorAll('input[type="checkbox"]:checked')
    )
    .map(cb => cb.value)
    .join(',');
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
                ? value
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
    if( tag === 'textarea')
    {
        console.log(control.value);
        console.log(control.innerHTML);
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
    console.log(messages);

    const spanMessage = document.getElementById('infoMessage');    

    spanMessage.innerHTML = `
        <ul class="mb-0 ps-3">
            ${messages.map(m => `<li style='color:blue'> ${m}</li>`).join('')}
        </ul>
    `;
    container.style.display = 'block';
}


function showErrors(messages) {
    const container = document.getElementById('errorContainer');
    const span = document.getElementById('errorMessage');

    span.innerHTML = `
        <ul class="ps-3 mb-0">
            ${messages.map(m => `<li style='color:red' >${m}</li>`).join('')}
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
    console.log(response);
    const data = response.data;
    return data;
    }catch (error) {
    console.error("Error loading dropdown:", error);
  }
}


function loadScript(src) {
    return new Promise((resolve, reject) => {
        const script = document.createElement("script");
        script.src = src;
        script.async = true;

        script.onload = () => resolve(src);
        script.onerror = () => reject(new Error(`Failed to load ${src}`));

        document.head.appendChild(script);
    });
}


function clearAccessorControls() {
    let formEl =document.getElementById("genericForm");

    const controls = formEl.querySelectorAll('[data-accessor]');

    controls.forEach(ctrl => {

        // INPUTS
        if (ctrl instanceof HTMLInputElement) {

            if (ctrl.type === 'checkbox' || ctrl.type === 'radio') {
                ctrl.checked = false;
            } else {
                ctrl.value = '';
            }
        }

        // TEXTAREA
        else if (ctrl instanceof HTMLTextAreaElement) {
            ctrl.value = '';
        }

        // SELECT
        else if (ctrl instanceof HTMLSelectElement) {
            ctrl.selectedIndex = -1;   // unselect
        }

        // UL (your multiselect checkboxes)
        else if (ctrl instanceof HTMLUListElement) {
            ctrl.querySelectorAll('input[type="checkbox"]').forEach(cb => cb.checked = false);
        }

        // OTHER DISPLAY ELEMENTS
        else {
            ctrl.textContent = '';
        }
    });
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