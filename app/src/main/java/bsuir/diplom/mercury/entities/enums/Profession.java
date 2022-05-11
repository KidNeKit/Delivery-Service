package bsuir.diplom.mercury.entities.enums;

public enum Profession {
    DRIVER("Водитель"),
    LOADER("Грузчик");

    private final String text;

    Profession(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
