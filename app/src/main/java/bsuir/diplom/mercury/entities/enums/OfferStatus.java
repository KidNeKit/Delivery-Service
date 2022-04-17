package bsuir.diplom.mercury.entities.enums;

public enum OfferStatus {
    IN_PROCESSING("In processing"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    REJECTED("Rejected");

    private final String status;

    OfferStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
