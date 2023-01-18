package com.UserloginSecurity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UserloginSecurity.dao.UserRepository;
import com.UserloginSecurity.dto.Logindto;
import com.UserloginSecurity.dto.Userdto;
import com.UserloginSecurity.entity.User;

@Service
public class UserService {
	
	
	@Autowired
   UserRepository userRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void signUp(Userdto userdto) {
		
//		String decodePasswod=passwordEncoder.encode(userdto.getPassword());
//		userdto.setPassword(decodePasswod);
		User user1=userRepository.findOneByEmailId(userdto.getEmailId());

		if(user1==null)
		{
		userRepository.save((UserDtoToUser(userdto)));
		}
		else {
			throw new UsernameNotFoundException("User not found "+user1);
		}
	}

	
	public String login(Logindto logindto) {
		
		
		User user = this.userRepository.findOneByIgnoreCaseEmailIdAndPassword(logindto.getEmailId(), logindto.getPassword());
		
		if(user==null)
		{
			return "User login faild";
		}
		else
		{
			return "User login successful";
		}
		
	}
	
	public List<Userdto> featchAllData() {
		List<Userdto> userdtolist = new ArrayList<Userdto>();
		userRepository.findAll().forEach(li->userdtolist.add(UserToUserDto(li)));
		return userdtolist;
	}


	public User UserDtoToUser(Userdto userdto)
	{
		User user=new User();
		
		user.setUserId(userdto.getUserId());
		user.setUserName(userdto.getUserName());
		user.setEmailId(userdto.getEmailId());
		//user.setPassword(userdto.getPassword());
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		user.setUserPhoneNo(userdto.getUserPhoneNo());
		user.setRoles(userdto.getRoles());
		return user;
	}

	
	 public Userdto UserToUserDto(User user)
	 {
		 Userdto userDto= new Userdto();

		  userDto.setUserId(user.getUserId());;

		  userDto.setUserName(user.getUserName());

		  userDto.setEmailId(user.getEmailId());

		  userDto.setPassword(user.getPassword());

		  userDto.setUserPhoneNo(user.getUserPhoneNo());
		  
		  userDto.setRoles(user.getRoles());
	       return userDto;
	    }


}
