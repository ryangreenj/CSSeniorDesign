package com.education.education.web.helpers;

import com.education.education.course.Course;
import com.education.education.profile.Profile;
import com.education.education.testerhelper.GenerateMany;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseJoinRequest;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.ProfileCreationRequest;
import com.education.education.web.models.ProfileCreationResponse;
import com.education.education.web.models.ProfileRetrievalRequest;

import static com.education.education.course.Course.aCourseBuilder;
import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.GenerateMany.generateListOf;
import static com.education.education.web.models.ProfileCreationResponse.aProfileCreationResponseBuilder;

public class RandomProfile {

    public static Profile.ProfileBuilder getRandomProfileBuilder(){
        return Profile.aProfileBuilder()
                .id(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .username(getRandomAlphaNumericString(getRandomNumberBetween(5,20)))
                .coursesEnrolled(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)))
                .coursesOwned(GenerateMany.generateListOf(
                        () -> getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                        getRandomNumberBetween(0,10)));
    }

    public static ProfileCreationRequest getRandomProfileCreationRequest(){
        return new ProfileCreationRequest(getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }

    public static ProfileRetrievalRequest getRandomProfileRetrievalRequest(){
        return new ProfileRetrievalRequest(getRandomAlphaNumericString(getRandomNumberBetween(5,20)));
    }

    public static CourseJoinRequest getRandomCourseJoinRequest(){
        return new CourseJoinRequest(
                getRandomAlphaNumericString(getRandomNumberBetween(5,20)),
                getRandomAlphaNumericString(getRandomNumberBetween(5,20))
        );
    }

}
