package smp.models;

public class Punishment {
    public String punishmentType;
    public Long punishmentDuration;
    public String punishmentReason;
    public String punishmentModerator;
    public Punishment(){

    }
    public Punishment(String type, Long duration, String reason, String moderator){
        this.punishmentType = type;
        this.punishmentDuration = duration;
        this.punishmentReason = reason;
        this.punishmentModerator = moderator;
    }
}
