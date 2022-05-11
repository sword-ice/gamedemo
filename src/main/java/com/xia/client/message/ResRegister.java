package com.xia.client.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import com.xia.game.Cmd;
import com.xia.game.Modle;

@MessageMeta(module = Modle.REGISTER,cmd = Cmd.resRegister)
public class ResRegister extends Message {
    @Protobuf
    private int status;
}
