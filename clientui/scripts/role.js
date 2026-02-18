console.log('loaded pop.js')

loadRoles();

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


