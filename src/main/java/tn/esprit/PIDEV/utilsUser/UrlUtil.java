package tn.esprit.PIDEV.utilsUser;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

public class UrlUtil {

    @Value("${server.port}")
    private static String serverPort;

    public static String getAppUrl(HttpServletRequest request) {
        StringBuilder appUrl = new StringBuilder();
        appUrl.append(request.getScheme())
                .append("://")
                .append(request.getServerName());


        if (("http".equals(request.getScheme()) && request.getServerPort() != 80) ||
                ("https".equals(request.getScheme()) && request.getServerPort() != 443)) {
            appUrl.append(":").append(request.getServerPort());
        }


        appUrl.append(request.getContextPath());

        return appUrl.toString();
    }
}
