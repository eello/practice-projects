package eello.ecommerce.validation.validator;

import eello.ecommerce.validation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    // 영문자, 숫자, 특수문자를 포함한 8~20자리 문자열을 검사하는 정규표현식
    // 사용가능한 특수문자 ! @ # $ % ^ & * ( ) _ + - = [ ] { } ; ' : " , . < > ? / \ `
    public static final String PASSWORD_BASE_REGEX = "^[A-Za-z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?/`\\\\]{8,20}$";
    public static final Pattern PASSWORD_BASE_PATTERN = Pattern.compile(PASSWORD_BASE_REGEX);

    private static final int ALPHABET = 0, DIGIT = 1, SPECIAL = 2;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!PASSWORD_BASE_PATTERN.matcher(value).matches()) {
            return false;
        }

        // comb == 1(1 << ALPHABET): 영문자로만 이루어진 문자열, 2(1 << DIGIT): 숫자로만 이루어진 문자열, 4(1 << SPECIAL): 특수문자로만 이루어진 문자열
        // 이외에는 두 종류의 문자로 이루어진 문자열
        int comb = 0;

        char[] charArray = value.toCharArray();
        int continuous = 0; // 연속된 문자 수 ex) "123" = 3, "ab" = 2
        int same = 0; // 같은 문자가 연속으로 등장한 횟수 ex) "aa" = 2, "aaa" = 3
        for (int i = 0; i < charArray.length; i++) {
            comb |= 1 << getType(charArray[i]);

            if (i == 0) {
                continue;
            }

            int diff = charArray[i] - charArray[i - 1];
            if (Math.abs(diff) == 1) {
                continuous += diff;
            } else continuous = 0;

            if (diff == 0) {
                same++;
            } else same = 0;

            if (Math.abs(continuous) == 2 || same == 2) {
                // 연속된 문자가 3개 이상 이어지거나 같은 문자가 3번 연속 등장하는 경우
                return false;
            }
        }

        return comb != 1 << ALPHABET && comb != 1 << DIGIT && comb != 1 << SPECIAL;
    }

    private int getType(char ch) {
        if (Character.isLetter(ch)) {
            return ALPHABET;
        } else if (Character.isDigit(ch)) {
            return DIGIT;
        } else return SPECIAL;
    }
}
