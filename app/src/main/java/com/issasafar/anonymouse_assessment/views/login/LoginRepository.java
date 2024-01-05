package com.issasafar.anonymouse_assessment.data;

import com.issasafar.anonymouse_assessment.data.models.LoggedUser;
import com.issasafar.anonymouse_assessment.data.models.Result;
import com.issasafar.anonymouse_assessment.views.login.LoginResponse;

public class LoginRepository {

    /**
     * Class that requests authentication and user information from the remote data source and
     * maintains an in-memory cache of login status and user credentials information.
     */


        private static volatile LoginRepository instance;

        private LoginDataSource dataSource;

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        private LoggedUser user = null;

        // private constructor : singleton access
        private LoginRepository(LoginDataSource dataSource) {
            this.dataSource = dataSource;
        }

        public static LoginRepository getInstance(LoginDataSource dataSource) {
            if (instance == null) {
                instance = new LoginRepository(dataSource);
            }
            return instance;
        }

        public boolean isLoggedIn() {
            return user != null;
        }

        public void logout() {
            user = null;
            dataSource.logout();
        }

        private void setLoggedInUser(LoggedUser user) {
            this.user = user;
            // If user credentials will be cached in local storage, it is recommended it be encrypted
            // @see https://developer.android.com/training/articles/keystore
        }

        public void login(String username, String password, RepositoryCallBack<LoginResponse> repositoryCallback) {
            //TODO handle login here
           dataSource.login(username, password, repositoryCallback);

        }


}
