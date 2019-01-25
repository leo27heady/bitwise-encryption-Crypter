package sample;

import java.io.Serializable;

public class User implements Serializable {

    private String login = null;
    private String email = null;
    private String password = null;
    private String answer = null;
    private boolean remember = false;

    User(){}

    User(String login, String email, String password, String answer, boolean remember){
        this.login = login;
        this.email = email;
        this.password = password;
        this.answer = answer;
        this.remember = remember;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isRemember() {
        return remember;
    }
}
