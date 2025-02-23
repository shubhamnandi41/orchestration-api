package com.orchestration.Config;

import com.orchestration.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(StartupRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application startup: Loading users and reindexing...");
        String url = "https://dummyjson.com/users";
        userService.loadUsersFromUrl(url);
        userService.reindex();
    }
}
