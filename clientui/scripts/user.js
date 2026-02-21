console.log('loaded user js ');

async function loadUserMetadata() {
    const url =
        urlPrefix + "api/metadata/getUpdateViewMetadata" +
        "?entity=User&mode=" + mode;

    try {
        const response = await axios.get(url);
        let met = response.data;
       // console.log("metadata response:", met);
        const updateMetData = new UpdateViewMetadata(met);
        if (mode == 'Add') {
            await renderUpdateViewForm(updateMetData, 'A');
        } else if (mode == 'Edit') {

            await renderUpdateViewForm(updateMetData, 'E');
            let jsonContent = await fetchUserDataById( id);
             console.log(jsonContent);
            let formControl = document.getElementById("genericForm");
            if(id!==null)
                traverseJson(formControl, jsonContent);
        } else if (mode == 'View') {

            await renderUpdateViewForm(updateMetData, 'V');
            let jsonContent = await fetchUserDataById( id);
            let formControl = document.getElementById("genericForm");
            traverseJson(formControl, jsonContent);
        }
        if (updateMetData.jsFile !== null) {
            const fileName = updateMetData.jsFile;
            console.log(fileName);
            loadScript(fileName)
                .then(() => {
                    console.log("Script loaded");
                    // you can safely call functions from the loaded file here
                })
                .catch(err => console.error(err));
        }

    } catch (error) {
        console.error("Error loading metadata or list:", error);
    }
}

async function fetchUserDataById( userid)
{
   const url =
    urlPrefix + "api/user/getByUserId?userId=" + userid ;
    try {
    const response = await axios.get(url);
    const data = response.data;
    return data;
    }catch (error) {
    console.error("Error loading dropdown:", error);
  }
}

function onUserCreate() {

    window.location.href = './useraddview.html?entity=User&mode=Add';

}


function onUserView() {
    console.log('inside userview');

    const selectedIds = [...document.querySelectorAll('.row-check:checked')]
        .map(cb => cb.dataset.id);

    console.log('selectedIds=' + selectedIds);
    if (selectedIds.length > 1 || selectedIds.length == 0) {
        showErrorFromUI('Please select only one row for view');
    } else {
        window.location.href = './useraddview.html?entity=User&mode=View&id=' + selectedIds[0];
    }


}


function onEditMode() {

    window.location.href = './useraddview.html?entity=User&mode=Edit&id=' + id;
}


function onUserEdit() {


    const selectedIds = [...document.querySelectorAll('.row-check:checked')]
        .map(cb => cb.dataset.id);

    console.log('selectedIds=' + selectedIds);
    if (selectedIds.length > 1 || selectedIds.length == 0) {
        showErrorFromUI('Please select only one row for edit');
    } else {
        window.location.href = './useraddview.html?entity=User&mode=Edit&id=' + selectedIds[0];
    }
}

function onUserDelete() {

    console.log("Delete clicked");
    const selectedIds = [...document.querySelectorAll('.row-check:checked')]
        .map(cb => Number(cb.dataset.id));

    console.log('selectedIds=' + selectedIds);
    if (selectedIds.length == 0) {
        showErrorFromUI('Please select atleast one row for delete');
        return;
    }
    if (confirm("Are you sure you want to delete this record?")) {

        const url =
            urlPrefix + "api/generic/bulkDelete" +
            "?entityType=" + entity;

        axios.post(url, selectedIds)

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
}


function onUserSave() {
    const form = document.getElementById('genericForm');
    const payload = buildJsonFromForm(form);

    console.log(JSON.stringify(payload, null, 2));
    console.log('urlPrefix=' + urlPrefix);

    axios.post(
        urlPrefix + `api/user/create`,
        payload,
        {
        
            headers: {
                'Content-Type': 'application/json'
            }
        }
    )
        .then(response => {
            console.log('Success:', response.data);
            window.location.href = './genericList.html?entity=User';
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