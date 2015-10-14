package com.gobelins.mbrunelliere.userlogin.Profile;

/**
 * Created by mbrunelliere on 14/10/2015.
 */
public class User {

        private String name;
        private String email;
        private String password;

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

}
