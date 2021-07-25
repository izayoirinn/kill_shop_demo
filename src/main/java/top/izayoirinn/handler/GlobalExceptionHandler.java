package top.izayoirinn.handler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.izayoirinn.common.JsonResult;
import top.izayoirinn.exception.GlobalException;

/**
 * @author Rinn Izayoi
 * @date 2021/7/18 12:33
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<Object> BindExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldErrors = bindingResult.getFieldError();
        return JsonResult.errorMsg(fieldErrors != null ? fieldErrors.getDefaultMessage() : "参数异常");
    }

    @ExceptionHandler(MySQLSyntaxErrorException.class)
    public JsonResult<Object> BindExceptionHandler(MySQLSyntaxErrorException e) {
        e.printStackTrace();
        return JsonResult.errorMsg("查询错误...");
    }

    @ExceptionHandler(GlobalException.class)
    public JsonResult<Object> BindExceptionHandler(GlobalException e) {
        e.printStackTrace();
        return JsonResult.errorMsg("自定义抛出异常:" + e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public JsonResult<String> ExceptionHandler(Exception e) {
//        System.out.println(e);
        e.printStackTrace();
        return JsonResult.errorException(e.getMessage());
    }
}
