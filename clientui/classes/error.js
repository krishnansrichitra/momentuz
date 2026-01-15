class ApiErrorResponse {
    constructor(json = {}) {
        this.errors = (json.errors || []).map(e => new ApiError(e));
    }

    hasErrors() {
        return this.errors.length > 0;
    }

    getMessages() {
        return this.errors.map(e => e.errorMessage);
    }

    getByCode(code) {
        return this.errors.find(e => e.errorCode === code);
    }
}

class ApiError {
    constructor(json = {}) {
        this.errorType = json.errorType ?? null;
        this.errorCode = json.errorCode ?? null;
        this.errorMessage = json.errorMessage ?? '';
    }
}