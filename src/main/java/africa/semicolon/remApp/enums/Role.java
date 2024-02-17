package africa.semicolon.remApp.enums;

public enum Role {

    FRESH_USER("FRESH_USER"),
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
