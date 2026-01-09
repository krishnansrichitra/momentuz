function onUpload() {
  const fileInput = document.getElementById("fileInput");
   console.log("method called");
  if (!fileInput.files || fileInput.files.length === 0) {
    console.log("return called");
    return;
  }
console.log("file input called");
  const file = fileInput.files[0];

  // Create multipart form data
  const formData = new FormData();
  formData.append("file", file); // "file" must match @RequestParam name
console.log("Calling upload");
let entity =document.getElementById("lstEntities").value;
  axios.post(
    urlPrefix + "api/generic/upload" +"?entityType=" + entity ,
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }
  )
  .then(response => {
    console.log("Upload success:", response.data);

  })
  .catch(error => {
    console.error("Upload failed:", error);

  });// or reload data
}


async function loadEntities(selectId) {
  const url =
    urlPrefix + "api/lookup/getEntityDropDowns" ;

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
    console.error("Error loading entities:", error);
  }
}