async function loadDropDowns()
{

    const url =
        urlPrefix + "api/common/getAllCountries";
    try {
        const response = await axios.get(url);
        console.log(response.data);
        const select = document.getElementById("slCountry");

        const empty = document.createElement('option');
        empty.value = '';
        empty.textContent = 'Select';
        select.appendChild(empty);
         let items = response.data;
        Object.entries(items).forEach(([key, label]) => {
            const opt = document.createElement('option');
            opt.value = key;
            opt.textContent = label;
            select.appendChild(opt);
        });
       // showSuccessMessage(response.data.Messages);
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