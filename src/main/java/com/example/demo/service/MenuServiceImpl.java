package com.example.demo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MenuDao;
import com.example.demo.exception.ApplicationException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MenuServiceImpl implements MenuService {

//    @Autowired
//    private MenuDao menuDao;

    private final CircuitBreaker circuitBreaker;

    // Constructor for injecting CircuitBreaker
    public MenuServiceImpl() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .slidingWindowSize(5)
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(5000))
            .permittedNumberOfCallsInHalfOpenState(2)
            .minimumNumberOfCalls(3)
            .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        this.circuitBreaker = registry.circuitBreaker("menu-service");
    }

    @Override
    public List<String> getDrinks(String menuObj) throws Throwable {
        // Wrap the logic with CircuitBreaker
        return CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
            List<String> response = new ArrayList<>();
            // Simulate getting drinks and possibly failing
//            response = menuDao.getDrinks(menuObj);
            if (response.isEmpty()) {
                throw new ApplicationException("Drinks not found", "Please check the inventory");
            }
            return response;
        }).get();  // Use .get() to execute the decorated supplier
    }

    // Fallback method for CircuitBreaker
    public List<String> fallbackMethod(String menuObj, Throwable e) {
        log.error("Circuit breaker is opened and fallback method working", e);
        return new ArrayList<>();
    }
}
