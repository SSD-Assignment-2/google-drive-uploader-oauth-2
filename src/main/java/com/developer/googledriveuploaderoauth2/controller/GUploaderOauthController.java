package com.developer.googledriveuploaderoauth2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.developer.googledriveuploaderoauth2.model.GUploaderFileUpload;
import com.developer.googledriveuploaderoauth2.service.GUploaderDriveService;
import com.developer.googledriveuploaderoauth2.service.GUploaderOauthService;

@Controller
public class GUploaderOauthController {

	@Autowired
	GUploaderOauthService gUploaderOauthService;

	@Autowired
	GUploaderDriveService gUploaderDriveService;

	/**
	 * Handles the root request. Checks if user is already authenticated via SSO.
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public String displayHomePage() throws Exception {
		if (gUploaderOauthService.isUserAuthenticated()) {
			System.out.println("User is an authenticated user and redirecting to home page");
			return "redirect:/home";
		} else {
			System.out.println("User is not an authenticated user !!!");
			return "redirect:/login";
		}
	}

	/**
	 * Directs to login
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String loadLogin() {
		return "main.html";
	}

	/**
	 * Directs to home
	 * 
	 * @return
	 */
	@GetMapping("/home")
	public String loadHomePage() {
		return "home.html";
	}

	/**
	 * Calls the Google OAuth service to authorize the app
	 * 
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/googlesignin")
	public void googleSignIn(HttpServletResponse response) throws Exception {
		System.out.println("Calling the SSO");
		response.sendRedirect(gUploaderOauthService.authenticateUserThroughGoogle());
	}

}

