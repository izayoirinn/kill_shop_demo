package top.izayoirinn.common;

import lombok.Data;

import java.util.Map;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 14:46
 * <pre>
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 * 				200：表示成功
 * 			    201：消息入队，等待处理
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				555：异常抛出信息
 * </pre>
 */
@Data
public class JsonResult<T> {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    /*
        //================= ~ Constructor ~ ==========================
    */

    private JsonResult() {

    }

    private JsonResult(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult(T data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    /*
        //================= ~ build ~ ==========================
   */
    public static <T> JsonResult<T> build(Integer status, String msg, T data) {
        return new JsonResult<T>(status, msg, data);
    }

    public static <T> JsonResult<T> delayOrder() {
        return new JsonResult<T>(201, "等待中", null);
    }

    public static <T> JsonResult<T> ok(T data) {
        return new JsonResult<T>(data);
    }

    public static <T> JsonResult<T> ok() {
        return new JsonResult<T>(null);
    }

    public static <T> JsonResult<T> errorMsg(String msg) {
        return new JsonResult<>(500, msg, null);
    }

    public static JsonResult<Map<String, String>> errorMap(Map<String, String> data) {
        return new JsonResult<>(501, "error", data);
    }

    public static <T> JsonResult<T> errorException(String msg) {
        return new JsonResult<T>(555, msg, null);
    }

}
