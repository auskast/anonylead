package com.edmunds.anonylead;

/**
 * Created by IntelliJ IDEA. User: pfitzgerald Date: 8/16/12 Time: 5:17 PM
 */
public class Record {
    private String email;
    private String firstName;
    private String lastName;
    private Duration duration = Duration.SHORT;
    private DigestPeriod digestPeriod = DigestPeriod.NONE;

    public Record() {}

    public Record(String email, String firstName, String lastName, Duration duration, DigestPeriod digestPeriod) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.duration = duration;
        this.digestPeriod = digestPeriod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DigestPeriod getDigestPeriod() {
        return digestPeriod;
    }

    public void setDigestPeriod(DigestPeriod digestPeriod) {
        this.digestPeriod = digestPeriod;
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Record")
            .append("(FirstName: ").append(firstName)
            .append(", LastName: ").append(lastName)
            .append(", Email: ").append(email)
            .append(", Duration: ").append(duration)
            .append(", DigestPeriod: ").append(digestPeriod)
            .append(")")
            .toString();
    }
}
