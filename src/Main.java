import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;


public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = mapper.createArrayNode();

        String currPage = new String();
        currPage = "unauth";

        ArrayList<User> users = input.getUsers();
        User currUser = users.get(0);
        ObjectNode credentials = mapper.createObjectNode();
        ObjectNode user = mapper.createObjectNode();
        user.put("name", currUser.getName());
        user.put("password", currUser.getPassword());
        user.put("accountType", currUser.getAccType());
        user.put("country", currUser.getBalance());
        credentials.set("credentials", user);


        output.add(credentials);





        mapper.writeValue(new File(args[1]), output);
    }
}
