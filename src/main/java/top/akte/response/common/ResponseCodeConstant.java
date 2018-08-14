package top.akte.response.common;

public enum ResponseCodeConstant {
    SUCCESS("000000", "成功"),
    FAILED_ORDER("010001", "下单失败"),
    PROCESSING("020001", "交易处理中"),
    DUBBO_UNKNOWN_EXCEPTION("030001", "网络未知异常"),
    DUBBO_NETWORK_EXCEPTION("030002", "网络异常"),
    DUBBO_TIME_OUT("030003", "请求超时"),
    DUBBO_BIZ_EXCEPTION("030004", "业务异常"),
    DUBBO_FORBIDDEN_EXCEPTION("030005", "网络禁止"),
    DUBBO_SERIALIZATION_EXCEPTION("030006", "网络序列化异常"),
    REQUEST_ILLEGAL("040001", "请求参数非法"),
    REQUEST_VALID("040002", "请求参数合法"),
    SYS_EXCEPTION("050001", "系统异常"),
    REMOTE_RESPONSE_NULL_EXCEPTION("050002", "系统异常，请确认请求是否成功再尝试发起"),
    OBJECT_CONVERT_FAILED("050003", "对象格式转换失败"),
    DB_EXCEPTION("050004", "数据库异常");

    private final String responseCode;
    private final String responseDesc;

    private ResponseCodeConstant(String responseCode, String responseDesc) {
        this.responseCode = responseCode;
        this.responseDesc = responseDesc;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getResponseDesc() {
        return this.responseDesc;
    }
}
