console.log('loaded pop.js')

loadRoles();
loadAccessCodes();

async function loadRoles() {

    const url =
        urlPrefix + "api/lookup/getRolesDropDown";
    try {
        const response = await axios.get(url);
        console.log(response.data);
        const select = document.getElementById("RLCR-DESC");

         let items = response.data;
        Object.entries(items).forEach(([key, label]) => {
            const opt = document.createElement('option');
            opt.value = key;
            opt.textContent = label;
            select.appendChild(opt);
        });

        
    } catch (error) {
        console.error('Error:', error);
        const response = error.response?.data;
        const apiErrors = new ApiErrorResponse(response);
        if (apiErrors.hasErrors()) {
            const messages = apiErrors.getMessages();
            showErrors(messages);
        }

    }
}

async function loadAccessCodes() {

    const url =
        urlPrefix + "api/lookup/allAccessCodes";
    try {
        const response = await axios.get(url);
        console.log(response.data);
        const select = document.getElementById("RLCR-ACC");

         let items = response.data;
        Object.entries(items).forEach(([key, label]) => {
            const liouter = document.createElement('li');
            const lbl = document.createElement('label');
            lbl.className='dropdown-item';
            const inpchk=document.createElement('input');
            inpchk.type='checkbox';
            inpchk.value = key;
            inpchk.className='me-2';
          
            lbl.appendChild(inpchk);
            const spn = document.createElement('span');
            spn.innerHTML = label;
            lbl.appendChild(spn);
            liouter.appendChild(lbl);
            select.appendChild(liouter);
        });

        
    } catch (error) {
        console.error('Error:', error);
        const response = error.response?.data;
        const apiErrors = new ApiErrorResponse(response);
        if (apiErrors.hasErrors()) {
            const messages = apiErrors.getMessages();
            showErrors(messages);
        }

    }
}


