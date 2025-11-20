package vn.spring.laptopshop.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import vn.spring.laptopshop.service.validator.RegisterChecked;

@RegisterChecked
public class RegisterDTO {

  @Size(min = 1, message = "Họ phải có tối thiểu 1 kí tự")
  private String firstName;

  @Size(min = 1 , message = "Tên phải có ít nhất 1 kí tự")
  private String lastName;

  @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;

  @Size(min = 6, message = "Mật khẩu phải có tối thiểu 6 kí tự")
  private String password;

  private String confirmPassword;

  private String avatar ;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

}
