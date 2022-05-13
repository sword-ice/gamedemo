package com.xia.game.login.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import com.xia.game.Cmd;
import com.xia.game.Modle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ProtobufClass
@MessageMeta(module = Modle.REGISTER,cmd = Cmd.resRegister)
public class ResRegister extends Message {
    private int status;
}
