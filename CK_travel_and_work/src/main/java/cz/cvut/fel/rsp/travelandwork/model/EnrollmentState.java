package cz.cvut.fel.rsp.travelandwork.model;

public enum EnrollmentState {
    ACTIVE("ACTIVE"), CANCELED("CANCELED"), FINISHED("FINISHED");

    private final String enrollmentState;


    EnrollmentState(String enrollmentState) {
        this.enrollmentState = enrollmentState;
    }

    @Override
    public String toString() {
        return enrollmentState;}
}
