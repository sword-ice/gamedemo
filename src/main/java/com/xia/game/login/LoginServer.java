package com.xia.game.login;


import com.xia.framework.common.SpringContext;
import com.xia.framework.net.IdSession;
import com.xia.client.message.ResLogin;
import com.xia.game.player.PlayerInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LoginServer {

    private static long no_uid = 10000;

    public ResLogin reqLogin(IdSession session,long username, String password) throws UnknownHostException {
        //账号验证


        PlayerInfo info = new PlayerInfo();
        //获取玩家数据
        info.setIp(InetAddress.getLocalHost().getHostAddress());
        info.setTs(System.currentTimeMillis());


        long uid = info.getUid();
        if(uid == 0){
            uid = no_uid++;
            info.setUid(uid);
        }
        //绑定uid和session
        session.setAttribute("uid",uid);
        SpringContext.getSessionManager().registerSession(uid,session);
        System.out.println(System.currentTimeMillis()-username);
        ResLogin msg = new ResLogin();
        msg.setPlayerInfo(info);
        return msg;
    }
}
