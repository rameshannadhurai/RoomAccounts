package checking.app.sample.models;

public class UserDetails {

    private String Username;
    private String Useremail;
    private String Usertotel;
    private String UserExtras;
    private String UserBal;
    private String UserCurrent;
    private String UserNet;
    private String UserWater;
    private String UserShop;
    private String adminPermission;

    public UserDetails() {
    }

    public UserDetails(String username, String useremail, String usertotel, String userExtras, String userBal, String userCurrent, String userNet, String userWater, String userShop, String adminPermission) {
        this.Username = username;
        this.Useremail = useremail;
        this.Usertotel = usertotel;
        this.UserExtras = userExtras;
        this.UserBal = userBal;
        this.UserCurrent = userCurrent;
        this.UserNet = userNet;
        this.UserWater = userWater;
        this.UserShop = userShop;
        this.adminPermission = adminPermission;
    }

    public String getUserShop() {
        return UserShop;
    }

    public void setUserShop(String userShop) {
        this.UserShop = userShop;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getUseremail() {
        return Useremail;
    }

    public void setUseremail(String useremail) {
        this.Useremail = useremail;
    }

    public String getUsertotel() {
        return Usertotel;
    }

    public void setUsertotel(String usertotel) {
        this.Usertotel = usertotel;
    }

    public String getUserExtras() {
        return UserExtras;
    }

    public void setUserExtras(String userExtras) {
        this.UserExtras = userExtras;
    }

    public String getUserBal() {
        return UserBal;
    }

    public void setUserBal(String userBal) {
        this.UserBal = userBal;
    }

    public String getUserCurrent() {
        return UserCurrent;
    }

    public void setUserCurrent(String userCurrent) {
        this.UserCurrent = userCurrent;
    }

    public String getUserNet() {
        return UserNet;
    }

    public void setUserNet(String userNet) {
        this.UserNet = userNet;
    }

    public String getUserWater() {
        return UserWater;
    }

    public void setUserWater(String userWater) {
        this.UserWater = userWater;
    }

    public String getAdminPermission() {
        return adminPermission;
    }

    public void setAdminPermission(String adminPermission) {
        this.adminPermission = adminPermission;
    }
}
