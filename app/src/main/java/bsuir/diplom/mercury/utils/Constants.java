package bsuir.diplom.mercury.utils;

public enum Constants {
    CURRENT_OFFERS_DB("currentOffers"),

    BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY("buttonSwitchBehaviourRequestKey"),
    VIEWPAGER_FRAGMENTS_DATA_TRANSFER_REQUEST_KEY("viewpagerFragmentDataTransferRequestKey"),

    /*Bundle transfer data tags*/
    IS_ALLOWED_NEXT("isAllowedNext"),
    IS_ALLOWED_PREV("isAllowedPrev"),
    CURRENT_ITEMS_LIST("currentItemsList");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
