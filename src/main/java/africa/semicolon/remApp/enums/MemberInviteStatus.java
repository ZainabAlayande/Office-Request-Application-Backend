package africa.semicolon.remApp.enums;

public enum MemberInviteStatus {

    PENDING("PENDING"), JOINED("JOINED");

    private final String name;
    MemberInviteStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
