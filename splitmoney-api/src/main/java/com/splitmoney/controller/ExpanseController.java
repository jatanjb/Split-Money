package com.splitmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.splitmoney.model.Expanse;
import com.splitmoney.model.ExpanseDTO;
import com.splitmoney.model.GroupExpanseDTO;
import com.splitmoney.model.Response;
import com.splitmoney.model.SearchExpanseDTO;
import com.splitmoney.model.SingleSettleExpenseDTO;
import com.splitmoney.repository.ExpanseRepository;
import com.splitmoney.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

@RestController
public class ExpanseController {
    @Autowired
    ExpanseRepository expanseRepository;
    
    @Autowired
    UserRepository userRepository;

    @CrossOrigin()
    @PostMapping("/expanse/add")
    public Object addExpanse(@Valid @RequestBody ExpanseDTO expanseDTO) {
        Response response = new Response();
        Expanse expanse = new Expanse();
        expanse.setOweID(userRepository.findById(expanseDTO.getOweID()));
        expanse.setLendID(userRepository.findById(expanseDTO.getLendID()));
        expanse.setAmount(expanseDTO.getAmount());
        expanse.setDescription(expanseDTO.getDescription());
        
        expanseRepository.save(expanse);
        response.setStatus(200);
        response.setMessage("Expanse Added Successfully!!");
        response.setResponse(expanse);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/expanse/addGroupExpanse")
    public Object addGroupExpanse(@Valid @RequestBody GroupExpanseDTO expanseDTO) {
        Response response = new Response();
        String replace = expanseDTO.getOweID().replace("[","");
        String replace1 = replace.replace("]","");
        List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(", ")));
        for(String oweID : myList)
        {
        	Expanse expanse = new Expanse();
        	expanse.setOweID(userRepository.findById(Long.parseLong(oweID)));
            expanse.setLendID(userRepository.findById(expanseDTO.getLendID()));
            expanse.setAmount((expanseDTO.getAmount())/myList.size());
            expanse.setDescription(expanseDTO.getDescription());
            expanseRepository.save(expanse);
        }
        response.setStatus(200);
        response.setMessage("Expanse Added Successfully!!");
        response.setResponse(null);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/expanse/searchExpanseByLendId")
    public Object searchExpanseByLendId(@Valid @RequestBody SearchExpanseDTO searchExpanseDTO) {
        List<Expanse> expanse = expanseRepository.findUserByLendidToOweid(searchExpanseDTO.getLendID(), searchExpanseDTO.getOweID());
        Response response = new Response(); 
        response.setStatus(200);
        response.setMessage("Success");
        response.setResponse(expanse);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/expanse/settleExpanse")
    public Object settleExpanse(@Valid @RequestBody SearchExpanseDTO searchExpanseDTO) {
    	List<Expanse> expanses = expanseRepository.findUserByLendidToOweid(searchExpanseDTO.getLendID(), searchExpanseDTO.getOweID());
    	for(Expanse expanse : expanses)
        {
    		expanseRepository.deleteById(expanse.getId());
        }
    	List<Expanse> expanses2 = expanseRepository.findUserByLendidToOweid(searchExpanseDTO.getOweID(),searchExpanseDTO.getLendID());
    	for(Expanse expanse : expanses2)
        {
    		expanseRepository.deleteById(expanse.getId());
        }
        Response response = new Response(); 
        response.setStatus(200);
        response.setMessage("Settle SuccessFully");
        response.setResponse(null);
        return response;
    }
    
    @CrossOrigin()
    @PostMapping("/expanse/singleSettleExpanse")
    public Object singleSettleExpanse(@Valid @RequestBody SingleSettleExpenseDTO singleSettleExpenseDTO) {
    	expanseRepository.deleteById(singleSettleExpenseDTO.getId());
        Response response = new Response(); 
        response.setStatus(200);
        response.setMessage("Settle SuccessFully");
        response.setResponse(null);
        return response;
    }
    
    
}