console.log('loaded user js ');

async function loadWIMetadata() {
    const url =
        urlPrefix + "api/metadata/getUpdateViewMetadata" +
        "?entity=WorkItem-N&mode=Add";
    let md='Add';

    try {
        const response = await axios.get(url);
        let met = response.data;
       // console.log("metadata response:", met);
        const updateMetData = new UpdateViewMetadata(met);
        if (md == 'Add') {
            await renderUpdateViewForm(updateMetData, 'A');
        } else if (md == 'Edit') {

            await renderUpdateViewForm(updateMetData, 'E');
            let jsonContent = await fetchUserDataById( id);
             console.log(jsonContent);
            let formControl = document.getElementById("genericForm");
            if(id!==null)
                traverseJson(formControl, jsonContent);
        } else if (md == 'View') {

            await renderUpdateViewForm(updateMetData, 'V');
            let jsonContent = await fetchUserDataById( id);
            let formControl = document.getElementById("genericForm");
            traverseJson(formControl, jsonContent);
        }

    } catch (error) {
        console.error("Error loading metadata or list:", error);
    }
}


async function  createWIT(params) {
    
}
