package com.company.klinikTani.module.authentication.service;

import com.company.klinikTani.exception.AuthenticateFailedException;
import com.company.klinikTani.exception.RegisterFailedException;
import com.company.klinikTani.exception.ResourceNotFoundException;
import com.company.klinikTani.module.authentication.entity.User;
import com.company.klinikTani.module.authentication.entity.UserDetails;
import com.company.klinikTani.utility.ModelMapperUtility;
import com.company.klinikTani.utility.RestTemplateUtility;
import com.company.klinikTani.module.authentication.entity.Role;
import com.company.klinikTani.module.authentication.entity.RoleEnum;
import com.company.klinikTani.module.authentication.repository.RoleRepository;
import com.company.klinikTani.module.authentication.repository.UserDetailsRepository;
import com.company.klinikTani.module.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${resource.server.url}")
    private String BASE_URL;
    private static final String ADD_NEW_CUSTOMER_ENDPOINT = "/api/v1/customers";
    private static final String ADD_NEW_BANK_ACCOUNT_ENDPOINT = "/api/v1/accounts?customerId=";
    private static final String FIND_CUSTOMER_ENDPOINT = "/api/v1/customers/find";
    private static final String FIND_CUSTOMER_ENDPOINT_ID_CARD_NUMBER_PARAMETER = "idCardNumber=";
    private static final String FIND_CUSTOMER_ENDPOINT_ACCOUNT_NUMBER_PARAMETER = "accountNumber=";
    private static final String IDR_CURRENCY = "IDR";
    private static final Integer INITIAL_BALANCE = 1000000000;
    @Autowired
    private ModelMapperUtility modelMapperUtility;
    @Autowired
    private RestTemplateUtility restTemplateUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
//    @Autowired
    @Override
    @Transactional
    public void register(User userRequestBody, UserDetails userDetailsRequestBody) {
        if (userDetailsRepository.findByMobileNumber(userDetailsRequestBody.getMobileNumber()).isPresent() ||
                userRepository.findByEmail(userRequestBody.getEmail()).isPresent()) {
            throw new RegisterFailedException("Email address or mobile number already registered");
        }

//        ResponseEntity<AddNewCustomerResponse> addNewCustomerResponse = addCustomer(
//                userDetailsRequestBody.getFullName());

//        ResponseEntity<AddNewBankAccountResponse> addNewBankAccountResponse = addBankAccount(
//                Objects.requireNonNull(addNewCustomerResponse.getBody()).getCustomerId());

        Role userRole = roleRepository.findByRoleName(RoleEnum.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User newUser = userRepository.save(
                new User(
                        userRequestBody.getEmail(),
                        passwordEncoder.encode(userRequestBody.getPassword()),
                        userRole));

        UserDetails newUserDetails = modelMapperUtility.initialize()
                .map(userDetailsRequestBody, UserDetails.class);
        newUserDetails.setUser(newUser);
        userDetailsRepository.save(newUserDetails);

    }

    @Override
    public void login(String email, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException exception) {
            throw new AuthenticateFailedException("Incorrect email or password");
        }
    }

//    private Boolean isIdCardRegisteredAtServer(String idCardNumber) {
//        try {
//            restTemplateUtility.initialize().getForObject(
//                    BASE_URL + FIND_CUSTOMER_ENDPOINT + "?" +
//                            FIND_CUSTOMER_ENDPOINT_ID_CARD_NUMBER_PARAMETER + idCardNumber + "&" +
//                            FIND_CUSTOMER_ENDPOINT_ACCOUNT_NUMBER_PARAMETER,
//                    Object.class);
//        } catch (HttpClientErrorException exception) {
//            return false;
//        }
//        return true;
//    }

//    private ResponseEntity<AddNewCustomerResponse> addCustomer(String fullName) {
//        AddNewCustomerRequest request = new AddNewCustomerRequest();
//        request.setFullName(fullName);
//
//        HttpEntity<AddNewCustomerRequest> requestBody = new HttpEntity<>(request);
//        return restTemplateUtility.initialize().postForEntity(
//                BASE_URL + ADD_NEW_CUSTOMER_ENDPOINT,
//                requestBody,
//                AddNewCustomerResponse.class);
//    }
//
//    private ResponseEntity<AddNewBankAccountResponse> addBankAccount(String customerId) {
//        AddNewBankAccountRequest request = new AddNewBankAccountRequest();
//        request.setCurrency(IDR_CURRENCY);
//        request.setBalance(new BigDecimal(INITIAL_BALANCE));
//
//        HttpEntity<AddNewBankAccountRequest> requestBody = new HttpEntity<>(request);
//        return restTemplateUtility.initialize().postForEntity(
//                BASE_URL + ADD_NEW_BANK_ACCOUNT_ENDPOINT + customerId ,
//                requestBody,
//                AddNewBankAccountResponse.class);
//    }
}
