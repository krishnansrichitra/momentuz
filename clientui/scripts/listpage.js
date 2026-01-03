const iframeLocation = window.location;

// Query params from iframe URL
const params = new URLSearchParams(iframeLocation.search);

const entity = params.get("entity");
const PAGE_SIZE = 10;
let listColumns = [];
let currentPage = 1;
let filterFields =[]


// Base URL (protocol + host + port)
const urlPrefix = (iframeLocation.origin + "/").replace("5500","8080");

console.log(entity);    // Item
console.log(urlPrefix); // http://localhost:8080/


function getFilter()
{

    let filterData = {};
    filterFields.forEach ( filterField =>  { 
       let selValue =  document.getElementById(filterField.fieldKey).value;
       if (selValue != '' && selValue != null) {
          if (selValue != '-1' ||  filterField.control != 'dropdown')
             filterData[normalizeAccessor(filterField.accessor)] = selValue ;
       } 

    });
    return filterData;


}


async function clearFilter() {
   
    console.log('clearing all filter');
    filterFields.forEach ( filterField =>  { 
          if (filterField.control == 'dropdown')
             document.getElementById(filterField.fieldKey).value= '-1' ;
          else
            document.getElementById(filterField.fieldKey).value = '';

    });
        // TODO: implement filtering logic or backend call
    const fullData = await loadData(0);   // ✅ works
    renderListTitles(
      listColumns,
      "tbl-listcontent",
      fullData
    );
       renderPagination();
    }


async function applyFilter() {
 
        // TODO: implement filtering logic or backend call
    const fullData = await loadData(0);   // ✅ works
    console.log("fullData After Filter =", fullData);
    renderListTitles(
      listColumns,
      "tbl-listcontent",
      fullData
    );
       renderPagination();
    }





    async function loadData(offset) {
  const url =
    urlPrefix +"api/generic/listRecords" +
    "?entityType=" + entity +
    "&limit=" + PAGE_SIZE +
    "&offset=" + offset;

  try {
    console.log('filter condition = ' +JSON.stringify(getFilter()));
    const response = await axios.post(url, getFilter());
    console.log("list response:", response.data);
    return response.data; // ✅ returned Promise resolves here
  } catch (error) {
    console.error("Error loading supplier list:", error);
    throw error; // important
  }
}

