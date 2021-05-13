package Shared.Event.Platform;

public class Discord implements Platform{
    @Override
    public String meetingLink(String link) {
        return link;
    }

    @Override
    public String type() {
        return "Discord";
    }
}
