package com.xia.game.login;


import com.xia.framework.common.SpringContext;
import com.xia.framework.net.IdSession;
import com.xia.client.message.ResLogin;
import com.xia.game.player.PlayerInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LoginServer {

    public ResLogin reqLogin(IdSession session,long username, String password){
        //账号验证


        PlayerInfo info = new PlayerInfo();
        //获取玩家数据


        long uid = info.getUid();

        //绑定uid和session
        session.setAttribute("uid",uid);
        SpringContext.getSessionManager().registerSession(uid,session);
        System.out.println(username + password);
        ResLogin msg = new ResLogin();
        msg.setPlayerInfo(info);
        return msg;
    }
}
