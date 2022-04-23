package bsuir.diplom.mercury.utils;

public enum Constants {
    CURRENT_OFFERS_DB("currentOffers"),

    FRAGMENT_DATA_TRANSFER_REQUEST_KEY("fragmentDataTransferRequestKey"),

    /*Bundle transfer data tags*/
    IS_ALLOWED_NEXT("isAllowedNext"),
    IS_ALLOWED_PREV("isAllowedPrev");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
