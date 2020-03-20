package cz.cvut.fel.rsp.travelandwork.model;

public enum Role {
    STUDENT("ROLE_STUDENT"), MANAGER("ROLE_MANAGER"),SUPERUSER("ROLE_SUPERUSER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}

