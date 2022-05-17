package bsuir.diplom.mercury.utils;

public enum Constants {
    CURRENT_OFFERS_DB("currentOffers"),

    BUTTON_SWITCH_BEHAVIOR_REQUEST_KEY("buttonSwitchBehaviourRequestKey"),
    VIEWPAGER_FRAGMENTS_DATA_TRANSFER_REQUEST_KEY("viewpagerFragmentDataTransferRequestKey"),
    EDITABLE_ITEM_POSITION_REQUEST_KEY("editableItemPositionRequestKey"),
    CHANGE_VIEW_PAGER_SELECTED_PAGE_REQUEST_KEY("changeViewPagerSelectedPageRequestKey"),
    CURRENT_ITEM_LIST_REQUEST_KEY("currentItemListRequestKey"),

    /*Bundle transfer data tags*/
    IS_ALLOWED_NEXT("isAllowedNext"),
    IS_ALLOWED_PREV("isAllowedPrev"),
    CURRENT_ITEMS_LIST("currentItemsList"),
    EDITABLE_ITEM_POSITION("editableItemPosition"),
    SET_PREVIOUS_PAGE("setPreviousPage"),
    SET_NEXT_PAGE("setNextPage"),

    /*Logging tags*/
    ITEM_LIST_CHANGE_LOGGING("itemListChangeLogging");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
