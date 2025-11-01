package LVLupServer.message.server;

import LVLupServer.Message;
import LVLupServer.shared.SuggestionDTO;

import java.util.List;

public class PendingSuggestionsResponse extends Message {
    private static final long serialVersionUID = 1L;

    private final List<SuggestionDTO> suggestions;

    public PendingSuggestionsResponse(List<SuggestionDTO> suggestions) {
        this.suggestions = suggestions;
    }

    public List<SuggestionDTO> getSuggestions() {
        return suggestions;
    }
}