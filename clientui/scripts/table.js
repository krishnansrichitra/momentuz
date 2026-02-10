function bindRowEvents(tr, urlPrefix) {
    // bind typeahead inputs inside this row
    const inputs = tr.querySelectorAll('input[data-typeahead="1"]');

    inputs.forEach(inpel => {
        const param = inpel.dataset.param;         // string (from dataset)
        const fieldKey = inpel.dataset.fieldKey;

        // IMPORTANT: avoid double-binding if bindRowEvents gets called twice
      //  if (inpel.dataset.boundTypeahead === "1") return;
       // inpel.dataset.boundTypeahead = "1";
       let datactrl = document.getElementById('sgst' + fieldKey);

        inpel.addEventListener(
            "input",
            createTypeaheadHandler(urlPrefix, param, fieldKey, inpel, datactrl)
        );
    });
}

function addRow(tblId) {
    console.log('Adding row');
    let tabl = document.getElementById(tblId);
    var rowCount = tabl.rows.length;
    var row = tabl.rows[rowCount - 1];
    const newrow = row.cloneNode(true);
    tabl.tBodies[0].appendChild(newrow);
    newrow.querySelectorAll("input").forEach(i => i.value = "");
    bindRowEvents(newrow, urlPrefix);

}

function removeRow(tblId, btn) {

    let tabl = document.getElementById(tblId);
    const row = btn.closest("tr");
    const index = row.sectionRowIndex;
    if (index > 0) {
        tabl.deleteRow(index);
    }
}
