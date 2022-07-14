package com.xia.game.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xia
 */
@Data
public class User implements Serializable {
    private int uid;
    private long username;
    private String password;

    @Override
    public String toString(){
        return "uid:"+uid+" username:"+username+" password:"+password;
    }
}
