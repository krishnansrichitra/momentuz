    function applyFilter() {
        const name = document.getElementById("filterName").value;
        const phone = document.getElementById("filterPhone").value;

        console.log("Filter applied:", { name, phone });
        // TODO: implement filtering logic or backend call
    }





    function loadData() {
    const url = "http://localhost:8080/api/generic/listRecords"
        + "?entityType=Supplier"
        + "&limit=20"
        + "&offset=0";

    axios.post(url, {})
        .then(response => {
            console.log("Supplier list response:", response.data);
        })
        .catch(error => {
            console.error("Error loading supplier list:", error);
        });
  }

  function loadMetadata(){
    const url = "http://localhost:8080/api/metadata/getListMetadata"
        + "?entity=Item";


    axios.get(url, {})
        .then(response => {
            console.log(" metadata response:", response.data);
             const listMetadata = new ListMetadata(response.data);
            renderFilterFields(listMetadata.filterFields, "filter-container");

        })
        .catch(error => {
            console.error("Error loading supplier list:", error);
        });

  }



  function renderFilterFields(filterFields, containerId) {
  const container = document.getElementById(containerId);
  if (!container) {
    console.log(" no contaioner")
;    return;
  }
  console.log(filterFields);
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
    console.log(field);
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
    control.onclick="applyFilter()" ;
    control.innerHTML="Apply";


    let control2;
    control2 = document.createElement("button");
    control2.className =" btn btn-info w-40" ;
    control2.onclick="clearFilter()" ;
    control2.innerHTML="Clear";
  
     wrapper.appendChild(control);
     wrapper.appendChild(document.createTextNode("\u00A0\u00A0"));

     wrapper.appendChild(control2);

    row.appendChild(wrapper);
  }

  container.appendChild(row);
}


