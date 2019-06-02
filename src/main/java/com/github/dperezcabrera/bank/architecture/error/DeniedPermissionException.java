package com.github.dperezcabrera.bank.architecture.error;

import lombok.Getter;

public class DeniedPermissionException extends RuntimeException {
    
    @Getter
    private final String requiredPermission;
    
    public DeniedPermissionException(String message, String requiredPermission) {
        super(message);
        this.requiredPermission = requiredPermission;
    }
}
