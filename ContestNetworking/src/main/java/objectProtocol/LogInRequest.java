package objectProtocol;

import domain.User;

public class LogInRequest implements Request {
    private User user;

    public LogInRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
