package com.soen342.sniffnjack;

import com.soen342.sniffnjack.Entity.Instructor;
import com.soen342.sniffnjack.Repository.InstructorRepository;
import com.soen342.sniffnjack.Repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class AdminControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Test
    public void givenWrongCredentials_whenAddAdmin_thenUnauthorized() throws Exception{
        Instructor instructor = new Instructor();
        instructor.setFirstName(TestcontainersConfiguration.instructorFirstName);
        instructor.setLastName(TestcontainersConfiguration.instructorLastName);
        instructor.setEmail(TestcontainersConfiguration.instructorEmail);
        instructor.setPassword(TestcontainersConfiguration.instructorPassword);
        instructor.setPhone(TestcontainersConfiguration.instructorPhone);
        instructor.setRole(roleRepository.findByName(instructor.getRole().getName()));
        instructor = instructorRepository.save(instructor);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TestcontainersConfiguration.instructorEmail, TestcontainersConfiguration.instructorPassword);

        mockMvc.perform(post("/admins/add").contentType("application/json").headers(headers))
                .andExpect(status().isUnauthorized());

        instructorRepository.delete(instructor);
    }
}
