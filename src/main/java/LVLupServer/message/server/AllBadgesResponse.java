package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.shared.BadgesDTO;
import java.util.List;

public class AllBadgesResponse extends Message {
    private static final long serialVersionUID = 1L;

    private final List<BadgesDTO> badges;

    public AllBadgesResponse(List<BadgesDTO> badges) {
        this.badges = badges;
    }

    public List<BadgesDTO> getBadges() {
        return badges;
    }

    @Override
    public String toString() {
        return "AllBadgesResponse{badges=" + (badges != null ? badges.size() : 0) + "}";
    }
}