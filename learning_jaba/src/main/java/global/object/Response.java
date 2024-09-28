package global.object;

import java.io.Serializable;

public class Response  implements Serializable {
    private String massage;
    private Object object;


    public Response(String s, Object obj) {
        massage = s;
        object = obj;
    }

    public Response(String s) {
        massage = s;
    }

    public String getMessage(){
        return massage;
    }
    @Override
    public String toString(){
        return massage;
    }
}
