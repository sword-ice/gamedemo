package com.xia.client;

import com.xia.framework.serializer.annotation.Controller;
import com.xia.framework.serializer.annotation.RequestMapping;
import com.xia.game.login.message.ReqLogin;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

@Controller
public class ClientMessage {

    private static Scanner sc = new Scanner(System.in);

    @RequestMapping
    public static void login(ChannelHandlerContext ctx){
        ReqLogin login = new ReqLogin();
        System.out.print("用户名：");
        login.setUsername(Long.valueOf(sc.nextLine()));
        System.out.print("密码：");
        login.setPassword(sc.nextLine());
        ctx.writeAndFlush(login);
    }

}
