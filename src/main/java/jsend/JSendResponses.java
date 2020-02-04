package jsend;

import kong.unirest.json.JSONObject;

public class JSendResponses {
    public static String getBody(JSONObject sended, String status) {
        JSONObject js = new JSONObject();
        js.put("status", status);
        js.put("data", sended);
        return js.toString();
    }
}
