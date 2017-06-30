package com.falace.workshop;

import com.falace.workshop.model.Talk;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


public class TalksFixtureProvider {

    public static List<Talk> createTalks() {
        return Stream.of(
                new Talk("Create better mocks for Spring Boot", 65),
                new Talk("More Java for people", 40),
                new Talk("Fun with Kotlin", 30),
                new Talk("Managing dependencies with Maven", 45),
                new Talk("Better error handling in Java", 45),
                new Talk("Scala from JEE guys", 5),
                new Talk("Slack for old people", 60),
                new Talk("Finance Domain explained", 45),
                new Talk("Healthier lunch in Berlin", 30),
                new Talk("Scope Future", 30),
                new Talk("Better work in Teams", 45),
                new Talk("Best Spring Features", 60),
                new Talk("Advance Spring Boot", 60),
                new Talk("Why Clojure Matters", 45),
                new Talk("Living in Berlin", 30),
                new Talk("Working with Azure", 30),
                new Talk("Maintain Java Code", 60),
                new Talk("Better Way of reading Books", 30),
                new Talk("What you need to know about ExtJS", 30)

        ).collect(toList());
    }
}
