package com.xia.game.login;


import com.xia.client.message.ResLogin;
import com.xia.framework.common.SpringContext;
import com.xia.framework.net.IdSession;
import com.xia.game.entity.User;
import com.xia.game.player.PlayerInfo;
import com.xia.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LoginServer {

    @Autowired
    private UserService userService;
    private static long no_uid = 10000;

    public ResLogin reqLogin(IdSession session,long username, String password) throws UnknownHostException {
        //账号验证


        PlayerInfo info = new PlayerInfo();
        //获取玩家数据
        User user = userService.findUserByUsername(username);
        ResLogin msg = new ResLogin();
        if(user == null||!password.equals(user.getPassword())){
            msg.setPlayerInfo(new PlayerInfo());
            return msg;
        }

        info.setIp(InetAddress.getLocalHost().getHostAddress());
        info.setTs(System.currentTimeMillis());
        long uid = user.getUid();
        if(uid == 0){
            uid = no_uid++;
        }
        info.setUid(uid);
        //绑定uid和session
        session.setAttribute("uid",uid);
        SpringContext.getSessionManager().registerSession(uid,session);
        msg.setPlayerInfo(info);
        return msg;
    }
}