function normalizeAccessor(expr) {
  if (!expr || typeof expr !== "string") return expr;

  // Base case: no brackets left
  if (!expr.includes("[")) {
    return expr;
  }

  // Replace innermost bracket expression first
  const replaced = expr.replace(
    /(\w+)\[(.+?)\]/,
    (match, parent, inner) => {
      const normalizedInner = normalizeAccessor(inner);

      // remove quotes if present
      const cleanInner = normalizedInner
        .replace(/^["']|["']$/g, "");

      return `${parent}.${cleanInner}`;
    }
  );

  // Recurse until fully normalized
  return normalizeAccessor(replaced);
}


function getValueByAccessor(row, accessor) {
  if (!row || !accessor) return null;

  try {
    // Convert bracket notation to dot notation
    // supplier["supplierName"] -> supplier.supplierName
    // itemGroup["fvValue"] -> itemGroup.fvValue
    const normalizedAccessor = accessor
      .replace(/\["([^"]+)"\]/g, ".$1")
      .replace(/\['([^']+)'\]/g, ".$1");

    return normalizedAccessor
      .split(".")
      .reduce((obj, key) => {
        return obj && obj[key] !== undefined ? obj[key] : null;
      }, row);

  } catch (e) {
    console.error("Invalid accessor:", accessor, e);
    return null;
  }
}


async function loadMetadata() {
  const url =
    urlPrefix + "api/metadata/getListMetadata" +
    "?entity="+entity;

  try {
    const response = await axios.get(url);
    console.log("metadata response:", response.data);

    const listMetadata = new ListMetadata(response.data);
    filterFields=listMetadata.filterFields
    renderFilterFields(filterFields, "filter-container");

    const fullData = await loadData(0);   // ✅ works
    console.log("fullData =", fullData);
    listColumns = listMetadata.listColumns;
    renderListTitles(
      listColumns,
      "tbl-listcontent",
      fullData
    );
       renderPagination();

  } catch (error) {
    console.error("Error loading metadata or list:", error);
  }
}






  function renderListTitles(listColumns,containerId,fullData)
  {
    const container = document.getElementById(containerId);
    if (!container) {
        console.log(" no contaioner");
        return;
    }
      container.innerHTML = ""; 
      const theadcontent = document.createElement("thead");
      theadcontent.className="table-light";
      let innerCnt = "<tr><th width=\"5%\">Select</th>" ;
      listColumns.forEach(field=>{
      innerCnt+='<th>' + field.fieldLabel +'</th>';
      });
      innerCnt+='</tr>';
      theadcontent.innerHTML=innerCnt;
      container.appendChild(theadcontent);
     
      const tbodyContent = document.createElement("tbody");

      fullData.forEach( rowData => {
        const trowContent = document.createElement("tr")
        let innerrow = '<td width="5%"><input type="checkbox" class="form-check-input">';
        innerrow+= '<input type="hidden" value="' + rowData['id'] +'"></td>';
          listColumns.forEach(field => {
            const value = getValueByAccessor(rowData, field.accessor);
            innerrow += `<td>${value ?? ''}</td>`;
        });
        trowContent.innerHTML = innerrow;
        tbodyContent.appendChild(trowContent);
      });
      container.appendChild(tbodyContent);


  }


  function renderFilterFields(filterFields, containerId) {
  const container = document.getElementById(containerId);
  if (!container) {
    console.log(" no contaioner")
    return;
  }

  console.log(" filter fields ");
  container.innerHTML = ""; // clear previous content

  const row = document.createElement("div");
  row.className = "row g-3"; 

  filterFields.forEach(field => {
    const wrapper = document.createElement("div");
    wrapper.className = "col-12 col-md-3";

    /*const label = document.createElement("label");
    label.className="form-label";
    label.innerText = field.fieldLabel;
    label.htmlFor = field.fieldKey; */

    let control;
    switch (field.control) {
      case "text":
        control = document.createElement("input");
        control.type = "text";
        control.className="form-control";
        control.placeholder = field.fieldLabel;
        control.id = field.fieldKey;
        break;

      case "dropdown":
        control = document.createElement("select");
        control.id = field.fieldKey;
        control.className = "form-control";
        control.dataset.param = field.param;
        control.multiple=false;

        // Default option
        const defaultOption = document.createElement("option");
        defaultOption.value = "-1";
        defaultOption.text = field.fieldLabel;
        defaultOption.disabled = false;   // optional (recommended)
        defaultOption.selected = true;

        const secOption = document.createElement("option");
        secOption.value = "0";
        secOption.text = "test"

        control.appendChild(defaultOption); // e.g. fv::item_group
        control.appendChild(secOption);
        break;

      case "lookup":
        control = document.createElement("input");
        control.type = "text";
        control.id = field.fieldKey;
        control.placeholder = field.fieldLabel;
        control.className="form-control";
        control.dataset.lookup = field.param; // e.g. supplier
        break;

      default:
        console.warn("Unsupported control:", field.control);
        return;
    }

   // wrapper.appendChild(label);
    wrapper.appendChild(control);
    row.appendChild(wrapper);
  });
   
// apply button
  {
    const wrapper = document.createElement("div");
    wrapper.className = "col-md-3";


    let control;
    control = document.createElement("button");
    control.className =" btn btn-primary w-40" ;
   control.addEventListener("click", applyFilter);
    control.innerHTML="Apply";


    let control2;
    control2 = document.createElement("button");
    control2.className =" btn btn-info w-40" ;
    control2.addEventListener("click", clearFilter);
    control2.innerHTML="Clear";
  
     wrapper.appendChild(control);
     wrapper.appendChild(document.createTextNode("\u00A0\u00A0"));

     wrapper.appendChild(control2);

    row.appendChild(wrapper);
  }

  container.appendChild(row);
}


  

  async function renderPagination() {
  const totalCount = await getTotalCt();
  const totalPages = Math.ceil(totalCount / PAGE_SIZE);

  const pagination = document.getElementById("pagination-container");
  const info = document.getElementById("pagination-info");

  pagination.innerHTML = "";

  // Info text
  const start = (currentPage - 1) * PAGE_SIZE + 1;
  const end = Math.min(currentPage * PAGE_SIZE, totalCount);
  info.textContent = `Showing ${start}-${end} of ${totalCount}`;

  /* ---------- Previous ---------- */
  pagination.appendChild(
    createPageItem(
      "Previous",
      currentPage - 1,
      currentPage === 1
    )
  );

  /* ---------- Page Numbers ---------- */
  for (let page = 1; page <= totalPages; page++) {
    pagination.appendChild(
      createPageItem(
        page,
        page,
        false,
        page === currentPage
      )
    );
  }

  /* ---------- Next ---------- */
  pagination.appendChild(
    createPageItem(
      "Next",
      currentPage + 1,
      currentPage === totalPages
    )
  );
}


 function createPageItem(label, page, disabled = false, active = false) {
  const li = document.createElement("li");
  li.className = "page-item";

  if (disabled) li.classList.add("disabled");
  if (active) li.classList.add("active");

  const a = document.createElement("a");
  a.className = "page-link";
  a.href = "#";
  a.innerText = label;

  a.addEventListener("click", e => {
    e.preventDefault();
    if (disabled || active) return;

    currentPage = page;

    const offset = (currentPage - 1) * PAGE_SIZE;
    onPageChange(offset, PAGE_SIZE);

    renderPagination();
  });

  li.appendChild(a);
  return li;
}


  async function onPageChange(offset, limit) {
    console.log("Pagination clicked → offset:", offset, "limit:", limit);
     const fullData = await loadData(offset); 
     console.log(fullData);
     renderListTitles(
      listColumns,
      "tbl-listcontent",
      fullData
    );

    // Placeholder: call your API here
    // loadData(offset, limit);
  }

  async function getTotalCt() {

      const url =
    urlPrefix +"api/generic/getRecordCount" +
    "?entityType=" + entity 

  try {
    const response = await axios.post(url, getFilter());
    console.log("list response:", response.data);
    return response.data['count']; 
  } catch (error) {
    console.error("Error loading supplier list:", error);
    throw error; // important
  }
  }