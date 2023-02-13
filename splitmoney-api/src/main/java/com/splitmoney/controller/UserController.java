package com.splitmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.splitmoney.model.Expanse;
import com.splitmoney.model.Response;
import com.splitmoney.model.SearchExpanseDTO;
import com.splitmoney.model.SearchUserDTO;
import com.splitmoney.model.User;
import com.splitmoney.model.UserListDTO;
import com.splitmoney.repository.ExpanseRepository;
import com.splitmoney.repository.UserRepository;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ExpanseRepository expanseRepository;

    @CrossOrigin()
    @PostMapping("/users/register")
    public Object registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        System.out.println(newUser.getUsername());
        Response response = new Response();
        for (User user : users) {
            if (user.equals(newUser)) {
                System.out.println("User Already exists!");
                response.setStatus(203);
                response.setMessage("USER ALREADY EXISTS");
                return response;
            }
        }
        userRepository.save(newUser);
        response.setStatus(200);
        response.setMessage("User Created Successfully!!");
        response.setResponse(newUser);
        return response;
    }

    @CrossOrigin()
    @PostMapping("/users/login")
    public Object loginUser(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        Response response = new Response();
        for (User other : users) {
            if (other.equals(user)) {
            	other.setLoggedIn(true);
                userRepository.save(other);
                response.setStatus(200);
                response.setMessage("Login Successfully!!");
                response.setResponse(other);
                return response;
            }
        }
        
        response.setStatus(400);
        response.setMessage("Incorrect Username or Password!!");
        return response;
    }
    
    @CrossOrigin()
    @GetMapping("/users/allUser")
    public Object allUser() {
        List<User> users = userRepository.findAll();
        Response response = new Response(); 
        response.setStatus(200);
        response.setMessage("Success");
        response.setResponse(users);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/users/allUserList") 
    public Object allUserList(@Valid @RequestBody SearchExpanseDTO searchExpanseDTO) {
	        List<User> users = userRepository.findAll();
	        List<UserListDTO> userList = new ArrayList<UserListDTO>();
	        for(User user : users)
	        {
	        	if(user.getId() != searchExpanseDTO.getLendID()) {
	        		List<Expanse> plusExpanses = expanseRepository.findUserByLendidToOweid(searchExpanseDTO.getLendID(), user.getId());
	        		float plusAmount = (float) 0.0;
	        		for(Expanse plusExpanse : plusExpanses)
	                {
	        			plusAmount = (float) (plusAmount + plusExpanse.getAmount());
	                }
	        		List<Expanse> minExpanses = expanseRepository.findUserByLendidToOweid(user.getId(),searchExpanseDTO.getLendID());
	        		float minAmount = (float) 0.0;
	        		for(Expanse minExpanse : minExpanses)
	                {
	        			minAmount = (float) (minAmount + minExpanse.getAmount());
	                }
	        		float finalAmount = plusAmount - minAmount;
	        		UserListDTO userlistdto = new UserListDTO();
	        		userlistdto.setAmount(finalAmount);
	        		userlistdto.setFirstName(user.getFirstName());
	        		userlistdto.setLastName(user.getLastName());
	        		userlistdto.setId(user.getId());
	        		userlistdto.setUsername(user.getUsername());
	        		userList.add(userlistdto);
	        	}
	        }
	        Response response = new Response(); 
	        response.setStatus(200);
	        response.setMessage("Success");
	        response.setResponse(userList);
	        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/users/searchUser")
    public Object searchUser(@Valid @RequestBody SearchUserDTO searchUserDTO) {
        List<User> users = userRepository.findByfirstName(searchUserDTO.getName());
        Response response = new Response(); 
        response.setStatus(200);
        response.setMessage("Success");
        response.setResponse(users);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/users/logout")
    public Object logUserOut(@Valid @RequestBody User user) {
        List<User> users = userRepository.findAll();
        Response response = new Response();
        for (User other : users) {
            if (other.equals(user)) {
            	other.setLoggedIn(false);
                userRepository.save(other);
                response.setStatus(200);
                response.setMessage("Logout Successfully!!");
                return response;
            }
        }
        response.setStatus(400);
        response.setMessage("Logout Failed!!");
        return response;
    }
        
    
}