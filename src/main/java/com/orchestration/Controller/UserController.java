//package com.orchestration.Controller;
//
//
//import com.orchestration.Entity.User;
//import com.orchestration.Service.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//
//    @Autowired
//    private  UserService userService;
//
//    @PostMapping("/load-users")
//    public ResponseEntity<?> loadUsers()
//    {
//        String jsonUrl = "https://dummyjson.com/users";
//        if(userService.loadUsersFromUrl(jsonUrl)) {
//            return new ResponseEntity<>("Users loaded successfully!", HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>("Cannot Load Users!",HttpStatus.NOT_FOUND);
//}
//    }
//
//
//    @PostMapping("/reindex")
//    public String reindex() {
//        userService.reindex();
//        return "Reindexing started!";
//    }
//
//
//
//}
