package com.project.FleetManagement.enums;


public enum DeliveryStatus {
    UNASSIGNED("UNASSIGNED"),
    DISPATCHED("DISPATCHED"),
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED");

    private final String value;

    DeliveryStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public boolean canTransitionTo(DeliveryStatus newStatus){
        switch(this){
            case UNASSIGNED:
                return newStatus==DISPATCHED;
            case DISPATCHED:
                return newStatus==IN_TRANSIT;
            case IN_TRANSIT:
                return newStatus==DELIVERED;
            case DELIVERED:
                return false;
            default:
                return false;
        }
    }

    public DeliveryStatus[] getValidStates(){
        switch(this){
            case UNASSIGNED:
                return new DeliveryStatus[]{DISPATCHED};
            case DISPATCHED:
                return new DeliveryStatus[]{IN_TRANSIT};
            case IN_TRANSIT:
                return new DeliveryStatus[]{DELIVERED};
            case DELIVERED:
                return new DeliveryStatus[]{};
            default:
                return new DeliveryStatus[]{};
        }
    }
}
