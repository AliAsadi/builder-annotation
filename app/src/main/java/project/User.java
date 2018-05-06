package project;

import project.test.annotation.Builder;

/**
 * Created by Ali Esa Assadi on 02/05/2018.
 */

@Builder
public class User {
    private int id;
    private String name;
    private String pass;
    private String email;
    private String age;

    public User( String name,int id, String pass, String email, String age) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.email = email;
        this.age = age;
    }

    public User(String name, String pass, String email, String age) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.email = email;

        this.age = age;
    }

    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }

}
