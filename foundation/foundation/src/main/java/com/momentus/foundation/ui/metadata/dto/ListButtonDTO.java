package com.momentus.foundation.ui.metadata.dto;

import jakarta.persistence.Column;

public class ListButtonDTO {

    String buttonClass ;

    @Column
    String jsMethod;

    @Column
    String innerText ;

    public String getButtonClass() {
        return buttonClass;
    }

    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }

    public String getJsMethod() {
        return jsMethod;
    }

    public void setJsMethod(String jsMethod) {
        this.jsMethod = jsMethod;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public ListButtonDTO() {
    }

    public ListButtonDTO(String buttonClass, String jsMethod, String innerText) {
        this.buttonClass = buttonClass;
        this.jsMethod = jsMethod;
        this.innerText = innerText;
    }
}
