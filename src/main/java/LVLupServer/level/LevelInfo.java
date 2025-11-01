package LVLupServer.level;

import java.awt.*;

public class LevelInfo {
    private int lvl;
    private int requiredXp;
    private int gemAmount;
    private byte[] badgeImage;
    private String badgeName;

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getRequiredXp() {
        return requiredXp;
    }

    public void setRequiredXp(int requiredXp) {
        this.requiredXp = requiredXp;
    }

    public int getGemAmount() {
        return gemAmount;
    }

    public void setGemAmount(int gemAmount) {
        this.gemAmount = gemAmount;
    }

    public byte[] getBadgeImage() {
        return badgeImage;
    }

    public void setBadgeImage(byte[] badgeImage) {
        this.badgeImage = badgeImage;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public LevelInfo(int lvl, int requiredXp, int gemAmount, byte[] badgeImage, String badgeName) {
        this.lvl = lvl;
        this.requiredXp = requiredXp;
        this.gemAmount = gemAmount;
        this.badgeImage = badgeImage;
        this.badgeName = badgeName;
    }
}
