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

async function loadSectors()
{

    const url =
        urlPrefix + "api/common/getAllSectors";
    try {
        const response = await axios.get(url);
        console.log(response.data);
        const select = document.getElementById("slSector");

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


async function  signUpCompany() {

    let input = { };
    let company  = document.getElementById("txtCompany").value;
    let orgCode  = document.getElementById("txtOrgCode").value;
    let address1  = document.getElementById("txtAddress1").value;
    let address2  = document.getElementById("txtAddress2").value;
    let city  = document.getElementById("txtCity").value;
    let state  = document.getElementById("txtState").value;
    let zipCode  = document.getElementById("txtZipCode").value;
    let phone  = document.getElementById("txtPhone").value;
    let email  = document.getElementById("txtEmail").value;
    let firstName  = document.getElementById("txtFirstName").value;
    let lastName  = document.getElementById("txtLastName").value;
    let primaryEmail  = document.getElementById("txtPrimaryEmail").value;

    let country = document.getElementById("slCountry").value;
    let sector = document.getElementById("slSector").value;

    input['orgCode'] =orgCode;
    input['organizationName'] =company;
    input['sector'] =sector;
    input['address1'] =address1;
    input['address2'] =address2;
    input['zipCode'] =zipCode;
    input['city'] =city;
    input['state'] =state;
    input['country'] =country;
    input['phone'] =phone;
    input['email'] =email;
    input['firstName'] =firstName;
    input['lastName'] =lastName;

    input['primaryUserEmail'] =primaryEmail;

    const url =
        urlPrefix + "api/orgsignup/register";


    axios.post(
       url,
        input
    )
        .then(response => {
            console.log('Success:', response.data);
            showSuccessMessage('Company Registered.  Please check your primary email for login credentials');
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