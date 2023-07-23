package org.example.Expense.Tracker.Service;

import org.example.Expense.Tracker.Model.AuthenticationToken;
import org.example.Expense.Tracker.Model.DTO.SignInInput;
import org.example.Expense.Tracker.Model.DTO.SignUpOutput;
import org.example.Expense.Tracker.Model.User;
import org.example.Expense.Tracker.Repository.IAuthenticationRepo;
import org.example.Expense.Tracker.Repository.IUserRepo;
import org.example.Expense.Tracker.Service.HashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    IAuthenticationRepo authenticationRepo;

    public SignUpOutput UserSignUp(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPass());

            //Save User

            user.setUserPass(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "USer registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String UserSignIn(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPass().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationRepo.save(authToken);

                return "This is your token keep it safe : " + authToken.getTokenValue();
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String UserSignOut(String email) {

        User user = userRepo.findFirstByUserEmail(email);
        authenticationRepo.delete(authenticationRepo.findFirstByUser(user));
        return "Patient Signed out successfully";
    }


    public User findById(Integer userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User getFirstByEmail(String email) {
        return userRepo.findFirstByUserEmail(email);
    }
}
