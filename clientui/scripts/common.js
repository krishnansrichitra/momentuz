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
