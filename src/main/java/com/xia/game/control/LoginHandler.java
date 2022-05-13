package com.xia.game.control;

import com.xia.client.message.ResLogin;
import com.xia.framework.net.IdSession;
import com.xia.framework.serializer.annotation.Controller;
import com.xia.framework.serializer.annotation.RequestMapping;
import com.xia.game.login.LoginServer;
import com.xia.game.login.message.ReqLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Controller
@Component
public class LoginHandler {

    @Autowired
    LoginServer loginServer;

    @RequestMapping
    public ResLogin login(IdSession session, ReqLogin reqLogin) throws UnknownHostException {
        long username = reqLogin.getUsername();
        String password = reqLogin.getPassword();
        ResLogin resLogin =  loginServer.reqLogin(session,username,password);
        //session.sendPacket(resLogin);
        System.out.println(resLogin.toString());
        return resLogin;
    }

}
