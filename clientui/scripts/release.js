
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
        payload['id'] = Number(releaseId);
    } 

    let version = document.getElementById('RLSCR-IT-CRD-VRS').value ; 
    if(version != null && version !== '' && Number(version) >= 0) {
        payload['version'] = Number(version);
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
            reloadReleaseDropDown();
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

async function  fetch() {

       let id=  document.getElementById("RLSCR-IT-CRD-RLSID").value;
    const url =
    urlPrefix + "api/generic/getById?entityType=" + encodeURIComponent('Release') + "&id=" + id ;
    try {
    const response = await axios.get(url);
    const jsonContent = response.data;
    console.log(jsonContent);
    let formControl = document.getElementById("genericForm");
    if(id!==null)
                traverseJson(formControl, jsonContent);
    document.getElementById("RLSCR-IT-CRD-VRS").value = jsonContent['version'];
    //return data;



    }catch (error) {
    console.error("Error fetching data");
  }
    
}
async function generateSprints() {
    
}

async function saveTeam() {
    let payload = {};
    payload['teamName'] = document.getElementById('RLSCR-IT-TM-NM').value;
     let owneruser = document.getElementById('RLSCR-IT-TM-PRDOWN').value ;  
     if (owneruser !==  null && owneruser !==  ''  ){
        let ownjson = {};
        ownjson['userId'] =owneruser;
        payload['productOwner'] = ownjson;
    } 
    let scrummaster = document.getElementById('RLSCR-IT-TM-SCRM').value ;  
     if (scrummaster !==  null && scrummaster !==  ''  ){
        let ownjson = {};
        ownjson['userId'] =scrummaster;
        payload['scrumMaster'] = ownjson;
    } 
    let version = document.getElementById('RLSCR-IT-TM-VRS').value ; 
    if(version != null && version !== '' && Number(version) >= 0) {
        payload['version'] = Number(version);
    }


    console.log(JSON.stringify(payload, null, 2));
    console.log('urlPrefix=' + urlPrefix);

    axios.post(
        urlPrefix + `api/generic/createOrUpdate`,
        payload,
        {
            params: {
                entityType: 'Team'
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }
    )
        .then(response => {
            console.log('Success:', response.data);
            showSuccessMessage(response.data.Messages);
            reloadTeamDropDown();
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

async function fetchTeam() {
    
}

async function reloadReleaseDropDown() {

    //RLSCR-IT-CRD-RLSID
    //RLSCR-IT-SP-RLSID
   document.getElementById("RLSCR-IT-CRD-RLSID").options.length= 0;
   document.getElementById("RLSCR-IT-SP-RLSID").options.length= 0;
   populateSelectOptions(document.getElementById("RLSCR-IT-CRD-RLSID"),"Release");
    populateSelectOptions(document.getElementById("RLSCR-IT-SP-RLSID"),"Release");


    
}

async function reloadTeamDropDown() {


   document.getElementById("RLSCR-IT-TM-TMID").options.length= 0;
   document.getElementById("RLSCR-IT-SP-TMID").options.length= 0;
   populateSelectOptions(document.getElementById("RLSCR-IT-TM-TMID"),"Team");
    populateSelectOptions(document.getElementById("RLSCR-IT-SP-TMID"),"Team");


    
}