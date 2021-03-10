package com.education.education.web;

import com.education.education.course.CourseService;
import com.education.education.profile.Profile;
import com.education.education.profile.ProfileService;
import com.education.education.web.models.ActivePromptletRequest;
import com.education.education.web.models.ActiveSessionRequest;
import com.education.education.web.models.CourseCreationRequest;
import com.education.education.web.models.CourseCreationResponse;
import com.education.education.web.models.CourseRequest;
import com.education.education.web.models.CourseResponse;
import com.education.education.web.models.PromptletAnswer;
import com.education.education.web.models.PromptletCreationRequest;
import com.education.education.web.models.PromptletRetrievalRequest;
import com.education.education.web.models.PromptletRetrievalResponse;
import com.education.education.web.models.SessionCreationRequest;
import com.education.education.web.models.SessionResponse;
import com.education.education.web.models.SessionRetrievalRequest;
import com.education.education.web.models.UserResponseRequest;
import com.education.education.web.models.UserResponseResponse;
import com.education.education.web.models.mappers.CourseToCourseResponseMapper;
import com.education.education.web.models.mappers.PromptletToPromptletRetrievalResponseMapper;
import com.education.education.web.models.mappers.SessionToSessionResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static com.education.education.web.models.UserResponseResponse.aUserResponseResponseBuilder;
import static java.util.stream.Collectors.toList;

@RequestMapping("/course")
@RestController
@EnableWebMvc
public class CourseController {

    private final CourseService courseService;

    private final ProfileService profileService;

    @Autowired
    public CourseController(final CourseService courseService, final ProfileService profileService) {
        this.courseService = courseService;
        this.profileService = profileService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseCreationResponse createCourse(@RequestBody final CourseCreationRequest courseCreationRequest){
        return new CourseCreationResponse(courseService.createCourse(courseCreationRequest.getCourseName()));
    }

    @PutMapping("")
    public List<CourseResponse> getCourses(@RequestBody final CourseRequest courseRequest){
        return courseService.getCourses(courseRequest.getCourseIds())
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(toList());
    }

    @PutMapping("/all")
    public List<CourseResponse> getAllCourses(){
        return courseService.getAllCourses()
                .stream()
                .map(CourseToCourseResponseMapper::mapCourseToCourseResponse)
                .collect(toList());
    }

    @PostMapping("/activeSession")
    public void setActiveSession(@RequestBody final ActiveSessionRequest activeSessionRequest){
        courseService.setActiveSession(activeSessionRequest.getCourseId(), activeSessionRequest.getSessionId());
    }

    @PostMapping("/session")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSession(@RequestBody final SessionCreationRequest sessionCreationRequest){
        courseService.addSession(sessionCreationRequest.getCourseId(), sessionCreationRequest.getSessionName());
    }

    @PutMapping("/session")
    public List<SessionResponse> getSessions(@RequestBody final SessionRetrievalRequest sessionRetrievalRequest){
        return courseService.getSessions(sessionRetrievalRequest.getSessionIds())
                .stream()
                .map(SessionToSessionResponseMapper::mapSessionToSessionResponse)
                .collect(toList());
    }

    @PostMapping("/session/promptlet")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPromptlet(@RequestBody final PromptletCreationRequest promptletCreationRequest){
        courseService.addPromptletToSession(
                promptletCreationRequest.getSessionId(),
                promptletCreationRequest.getPrompt(),
                promptletCreationRequest.getPromptlet_type(),
                promptletCreationRequest.getAnswerPool(),
                promptletCreationRequest.getCorrectAnswer());
    }

    @PutMapping("/session/promptlet")
    public List<PromptletRetrievalResponse> getPromptlets(@RequestBody final PromptletRetrievalRequest promptletRetrievalRequest){
        return courseService.getPromptlets(promptletRetrievalRequest.getPromptletIds())
                .stream()
                .map(PromptletToPromptletRetrievalResponseMapper::mapPromptletToPromptletRetrievalResponse)
                .collect(toList());
    }

    @PostMapping("/session/promptlet/active")
    public void activatePromptlet(@RequestBody final ActivePromptletRequest activePromptletRequest){
        courseService.activatePromptlet(activePromptletRequest.getSessionId(),activePromptletRequest.getPromptletId(), activePromptletRequest.isStatus());
    }

    @PostMapping("/session/promptlet/answer")
    @ResponseStatus(HttpStatus.CREATED)
    public void answerPromptlet(@RequestBody final PromptletAnswer promptletAnswer){
        final Profile p = profileService.getProfile(promptletAnswer.getProfileId());
        courseService.answerPromptlet(promptletAnswer.getActiveSessionId(), promptletAnswer.getPromptletId(),
                promptletAnswer.getProfileId(),p.getUsername(), promptletAnswer.getResponse());
    }

    @PutMapping("/session/promptlet/answers")
    public List<UserResponseResponse> getResponses(@RequestBody final UserResponseRequest userResponseRequest){
        return courseService.getPromptletResponses(userResponseRequest.getUserResponseIds())
                    .stream()
                    .map(x -> aUserResponseResponseBuilder()
                            .id(x.getId())
                            .profileName(profileService.getProfile(x.getProfileId()).getUsername())
                            .profileId(x.getProfileId())
                            .timestamp(x.getTimestamp())
                            .response(x.getResponse()).build())
                .collect(toList());
    }
}
