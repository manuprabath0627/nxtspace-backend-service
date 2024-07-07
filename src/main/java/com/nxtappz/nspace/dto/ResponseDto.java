package com.nxtappz.nspace.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDto {
    private String message;
    private int status;
}
