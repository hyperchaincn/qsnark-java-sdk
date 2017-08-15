package cn.qsnark.sdk.exception;

/**
 * Created by ZhangKejie on 2017/4/26.
 */
public class APIProException extends Exception{
    private int id; // a unique id
    private String classname; // the name of the class
    private String method; // the name of the method
    private String message; // a detailed message
    private APIProException previous =
            null; // the exception which was caught
    private String separator = "/n"; // line separator
    public APIProException(int id, APIProException previous) {
        this.id        = id;
        this.classname = "QsnarkAPI";
        this.method    = "new QsnarkAPI(APIProperties)";
        this.message   = "ApiProperties is null!";
        this.previous  = previous;
    }
    public String traceBack() {
        return traceBack("/n");
    }

    public String traceBack(String sep) {
        this.separator = sep;
        int level = 0;
        APIProException e = this;
        String text = line("Calling sequence (top to bottom)");
        while (e != null) {
            level++;
            text += line("--level " + level + "--------------------------------------");
            text += line("Class/Method: " + e.classname + "/" + e.method);
            text += line("Id          : " + e.id);
            text += line("Message     : " + e.message);
            e = e.previous;
        }
        return text;
    }

    private String line(String s) {
        return s + separator;
    }

}
