package LVLupServer.handlers;

import LVLupServer.LevelUpClient;
import LVLupServer.dataBase.BadgeRepo;
import LVLupServer.dataBase.SuggestionRepository;
import LVLupServer.dataBase.VirtualItemRepo;
import LVLupServer.message.client.*;
import LVLupServer.message.server.*;
import LVLupServer.shared.BorderIconDTO;
import LVLupServer.shared.BadgesDTO;
import LVLupServer.shared.SuggestionDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRequestHandler {
    private final LevelUpClient client;
    private final VirtualItemRepo repo = new VirtualItemRepo();
    private final SuggestionRepository suggestionRepo = new SuggestionRepository();

    public ClientRequestHandler(LevelUpClient client) {
        this.client = client;
    }

    public void handleGetAllBadges() {
        try {
            List<BadgesDTO> badgeList = repo.getAllBadges();
            client.send(new AllBadgesResponse(badgeList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleGetAllBorderIcons() {
        try {
            List<BorderIconDTO> icons = repo.getAllBorderIcons();
            System.out.println("🟢 Sending " + icons.size() + " border icons to client");
            client.send(new AllBorderIconsResponse(icons));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleAddVirtualItem(String itemName, int gemCost, byte[] imageData) {
        try {
            repo.addBorderIconItem(itemName, gemCost, imageData);
            client.send(new VirtualItemAddedResponse(true, "Item added successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            client.send(new VirtualItemAddedResponse(false, "Failed to add item: " + e.getMessage()));
        }
    }

    public void handleUpdateBorderIcon(BorderIconDTO icon) {
        try {
            boolean updated = repo.updateBorderIcon(icon);
            client.send(new UpdateBorderIconResponse(updated,
                    updated ? "Item updated successfully" : "Item update failed"));
        } catch (Exception e) {
            e.printStackTrace();
            client.send(new UpdateBorderIconResponse(false, "Server error: " + e.getMessage()));
        }
    }

    public void handleDeleteBorderIcon(int id) {
        try {
            boolean deleted = repo.deleteBorderIcon(id);
            client.send(new DeleteBorderIconResponse(deleted,
                    deleted ? "Item deleted successfully" : "Item deletion failed"));
        } catch (Exception e) {
            e.printStackTrace();
            client.send(new DeleteBorderIconResponse(false, "Server error: " + e.getMessage()));
        }
    }

    public void handleClearBadge(int level) {
        boolean success = BadgeRepo.clearBadge(level);
        String msg = success ? "Badge cleared successfully!" : "Failed to clear badge.";
        client.send(new ClearBadgeResponse(success, msg));
        handleGetAllBadges();
    }

    public void handleGetPendingSuggestions() {
        try {
            List<SuggestionDTO> suggestions = suggestionRepo.getPendingSuggestions();
            client.send(new PendingSuggestionsResponse(suggestions));
        } catch (SQLException e) {
            e.printStackTrace();
            client.send(new PendingSuggestionsResponse(new ArrayList<>()));
        }
    }

    public void handleApproveSuggestion(ApproveSuggestionRequest request) {
        try {
            boolean success;
            if ("goal".equals(request.getType())) {
                success = suggestionRepo.approveGoal(
                        request.getTaskId(),
                        request.getName(),
                        request.getCategory(),
                        request.getExp()
                );
            } else if ("habit".equals(request.getType())) {
                success = suggestionRepo.approveHabit(
                        request.getTaskId(),
                        request.getName(),
                        request.getCategory(),
                        request.getExp()
                );
            } else {
                success = false;
            }

            String message = success ? "Suggestion approved successfully!" : "Failed to approve suggestion";
            client.send(new SuggestionActionResponse(success, message));

            if (success) {
                handleGetPendingSuggestions();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            client.send(new SuggestionActionResponse(false, "Database error: " + e.getMessage()));
        }
    }

    public void handleRejectSuggestion(RejectSuggestionRequest request) {
        try {
            boolean success;
            if ("goal".equals(request.getType())) {
                success = suggestionRepo.rejectGoal(request.getTaskId());
            } else if ("habit".equals(request.getType())) {
                success = suggestionRepo.rejectHabit(request.getTaskId());
            } else {
                success = false;
            }

            String message = success ? "Suggestion rejected successfully!" : "Failed to reject suggestion";
            client.send(new SuggestionActionResponse(success, message));

            if (success) {
                handleGetPendingSuggestions();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            client.send(new SuggestionActionResponse(false, "Database error: " + e.getMessage()));
        }
    }
}