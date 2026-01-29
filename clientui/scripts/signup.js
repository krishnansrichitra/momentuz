async function loadDropDowns()
{

    const urlCountries =
        urlPrefix + "api/auth/resetPassword?email=" + email;
    try {
        const response = await axios.get(url);
        console.log('responded');
        console.log(response);
        showSuccessMessage(response.data.Messages);
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