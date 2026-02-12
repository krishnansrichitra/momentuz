
console.log('loaded pop.js')

loadGeneralEvents();

function loadGeneralEvents() {
let ctrls = document.getElementsByName("PO-Lines-barcode");
for (let ctr of ctrls) {
    ctr.addEventListener("focusout", function (e) {
    const row = e.target.closest("tr");
    const rowIndex = row.sectionRowIndex;
    console.log('rowIndex ='+ rowIndex);
    getByBarcode(e.target.value,rowIndex);
   });
}

let ctrlitems = document.getElementsByName("PO-Lines-item");
for (let ctr of ctrlitems) {
    ctr.addEventListener("focusout", function (e) {
    const row = e.target.closest("tr");
    const rowIndex = row.sectionRowIndex;
    console.log('rowIndex ='+ rowIndex);
   });
}
}

async function getByBarcode(barcode,index)
{
     const url =
        urlPrefix + "api/datafetch/itemByBarcode?" +
        "barcode=" + barcode ;
    const response = await axios.get(url);
    let lookupData = response.data.properties;
    console.log(lookupData);
    let ctrls = document.getElementsByName("PO-Lines-item");
    ctrls[index].value = lookupData['itemName'];

    
   let priceCtrls = document.getElementsByName("PO-Lines-price");
    priceCtrls[index].value = lookupData['price'];


}