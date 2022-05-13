package com.xia.game.player;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

@Data
@ProtobufClass
public class PlayerInfo {
    public String ip;
    private long uid;
    //用户信息
    private long ts;
}
