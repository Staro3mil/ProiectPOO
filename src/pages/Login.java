package pages;

import fileio.Credentials;
import fileio.*;

public class Login extends Page {


    public void OnPage(String feature, Credentials credentials) {
        for(User user : super.getUsers()){
            String existingName = user.getCredentials().getName();
            String loginName = credentials.getName();
            if (existingName.equals(loginName)) {
                super.setCurrentUser(user);
                break;
            }
        }
}

}
