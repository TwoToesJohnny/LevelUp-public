package LVLupServer.message.server;

import LVLupServer.Message;

import java.awt.*;
import java.io.Serial;

public class LevellingUpRewards extends Message {
    @Serial
    private static final long serialVersionUID = 111L;
    public int requiredXP;
    public int gemAmount;

    public byte[] badgeIcon;

    public String BadgeName;

    public LevellingUpRewards(int requiredXP, int gemAmount, byte[] badgeIcon, String badgeName) {
        this.requiredXP = requiredXP;
        this.gemAmount = gemAmount;
        this.badgeIcon = badgeIcon;
        BadgeName = badgeName;
    }
}
