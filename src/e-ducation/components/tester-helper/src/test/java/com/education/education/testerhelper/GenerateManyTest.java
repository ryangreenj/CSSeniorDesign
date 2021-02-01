package com.education.education.testerhelper;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
class GenerateManyTest {

    @Test
    void generateListOf_shouldReturnListOfSpecificSizeAndTypeString_whenTheParamFunctionReturnsAString() {
        final int list_size = getRandomNumberBetween(1,100);

        final List<String> list = GenerateMany.generateListOf(
                () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                list_size);

        assertThat(list.size()).isEqualTo(list_size);
        MatcherAssert.assertThat(list.get(0), instanceOf(String.class));
    }

    @Test
    void generateListOf_shouldReturnListOfSpecificSizeAndTypeInteger_whenTheParamFunctionReturnsAnInteger() {
        final int list_size = getRandomNumberBetween(1,100);

        final List<Integer> list = GenerateMany.generateListOf(
                () -> getRandomNumberBetween(5,20),
                list_size);

        assertThat(list.size()).isEqualTo(list_size);
        MatcherAssert.assertThat(list.get(0), instanceOf(Integer.class));
    }
}
