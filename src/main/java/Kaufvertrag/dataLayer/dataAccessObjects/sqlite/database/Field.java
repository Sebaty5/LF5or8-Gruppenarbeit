package Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database;

public class Field {
    private final String name;
    private final FieldType type;
    private final Boolean isPrimary;
    private final Boolean isNotNull;

    public Field(String name, FieldType type, Boolean isPrimary, Boolean isNotNull) {
        this.name = name;
        this.type = type;
        this.isPrimary = isPrimary;
        this.isNotNull = isNotNull;
    }

    public String getName() {
        return name;
    }

    public FieldType getType() {
        return type;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public Boolean getNotNull() {
        return isNotNull;
    }
}
