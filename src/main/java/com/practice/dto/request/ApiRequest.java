package com.practice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.dto.MetaDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ApiRequest<T> implements Serializable {

    @Valid
    @NotNull(message = "meta section cannot be empty")
    @JsonProperty(value = "meta")
    private MetaDTO metaDTO;

    @Valid
    @NotNull(message = "data cannot be empty")
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
