package com.nxtappz.nspace.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorData {
    private String name;
    private String error;
}
