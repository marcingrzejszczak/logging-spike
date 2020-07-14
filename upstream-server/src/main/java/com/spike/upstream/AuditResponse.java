package com.spike.upstream;

public class AuditResponse {
    private boolean success;

    public AuditResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
