package pages;

import fileio.Credentials;
import fileio.*;

public class Login extends Page {


    public void OnPage(String feature, Credentials credentials) {
        for(Credentials credential : super.getUsers()){
            String existingName = credential.getName();
            String loginName = credentials.getName();
            if (existingName.equals(loginName)) {
                super.setCurrentUser(credential);
                break;
            }
        }
}

}
