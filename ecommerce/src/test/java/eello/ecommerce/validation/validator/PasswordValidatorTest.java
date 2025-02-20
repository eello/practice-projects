package eello.ecommerce.validation.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordValidatorTest {

    private PasswordValidator pv = new PasswordValidator();

    @Test
    public void test() {
        // 8~20자리의 문자로만 이루어진 문자열
        assertThat(isValid("aws1225")).isFalse();
        assertThat(isValid("aws12157")).isTrue();
        assertThat(isValid("aws12157aws12157aws12")).isFalse();
        assertThat(isValid("aws12157aws12157aws1")).isTrue();

        // 한 문자 종류로만 이루어진 문자열
        assertThat(isValid("156019832")).isFalse();
        assertThat(isValid("ajdkwpdkv")).isFalse();
        assertThat(isValid("!@#$%^&**()")).isFalse();

        // 연속된 문자가 3개 이상이 포함된 문자열
        assertThat(isValid("abc115533")).isFalse();
        assertThat(isValid("aaa147492")).isFalse();
        assertThat(isValid("cba147292")).isFalse();
        assertThat(isValid("aws123964")).isFalse();
        assertThat(isValid("aws111964")).isFalse();
        assertThat(isValid("aws321964")).isFalse();

        // 올바르지 않은 문자가 포함된 문자열
        assertThat(isValid("!%%@235@%☺️")).isFalse();
        assertThat(isValid("!%%@235@%가")).isFalse();

        // 올바른 비밀번호
        assertThat(isValid("a!b252123")).isTrue();
        assertThat(isValid("!%ls20akvw")).isTrue();
        assertThat(isValid("07693bawo5l")).isTrue();
        assertThat(isValid("!%%@235@%")).isTrue();

    }

    private boolean isValid(String password) {
        return pv.isValid(password, null);
    }
}