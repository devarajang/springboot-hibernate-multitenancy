package cloudinvoice.wildfire.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cloudinvoice.wildfire.domain.Customer;
import cloudinvoice.wildfire.repository.CustomerRepository;

@Controller
@EnableAutoConfiguration
public class AppController {

	@Autowired
	CustomerRepository custromerRepository;
	
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
    
    
    @PostMapping(path="/customers")
    public ResponseEntity<String> createCustomer(@RequestBody Map<String,String> requestMap) {
    	Customer customer = new Customer(requestMap.get("firstName"), requestMap.get("lastName"), requestMap.get("idnumber"));
    	custromerRepository.save(customer);
    	return new ResponseEntity<String>("Success", null, HttpStatus.CREATED);
    }
   
}