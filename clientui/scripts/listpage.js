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