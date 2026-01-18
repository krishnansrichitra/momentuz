async function loadUserProfileMetadata() {
    const url =
        urlPrefix + "api/metadata/getUpdateViewMetadata" +
        "?entity=UserProfile&mode=Edit";

    try {
        const response = await axios.get(url);
        let met = response.data
        console.log("metadata response:", met);
        const updateMetData = new UpdateViewMetadata(met);
        await renderPasswordUpdateForm(updateMetData, 'E');


    } catch (error) {
        console.error("Error loading metadata or list:", error);
    }
}


async function renderPasswordUpdateForm(metadata, mode = 'E') {
    const form = document.getElementById('genericForm');
    form.innerHTML = '';
    let hr2 = document.createElement('hr');
    hr2.className = 'border border-secondary border-2 my-3';

    form.appendChild(hr2);
    console.log('metadsta title=' + metadata.title);
    document.getElementById("hpageTitle").innerHTML = metadata.title;
    const visibleFields = metadata.updateViewFields
        .filter(f => f.isVisible(mode) && !f.isHidden());
    let row = createRow();
    let colCount = 0;
    visibleFields.forEach(field => {
        row = createRow();
        colCount = 0;
        const col = document.createElement('div');
        col.className = 'col-lg-3 col-md-6 col-sm-12';

        const label = document.createElement('label');
        label.className = 'form-label';
        label.textContent = field.fieldLabel + ":";
        let ctrDiv = document.createElement("div");
        ctrDiv.className="input-group";
        const control = renderControl(field);
        label.classList.add('form-label', 'mb-1');
        let spanCtrl = document.createElement('span');
        spanCtrl.className = 'input-group-text';
        spanCtrl.id = "togglePassword" + field.id;
        spanCtrl.style="cursor: pointer;";
        spanCtrl.innerHTML = '👁️';

        
        
        col.appendChild(label);
        ctrDiv.appendChild(control);
         ctrDiv.appendChild(spanCtrl);
         col.appendChild(ctrDiv);

          spanCtrl.addEventListener('click', function () {
        const pwd = document.getElementById(field.id);
        const isPassword = pwd.type === 'password';
        pwd.type = isPassword ? 'text' : 'password';

        this.textContent = isPassword ? '🙈' : '👁️';
        });;

        row.appendChild(col);
        form.appendChild(row);
    });

     
    


    let hr1 = document.createElement('hr');
    hr1.className = 'border border-secondary border-2 my-3';

    form.appendChild(hr1);
    //  form.appendChild(hr2)


    // action buttons
    form.appendChild(renderButtons(metadata, mode));
}

function onUpdate() {

    let passwordDTO = {};
    passwordDTO['currentPassword'] = document.getElementById("USPRF-CRUPWD").value;
    passwordDTO['newPassword'] = document.getElementById("USPRF-PWD").value;
    passwordDTO['confirmPassword'] = document.getElementById("USPRF-CNFPWD").value;

    console.log('passwordDTO=' + JSON.stringify(passwordDTO));

    axios.post(
        urlPrefix + `api/user/updatePassword`,
        passwordDTO
    )
        .then(response => {
            console.log('Success:', response.data);
            document.getElementById("USPRF-CRUPWD").value='';
            document.getElementById("USPRF-PWD").value='';
            document.getElementById("USPRF-CNFPWD").value='';

            showSuccessMessage(response.data.Messages);
            
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

function onClose()
{

        window.parent.closeHtmlDialog();

}