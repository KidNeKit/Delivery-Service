package bsuir.diplom.mercury.entities.enums;

public enum Role {
    DRIVER("Водитель"),
    LOADER("Грузчик"),
    USER("Пользователь");

    private final String text;

    Role(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
