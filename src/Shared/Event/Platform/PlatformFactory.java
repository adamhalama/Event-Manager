package Shared.Event.Platform;

public class PlatformFactory {
    public Platform getPlatform(String platform) {
        if (platform == null) {
            return null;
        }
        if (platform.equalsIgnoreCase("DISCORD")) {
            return new Discord();
        } else if (platform.equalsIgnoreCase("ZOOM")) {
            return new Zoom();
        } else if (platform.equalsIgnoreCase("TEAMS")) {
            return new Teams();
        }
        return null;
    }
}
