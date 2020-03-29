package objectProtocol;

import domain.User;

public class LogOutRequest implements Request{
    public User user;

    public LogOutRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
