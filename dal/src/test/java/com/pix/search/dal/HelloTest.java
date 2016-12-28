package com.pix.search.dal;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HelloTest {

    @Test
    public void check() {
        assertThat(new Hello().toString(), is("Hello"));
    }


}
