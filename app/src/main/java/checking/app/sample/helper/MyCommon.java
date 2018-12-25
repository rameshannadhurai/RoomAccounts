package checking.app.sample.helper;

/**
 * Created by User on 03-08-2016.
 */
public class MyCommon {


    private static MyCommon ourInstance = new MyCommon();
    public static String DEVICETOCKEN = "";

    public static String USERDETAILS = "Users";


    private MyCommon() {
    }

    public static MyCommon getInstance() {
        return ourInstance;
    }


}
