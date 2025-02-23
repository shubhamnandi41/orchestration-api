package com.orchestration.Controller;

import com.orchestration.Entity.User;
import com.orchestration.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    @Operation(summary = "Finds user by id", description = "Finds a user by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Found"),
            @ApiResponse(responseCode = "404", description = "User not Found")
    })
    public ResponseEntity<?> findById(@PathVariable Long id)
    {
        Optional<User> user = userService.getById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Could Not Find any user with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Finds user by email", description = "Finds a user by their email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User Found"),
            @ApiResponse(responseCode = "404", description = "User not Found")
    })
    public ResponseEntity<?> findByEmail(@PathVariable String email)
    {

        Optional<User> user = userService.getByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could Not Find any user with email: " + email, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Performs a free-text search on first name, last name, and SSN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of matching users"),
            @ApiResponse(responseCode = "400", description = "Invalid search query")
    })
    public ResponseEntity<?> searchUsers(@RequestParam String query)
    {
        Optional<List<User>> users = userService.searchUsers(query);
        if(!users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
