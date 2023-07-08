package site.woulduduo.dto.request.user;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import site.woulduduo.enumeration.Gender;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRegisterRequestDTO {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String userEmail;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 8, message = "닉네임은 2~8자리여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "닉네임은 한글, 영문, 숫자만 포함해야 합니다.")
    private String userNickname;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8~16자리여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String userPassword;

    @NotNull(message = "생년월일은 필수 입력값입니다.")
    @Past(message = "유효한 날짜여야 합니다.")
    private LocalDate userBirthday;

    @Size(max = 30, message = "인스타그램 아이디는 최대 30자리까지 입력 가능합니다.")
    private String userInstagram;

    @Size(max = 30, message = "트위터 아이디는 최대 30자리까지 입력 가능합니다.")
    private String userTwitter;

    @Size(max = 30, message = "페이스북 아이디는 최대 30자리까지 입력 가능합니다.")
    private String userFacebook;

    @NotBlank(message = "롤 닉네임은 필수 입력값입니다.")
    @Size(max = 10, message = "롤 닉네임은 최대 10자리까지 입력 가능합니다.")
    private String lolNickname;

    @NotNull(message = "성별은 필수 입력값입니다.")
    private Gender userGender;

    @Builder.Default
    private MultipartFile[] profileImages = new MultipartFile[0];

    private String[] profileImagePaths;



    // 생일 입력 방식을 (yyyy-mm-dd -> yyyy.mm.dd)로 바꾸는 메서드
    public void setUserBirthday(String userBirthday) {
        LocalDate birthday = LocalDate.parse(userBirthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.userBirthday = birthday;
    }
}
