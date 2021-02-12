package com.education.education.web;

import com.education.education.profile.ProfileService;
import com.education.education.web.models.CourseJoinRequest;
import com.education.education.web.models.ProfileCreationRequest;
import com.education.education.web.models.ProfileCreationResponse;
import com.education.education.web.models.ProfileRetrievalRequest;
import com.education.education.web.models.ProfileRetrievalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.education.education.web.models.ProfileCreationResponse.aProfileCreationResponseBuilder;
import static com.education.education.web.models.mappers.ProfileToProfileRetrievalResponseMapper.mapProfileToProfileRetrievalResponse;

@RestController
@RequestMapping("/profile")
@EnableWebMvc // TODO: What does this do
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(final ProfileService profileService){
        this.profileService = profileService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileCreationResponse createProfile(@RequestBody final ProfileCreationRequest profileCreationRequest){
        return aProfileCreationResponseBuilder()
                .id(profileService.createProfile(profileCreationRequest.getUsername()))
                .build();
    }

    @GetMapping("")
    public ProfileRetrievalResponse getProfile(@RequestBody final ProfileRetrievalRequest profileRetrievalRequest){
        return mapProfileToProfileRetrievalResponse(profileService.getProfile(profileRetrievalRequest.getProfileId()));
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinClass(@RequestBody final CourseJoinRequest courseJoinRequest){
        profileService.joinClass(courseJoinRequest.getProfileId(), courseJoinRequest.getCourseId());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public void createClass(@RequestBody final CourseJoinRequest courseJoinRequest){
        profileService.createClass(courseJoinRequest.getProfileId(), courseJoinRequest.getCourseId());
    }
}
