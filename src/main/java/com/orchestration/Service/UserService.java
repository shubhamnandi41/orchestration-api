package com.orchestration.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orchestration.Entity.User;
import com.orchestration.Exception.UserNotFoundException;
import com.orchestration.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private UserSearchRepository userSearchRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger(UserService.class);




    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public boolean loadUsersFromUrl(String url){
        try {
//            String url = "https://dummyjson.com/users";
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode usersNode = rootNode.get("users"); // Extract the array

            List<User> users = objectMapper.readValue(usersNode.toString(), new TypeReference<List<User>>() {});
            if(users==null || users.isEmpty())
            {
                logger.warn("No users were extracted, skipping save all");
                return false;
            }
            userRepository.saveAll(users);
            logger.info("User Loaded Successfully!");
            return true;
        } catch (Exception e) {

            logger.info("Error loading user: ",e);
            return false;
        }
    }


    @Transactional
    public void reindex(){
        logger.info("Starting full-text reindexing...");
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().start();
            logger.info("Reindexing completed successfully");

        } catch (Exception e) {
            logger.error("Error during reindexing",e);
        }
    }



    public Optional<User> getById(Long id){
        logger.info("Fetching user with ID: {}", id);
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("User with ID {} not found", id);
                    return new UserNotFoundException("User with ID " + id + " not found");
                }));
    }

    public Optional<User> getByEmail(String email){
        logger.info("Fetching user with Email: {}", email);
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User with Email {} not found", email);
                    return new UserNotFoundException("User with Email " + email + " not found");
                }));
    }

    @Transactional
    public Optional<List<User>> searchUsers(String keyword){
        logger.info("Executing search for keyword: {}", keyword);

        SearchSession searchSession = Search.session(entityManager);
        List<User> users = searchSession.search(User.class)
                .where(f -> f.wildcard()
                        .fields("firstName", "lastName","ssn")
                        .matching(keyword.toLowerCase()+"*"))
                .fetchHits(10);

        return Optional.ofNullable(Optional.ofNullable(users)
                .orElseThrow(() -> {
                    logger.warn("No Search results for keyword '{}'", keyword);
                    return new UserNotFoundException("User with Keyword '" + keyword + "' not found");
                }));

    }

}
