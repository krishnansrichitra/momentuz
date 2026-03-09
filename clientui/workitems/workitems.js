console.log('loaded user js ');

async function loadWIMetadata() {
    const url =
        urlPrefix + "api/workitem/getUpdateViewMetadata" +
        "?projectId=1&projectType=Story&mode=Add";
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

     const url =
        urlPrefix + "api/workitem/getUpdateViewMetadata" +
        "?projectId=1&projectType=Story&mode=Add";
    let md='Add';

    try {
        const response = await axios.get(url);
        let met = response.data;
       console.log("metadata response:", met);
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


function onWTSave() {
    const form = document.getElementById('genericForm');
    const payload = buildJsonFromForm(form);

    console.log(JSON.stringify(payload, null, 2));
    console.log('urlPrefix=' + urlPrefix);

    axios.post(
        urlPrefix + `api/workitem/createOrUpdate`,
        payload,
        {
          
            headers: {
                'Content-Type': 'application/json'
            }
        }
    )
        .then(response => {
            console.log('Success:', response.data);
            window.location.href = './genericList.html?entity=' + entity;
        })
        .catch(error => {
            console.error('Error:', error);
            const response = error.response?.data;
            const apiErrors = new ApiErrorResponse(response);
            if (apiErrors.hasErrors()) {
                const messages = apiErrors.getMessages();
                showErrors(messages);
            }

        });


}