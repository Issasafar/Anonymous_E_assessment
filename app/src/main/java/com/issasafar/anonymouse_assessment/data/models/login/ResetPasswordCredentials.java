package com.issasafar.anonymouse_assessment.data.models.login;

public class ResetPasswordCredentials {
    private ResetPasswordCredentials() {

    }

    public static final class EmailNamePair extends ResetPasswordCredentials {
        private String email;
        private String userName;

        public EmailNamePair(String email, String userName) {
            this.email = email;
            this.userName = userName;
        }

        public String getEmail() {
            return email;
        }

        public String getUserName() {
            return userName;
        }

        @Override
        public String toString() {
            return "EmailNamePair{" +
                    "email='" + email + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }
    public static final class ResetPasswordDataHolder extends ResetPasswordCredentials{
        private String email;
      private String newPassword;
      private AccountType accountType;

        public ResetPasswordDataHolder(String email, String newPassword, AccountType accountType) {
            this.email = email;
            this.newPassword = newPassword;
            this.accountType = accountType;
        }

        @Override
        public String toString() {
            return "ResetPasswordDataHolder{" +
                    "email='" + email + '\'' +
                    ", newPassword='" + newPassword + '\'' +
                    ", accountType=" + accountType +
                    '}';
        }

        //simple enum for holding account type
        public enum AccountType {
                STUDENT("student"),
            TEACHER("teacher");
            private final String value;

            AccountType(String value) {
                this.value = value;
            }

            public String getValue() {
                return this.value;
            }
        }

    }
}
