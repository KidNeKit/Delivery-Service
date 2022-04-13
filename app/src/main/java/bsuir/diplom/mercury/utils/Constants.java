package bsuir.diplom.mercury.utils;

public enum Constants {
    CURRENT_OFFERS_DB("currentOffers");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
