package window.litestrap.roblox;

import java.net.URI;

import java.util.Map;
import java.util.HashMap;

public class UriMap {

    public static String getUri(String link) {
        if (link == null || link.trim().isEmpty()) {
            return null;
        }

        try {
            URI url = new URI(link.trim());
            String path = url.getPath() != null ? url.getPath() : "";
            Map<String, String> queryParams = parseQueryParams(url.getQuery());

            if (path.contains("/games/") && queryParams.containsKey("privateServerLinkCode")) {
                String[] pathParts = path.split("/");
                String placeId = null;

                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i].equals("games") && (i + 1) < pathParts.length) {
                        placeId = pathParts[i + 1];
                        break;
                    }
                }

                String linkCode = queryParams.get("privateServerLinkCode");

                if (placeId != null && !placeId.isEmpty() && linkCode != null && !linkCode.isEmpty()) {
                    return String.format("roblox://placeId=%s&linkCode=%s", placeId, linkCode);
                }
            } 

            if (path.contains("/share") && queryParams.containsKey("code")) {
                String code = queryParams.get("code");
                String type = queryParams.getOrDefault("type", "Server");

                if (code != null && !code.isEmpty()) {
                    return String.format("roblox://navigation/share_links?code=%s&type=%s", code, type);
                } 
            } 

            System.err.println("Invalid URL");
            return null;

        } catch (Exception e) {
            System.err.println("Invalid URL");
            return null;
        }
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length > 0) {
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                params.put(key, value);
            }
        }
        return params;
    }
}