package com.quotorcloud.quotor.common.core.enums;

public enum SMSType {

    AUTH,

    REG,

    FINDPASSWORD,

    MODIFYINFO,

    UPDATEPHONE;

    /**
     * toString
     *
     * @return String
     */
    @Override
    public String toString(){
        String sMSType = "";
        switch (this){
            case AUTH:
                sMSType = "登陆验证";
                break;
            case REG:
                sMSType = "注册账号";
                break;
            case FINDPASSWORD:
                sMSType = "修改密码";
                break;
            case MODIFYINFO:
                sMSType = "修改账号";
                break;
            case UPDATEPHONE:
                sMSType = "修改电话号";
                break;
            default:
                sMSType = "";
        }
        return sMSType;
    }

    public static Integer getType(String str){
        Integer sMSType;
        switch (str){
            case "AUTH":
                sMSType = 1;
                break;
            case "REG":
                sMSType = 2;
                break;
            case "FINDPASSWORD":
                sMSType = 3;
                break;
            case "MODIFYINFO":
                sMSType = 4;
                break;
            case "UPDATEPHONE":
                sMSType = 5;
                break;
            default:
                sMSType = 0;
        }
        return sMSType;
    }

    public static SMSType getSMSType(String str){
        SMSType sMSType = null;
        for (SMSType ut : SMSType.values()){
            if (ut.name().equals(str)){
                sMSType = ut;
                break;
            }
        }
        return sMSType;
    }
}
