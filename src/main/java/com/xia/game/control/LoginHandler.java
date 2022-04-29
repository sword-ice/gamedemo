package com.xia.game.control;

import com.xia.framework.net.IdSession;
import com.xia.framework.serializer.annotation.Controller;
import com.xia.framework.serializer.annotation.RequestMapping;
import com.xia.game.login.LoginServer;
import com.xia.game.login.message.ReqLogin;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class LoginHandler {
    @Autowired

    LoginServer loginServer;

    @RequestMapping
    public void login(IdSession session, ReqLogin reqLogin){

        long username = reqLogin.getUsername();
        String password = reqLogin.getPassword();
        loginServer.reqLogin(session,username,password);
    }

}
