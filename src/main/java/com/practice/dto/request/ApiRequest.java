package com.practice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.dto.MetaDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class ApiRequest<T> {

    @Valid
    @NotEmpty(message = "meta section cannot be empty")
    @JsonProperty(value = "meta")
    private MetaDTO metaDTO;

    @Valid
    @NotEmpty(message = "data cannot be empty")
    T data;

    public MetaDTO getMetaDTO() {
        return metaDTO;
    }

    public void setMetaDTO(MetaDTO metaDTO) {
        this.metaDTO = metaDTO;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
