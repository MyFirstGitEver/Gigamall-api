package com.example.gigamall.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gigamall.entities.ConfirmationEntity;
import com.example.gigamall.entities.UserEntity;
import com.example.gigamall.repositories.ConfirmationRepository;
import com.example.gigamall.repositories.UserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RequestMapping("api/user/")
@RestController
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ConfirmationRepository confirmationRepo;
	
	@DeleteMapping("/verify/{id}/{code}")
	public ResponseEntity<Integer> verify(
			@PathVariable("id") int id, 
			@PathVariable("code") String code){
		Optional<ConfirmationEntity> confirmation = confirmationRepo.findById(id);

		if(!confirmation.get().getCode().equals(code)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1);
		}
		
		ConfirmationEntity e = confirmation.get();
		
		int userId = e.getUserId();
		confirmationRepo.delete(e);
		if(new Date().getTime() - e.getCreatedDate().getTime() >= 300000) {
			userRepo.deleteById(userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(-2);
		}
		
		return ResponseEntity.ok(userId);
	}
	
	@PostMapping("/complete/{userId}")
	public ResponseEntity<String> complete(
			@RequestBody String pass,
			@PathVariable int userId) {
		pass = pass.substring(1, pass.length() - 1);
		
        String hashed = BCrypt.withDefaults().hashToString(12, pass.toCharArray());
		
		userRepo.updatePassword(hashed, userId);
		
		return ResponseEntity.ok(userRepo.findById(userId).get().getUserName());
	}
	
	@GetMapping("/login/{password}/{userName}")
	public ResponseEntity<UserEntity> isValidUser(
			@PathVariable String password,
			@PathVariable String userName){
		
		UserEntity user = fetchUser(userName, password);
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		ConfirmationEntity e =
				confirmationRepo.getConfirmationByUserId(user.getId());
		
		if(e != null) {
			if(new Date().getTime() - e.getCreatedDate().getTime() >= 300000) {
				Random random = new Random();
				StringBuilder code = new StringBuilder();
				
				for(int i=0;i<4;i++) {
					code.append(Integer.toString(random.nextInt(9)));
				}
				
				String codeStr = code.toString();
				sendNewCode(user.getUserName(), codeStr);
				
				confirmationRepo.updateCodeAndTime(codeStr, new Date(), e.getId());
			}
			
			UserEntity dummyUser = new UserEntity("asd", "das", "dadsa", "", 0);
			dummyUser.setId(e.getId());
			
			return ResponseEntity.status(HttpStatus.IM_USED).body(dummyUser);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PostMapping("/register/{usingGoogle}")
	public ResponseEntity<Integer> register(
			@PathVariable int usingGoogle,
			@RequestBody UserEntity user) {
		int userId = userRepo.save(user).getId();
		
		if(usingGoogle == 0) {
			return ResponseEntity.ok(userId);
		}
		
		Random random = new Random();
		
		StringBuilder code = new StringBuilder();
		
		for(int i=0;i<4;i++) {
			code.append(Integer.toString(random.nextInt(9)));
		}
		
		int id =
				confirmationRepo.save(new ConfirmationEntity(
						user.getId(), 
						code.toString(),
						new Date())).getId();
		
		
        sendNewCode(user.getUserName(), code.toString());
        return ResponseEntity.status(HttpStatus.OK).body(id);
	}
	
	private void sendNewCode(String email, String code) {
		String host="smtp.gmail.com";
        final String userName="21521967@gm.uit.edu.vn";
        final String pass="wfmqdepxxmnoaccy";

        Properties props=new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

        Session session = Session.getDefaultInstance(props,  
                 new javax.mail.Authenticator() {  
                  protected PasswordAuthentication getPasswordAuthentication() {  
                   return new PasswordAuthentication(userName,pass);  
                   }  
                });  

        try {
            MimeMessage message = new MimeMessage(session); 
             message.setFrom(new InternetAddress(userName));  
             message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));  
             message.setSubject("Test");
             
             message.setContent("Mã xác nhận của bạn là: <b>" + code, 
            		 "text/html; charset=utf-8");  

            Transport.send(message);
            System.out.println("done");

        } catch (MessagingException e) 
        {
            e.printStackTrace();
        }
	}
	
	private UserEntity fetchUser(String userName, String password) {
		UserEntity e = userRepo.login(userName);
		
		if(e == null) {
			return null;
		}
		
		if(e.getPassword().equals("")) {	
			ConfirmationEntity confirm =
					confirmationRepo.getConfirmationByUserId(e.getId());
			
			if(confirm == null) {
				userRepo.delete(e);
				return null;
			}
			
			return e;
		}
		
		BCrypt.Result result = 
				BCrypt.verifyer().verify(password.toCharArray(), e.getPassword());
		
		if(result.verified) {
			return e;
		}
		
		return null;
	}
}