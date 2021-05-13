package Shared.Event.Platform;

public class Teams implements Platform{
    @Override
    public String meetingLink(String link) {
        return link;
    }

    @Override
    public String type() {
        return "Teams";
    }
}
