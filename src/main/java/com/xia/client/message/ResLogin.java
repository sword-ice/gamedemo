package com.xia.client.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.xia.framework.message.Message;
import com.xia.framework.message.annotation.MessageMeta;
import com.xia.game.Cmd;
import com.xia.game.Modle;
import com.xia.game.player.PlayerInfo;
import lombok.Data;

@Data
@MessageMeta(module = Modle.LOGIN,cmd = Cmd.resLogin)
public class ResLogin extends Message {
    @Protobuf
    private PlayerInfo playerInfo;
}
