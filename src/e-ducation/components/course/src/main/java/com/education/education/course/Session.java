package com.education.education.course;

import lombok.Builder;

import java.util.List;

@Builder
public class Session {

    private final String id;
    private final List<Promptlet> promptlets;
}
