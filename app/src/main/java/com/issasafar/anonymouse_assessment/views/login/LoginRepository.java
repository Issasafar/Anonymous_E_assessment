package com.issasafar.anonymouse_assessment.views.login;

import android.util.Log;

import com.issasafar.anonymouse_assessment.data.models.User;
import com.issasafar.anonymouse_assessment.data.models.login.LoggedInUser;
import com.issasafar.anonymouse_assessment.data.models.login.LoginResponse;

public class LoginRepository {

    /**
     * Class that requests authentication and user information from the remote data source and
     * maintains an in-memory cache of login status and user credentials information.
     */


        private static volatile LoginRepository instance;

        private LoginDataSource dataSource;



        private LoginRepository(LoginDataSource dataSource) {
            this.dataSource = dataSource;
        }

        public static LoginRepository getInstance(LoginDataSource dataSource) {
            if (instance == null) {
                instance = new LoginRepository(dataSource);
            }
            return instance;
        }




        public void login(String username, String password, RepositoryCallBack<LoginResponse> repositoryCallback) {

           dataSource.login(username, password, repositoryCallback);

        }
        public void register(User user, RepositoryCallBack<LoginResponse> repositoryCallBack){

            dataSource.register(user,repositoryCallBack);
        }

}
