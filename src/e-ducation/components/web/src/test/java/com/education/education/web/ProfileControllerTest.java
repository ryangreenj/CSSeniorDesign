package com.education.education.web;

import com.education.education.profile.Profile;
import com.education.education.profile.ProfileService;
import com.education.education.web.models.CourseJoinRequest;
import com.education.education.web.models.ProfileCreationRequest;
import com.education.education.web.models.ProfileCreationResponse;
import com.education.education.web.models.ProfileRetrievalRequest;
import com.education.education.web.models.ProfileRetrievalResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.education.education.testerhelper.Chance.getRandomAlphaNumericString;
import static com.education.education.testerhelper.Chance.getRandomNumberBetween;
import static com.education.education.testerhelper.JsonString.asJsonString;
import static com.education.education.web.helpers.RandomProfile.getRandomCourseJoinRequest;
import static com.education.education.web.helpers.RandomProfile.getRandomProfileBuilder;
import static com.education.education.web.helpers.RandomProfile.getRandomProfileCreationRequest;
import static com.education.education.web.helpers.RandomProfile.getRandomProfileRetrievalRequest;
import static com.education.education.web.models.ProfileCreationResponse.aProfileCreationResponseBuilder;
import static com.education.education.web.models.mappers.ProfileToProfileRetrievalResponseMapper.mapProfileToProfileRetrievalResponse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    void createProfile_shouldReturnCreatedCode_andCallCreateProfile_andReturnProfileId() throws Exception {
        final ProfileCreationRequest profileCreationRequest = getRandomProfileCreationRequest();
        final String profileId = getRandomAlphaNumericString(getRandomNumberBetween(5, 20));
        final ProfileCreationResponse profileCreationResponse = aProfileCreationResponseBuilder().id(profileId).build();

        when(profileService.createProfile(profileCreationRequest.getUsername())).thenReturn(profileId);

        this.mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(profileCreationRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(profileCreationResponse)));

        verify(profileService).createProfile(profileCreationRequest.getUsername());
    }

    @Test
    void getProfile_shouldCallGetProfile_andReturnProfileRetrievalResponse() throws Exception {
        final ProfileRetrievalRequest profileRetrievalRequest = getRandomProfileRetrievalRequest();
        final Profile profile = getRandomProfileBuilder().id(profileRetrievalRequest.getProfileId()).build();
        final ProfileRetrievalResponse profileCreationResponse = mapProfileToProfileRetrievalResponse(profile);

        when(profileService.getProfile(profileRetrievalRequest.getProfileId())).thenReturn(profile);

        this.mockMvc.perform(get("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(profileRetrievalRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(profileCreationResponse)));

        verify(profileService).getProfile(profileRetrievalRequest.getProfileId());
    }

    @Test
    void joinClass_shouldCallJoinClass_andReturnOk() throws Exception {
        final CourseJoinRequest courseJoinRequest = getRandomCourseJoinRequest();

        this.mockMvc.perform(post("/profile/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(courseJoinRequest)))
                .andExpect(status().isOk());

        verify(profileService).joinClass(courseJoinRequest.getProfileId(), courseJoinRequest.getCourseId());
    }

    @Test
    void createClass_shouldCallCreateClass_andReturnOk() throws Exception {
        final CourseJoinRequest courseJoinRequest = getRandomCourseJoinRequest();

        this.mockMvc.perform(post("/profile/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(courseJoinRequest)))
                .andExpect(status().isOk());

        verify(profileService).createClass(courseJoinRequest.getProfileId(), courseJoinRequest.getCourseId());
    }
}
