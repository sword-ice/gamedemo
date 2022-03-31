package com.xia.game.net;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;


public class ChannelUtils {

    public static AttributeKey<IdSession> SESSION_KEY = AttributeKey.valueOf("session");


    public static boolean addChannelSession(Channel channel, IdSession session){
        Attribute<IdSession> sessionAttribute = channel.attr(SESSION_KEY);
        return sessionAttribute.compareAndSet(null,session);
    }

    public static IdSession getSessionBy(Channel channel){
        Attribute<IdSession> sessionAttribute = channel.attr(SESSION_KEY);
        return sessionAttribute.get();
    }

    public static String getIp(Channel channel){
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

}
