
async function updateRelease() {
    let payload = {};
    payload['releaseNo'] = document.getElementById('RLSCR-IT-CRD-RLSNO').value;
    let project =document.getElementById('RLSCR-IT-CRD-PRJ').value ;  

    if (project !==  null && project !==  '' && Number(project) > 0 ){
        let prjjson = {};
        prjjson['id'] = Number(project);
        payload['project'] = prjjson;
    } 
    let owneruser = document.getElementById('RLSCR-IT-CRD-OWN').value ;  
     if (owneruser !==  null && owneruser !==  ''  ){
        let ownjson = {};
        ownjson['userId'] =owneruser;
        payload['owner'] = ownjson;
    } 
    let status = document.getElementById('RLSCR-IT-CRD-STS').value ;  
     if (status !==  null && status !==  ''  ){
        let statusjson = {};
        statusjson['fvCode'] =status;
        payload['status'] = statusjson;
    }
    let releaseId = document.getElementById('RLSCR-IT-CRD-RLSID').value ; 
     if (releaseId !==  null && releaseId !==  '' && Number(releaseId) > 0 ){
        payload['id'] = releaseId;
    } 
    console.log(JSON.stringify(payload, null, 2));
    console.log('urlPrefix=' + urlPrefix);

    axios.post(
        urlPrefix + `api/generic/createOrUpdate`,
        payload,
        {
            params: {
                entityType: 'Release'
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }
    )
        .then(response => {
            console.log('Success:', response.data);
            showSuccessMessage(response.data.Messages);
           // window.location.href = './genericList.html?entity=' + entity;
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

async function generateSprints() {
    
}

async function createRelease() {
    
}

async function reloadReleaseDropDown(params) {
    
}