package com.xia.framework.net;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private Map<IdSession,Long> sessionPlayers = new ConcurrentHashMap<>();

    private Map<Long,IdSession> playerSessions = new ConcurrentHashMap<>();


    public long getUIdBy(IdSession session) {
        if (session != null) {
            return session.getOwnerId();
        }
        return 0;
    }

    public IdSession getSessionBy(long playerId) {
        return playerSessions.get(playerId);
    }

    public void registerSession(long uid,IdSession session){
        sessionPlayers.put(session,uid);
        playerSessions.put(uid,session);
        logger.info("uid:{} is register",uid);
    }



    public void unRegisterChannel(Channel channel){
        if(channel == null){
            return;
        }
        IdSession session = ChannelUtils.getSessionBy(channel);
        if(!sessionPlayers.containsKey(session)){
            return;
        }
        long uid = sessionPlayers.get(session);
        playerSessions.remove(uid);

    }

}
