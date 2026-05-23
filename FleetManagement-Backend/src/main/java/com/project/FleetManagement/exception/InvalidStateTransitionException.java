package com.project.FleetManagement.exception;

public class InvalidStateTransitionException extends BusinessException {

    public InvalidStateTransitionException(String currentStatus, String requestedStatus) {
        super(String.format("Cannot transition from '%s' to '%s'", currentStatus, requestedStatus));
    }
}