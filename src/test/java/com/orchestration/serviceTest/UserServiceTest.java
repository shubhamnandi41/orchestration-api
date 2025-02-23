package com.orchestration.serviceTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orchestration.Entity.User;
import com.orchestration.Exception.UserNotFoundException;
import com.orchestration.Repository.UserRepository;
import com.orchestration.Service.UserService;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.search.engine.search.predicate.SearchPredicate;
import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.engine.search.query.dsl.SearchQuerySelectStep;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.common.impl.HibernateOrmUtils;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestComponent
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

//    @Mock
//    private EntityManager entityManager;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private SearchSession searchSession;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John", "Doe", "john@example.com", "123-45-6789","johndoex","dummy","admin");
    }


//


    @Test
    void testGetById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testGetById_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(2L));
    }

    @Test
    void testGetByEmail_UserFound() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getByEmail("john@example.com");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testGetByEmail_UserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByEmail("unknown@example.com"));
    }



    @Test
    void testReindex() {

        EntityManager entityManager = mock(EntityManager.class);
        SearchSession mockSearchSession = mock(SearchSession.class);
        MassIndexer mockMassIndexer = mock(MassIndexer.class);

        UserService userService = new UserService(entityManager);

        try (MockedStatic<Search> mockedStatic = mockStatic(Search.class)) {

            mockedStatic.when(() -> Search.session(entityManager)).thenReturn(mockSearchSession);


            when(mockSearchSession.massIndexer()).thenReturn(mockMassIndexer);
            when(mockMassIndexer.start()).thenReturn(null);


            assertDoesNotThrow(userService::reindex);

            mockedStatic.verify(() -> Search.session(entityManager), times(1));
        }
    }
}
