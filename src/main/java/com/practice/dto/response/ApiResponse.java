package com.practice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.practice.dto.MetaDTO;

public class ApiResponse<T> {

    @JsonProperty(value = "meta")
    private MetaDTO metaDTO;

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
