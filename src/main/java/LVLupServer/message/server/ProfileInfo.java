package LVLupServer.message.server;

import LVLupServer.Message;

import java.io.Serial;
import java.util.ArrayList;

public class ProfileInfo extends Message {
    @Serial
    private static final long serialVersionUID = 122L;
    public int requiredXP;
    public ArrayList<byte[]> badgesDataList;
    public byte[] profileIconData;

    public ProfileInfo(int requiredXP, ArrayList<byte[]> badgesDataList, byte[] profileIconData) {
        this.requiredXP = requiredXP;
        this.badgesDataList = badgesDataList;
        this.profileIconData = profileIconData;
    }

}
