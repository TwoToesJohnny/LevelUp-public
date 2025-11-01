package LVLupServer.shared;

import java.io.Serializable;

public class BadgesDTO implements Serializable {
    private byte[] badgeIcon;
    private String badgeName;
    private int requiredLevel;
    private int gemReward;

    public BadgesDTO(byte[] badgeIcon, String badgeName, int requiredLevel, int gemReward) {
        this.badgeIcon = badgeIcon;
        this.badgeName = badgeName;
        this.requiredLevel = requiredLevel;
        this.gemReward = gemReward;
    }

    public byte[] getBadgeIcon() {
        return badgeIcon;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getGemReward() {
        return gemReward;
    }
}
