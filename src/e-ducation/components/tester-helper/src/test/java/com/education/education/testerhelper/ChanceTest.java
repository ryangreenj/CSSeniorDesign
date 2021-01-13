package com.education.education.testerhelper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {Chance.class})
class ChanceTest {

    @Test
    void getRandomAlphaNumericString_shouldReturnStringOfDesiredLength() {
        final int length = new Random().nextInt(100);
        final String s = getRandomAlphaNumericString(length);

        assertThat(s.length()).isEqualTo(length);
    }

    @Test
    void getRandomNumberBetween_shouldReturnANumberWithinBounds() {
        final int l_bound = new Random().nextInt(Integer.MAX_VALUE / 2);
        final int u_bound = new Random().nextInt(Integer.MAX_VALUE / 2) + l_bound;

        final int actual = getRandomNumberBetween(l_bound, u_bound);

        assertThat(actual).isGreaterThanOrEqualTo(l_bound);
        assertThat(actual).isLessThanOrEqualTo(u_bound);
    }
}
