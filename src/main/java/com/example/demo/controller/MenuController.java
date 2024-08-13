package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ApplicationException;
import com.example.demo.service.MenuService;

@RestController
@RequestMapping("/rest/menu")
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping("/{drinks}")
    public List<String> getDrinks(@PathVariable String drinks) throws ApplicationException {
		List<String> response = new ArrayList<>();
//		try {
			response = menuService.getDrinks(drinks);
			if(response.isEmpty()) {
				throw new ApplicationException("Drinks not found", "Please check the inventory");
			}
//		} catch (ApplicationException e) {
//			throw new ApplicationException("Drinks not found", e.getErrorMessage());
//		}
        return response;
    }
	
	
//	@GetMapping("/{sandwiches}")
//    public String getSandwiches(@PathVariable int index) {
//        return users.get(index);
//    }
//	
//	@GetMapping("/{burgers}")
//    public String getBurgers(@PathVariable int index) {
//        return users.get(index);
//    }
	
	
}
