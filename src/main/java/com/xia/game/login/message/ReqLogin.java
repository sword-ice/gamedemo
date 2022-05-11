package com.xia.game.login.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import com.xia.game.Cmd;
import com.xia.game.Modle;
import lombok.Data;

@Data
@ProtobufClass
@MessageMeta(module = Modle.LOGIN,cmd = Cmd.reqLogin)
public class ReqLogin extends Message {

    private long username;


    private String password;
}
