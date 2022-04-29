package com.xia.game.login.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.xia.game.Modle;
import com.xia.game.Cmd;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import lombok.Data;

@Data
@MessageMeta(module = Modle.REGISTER,cmd = Cmd.reqRegister)
public class ReqRegister extends Message {
    @Protobuf
    private int username;

    @Protobuf
    private String password;
}
