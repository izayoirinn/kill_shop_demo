package top.izayoirinn.bo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author Rinn Izayoi
 * @date 2021/7/12 18:42
 */
@Data
@ToString
public class UserBO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 26, message = "密码长度在6-26位之间")
    private String password;
}
