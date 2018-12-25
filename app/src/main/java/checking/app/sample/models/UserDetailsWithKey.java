package checking.app.sample.models;

public class UserDetailsWithKey {

    private String Key;
    private UserDetails userDetails;
    private boolean ischeck;

    public UserDetailsWithKey() {
    }

    public UserDetailsWithKey(String key, UserDetails userDetails,boolean ischeck) {
        this.Key = key;
        this.userDetails = userDetails;
        this.ischeck = ischeck;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
