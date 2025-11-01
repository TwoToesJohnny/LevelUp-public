package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.shared.BorderIconDTO;

import java.util.List;

public class AllBorderIconsResponse extends Message {
    private static final long serialVersionUID = 1L;

    private final List<BorderIconDTO> borderIcons;

    public AllBorderIconsResponse(List<BorderIconDTO> borderIcons) {
        this.borderIcons = borderIcons;
    }

    public List<BorderIconDTO> getBorderIcons() {
        return borderIcons;
    }
}