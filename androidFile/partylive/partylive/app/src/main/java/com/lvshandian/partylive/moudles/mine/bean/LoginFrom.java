package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;

/**
 * Created by gjj on 2016/12/7.
 */

public class LoginFrom implements Serializable {
    private boolean isThirdLogin;
    private String password;

    public boolean isThirdLogin() {
        return isThirdLogin;
    }

    public void setThirdLogin(boolean thirdLogin) {
        isThirdLogin = thirdLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
