function addRow(tblId)
{
    console.log('Adding row');
  let tabl = document.getElementById(tblId);
  var rowCount = tabl.rows.length;
  var row = tabl.rows[rowCount-1];
  var newrow = tabl.insertRow();
  newrow.innerHTML = row.innerHTML;
  var inputs = newrow.getElementsByTagName('input');
  console.log('RowCount=' + rowCount);
  for (i = 0; i < inputs.length; i++) {
     inputs[i].value = "";
  }

}

function removeRow(tblId,btn)
{

      let tabl = document.getElementById(tblId);
       const row = btn.closest("tr");
      const index = row.sectionRowIndex;
       if (index > 0) {
       tabl.deleteRow(index);
       }
}
