package com.niro.resorder.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.niro.resorder.pojo.Item;
import com.niro.resorder.singleton.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VolleyGetService {
    private static ItemDelegate itemDelegate;
    private static CategoryDelegate categoryDelegate;

    private static Context context;
    private static List<String> categoryList;
    private static List<Item> itemInvList;


    public interface ItemDelegate{
        void syncItemDetails(List<Item> itemList);
    }

    public interface CategoryDelegate{
        void syncItemCategory(List<String> syncCategoryList);
    }


    public static void syncAllInventory(Context con,String url, final ItemDelegate delegate) {
        //itemList.clear();
        context = con;
        itemDelegate= delegate;

        itemInvList = new ArrayList<>();

        //Log.e("okfhfhdfhdfhdf","response.toString()");
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("okfhfhdfhdfhdf",response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("inventories");

                    if(itemInvList.size() > 0){
                        itemInvList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        Item item = new Item();

                        if (!object.isNull("category")) {
                            item.setItemCategory(object.getString("category"));
                        }

                        if (!object.isNull("item_desc")) {
                            item.setItemDesc(object.getString("item_desc"));
                        }

                        if (!object.isNull("item_number")) {
                            item.setItemNumber(object.getString("item_number"));

                        }

                        if (!object.isNull("subcategory")) {
                            item.setItemSubCategory(object.getString("subcategory"));
                        }


                        if (!object.isNull("selling_price")) {
                            item.setItemPrice(object.getDouble("selling_price"));
                        }

                        itemInvList.add(item);
                    }
                    //storeInvenItemsDB(itemInvList);
                    itemDelegate.syncItemDetails(itemInvList);


                } catch (JSONException e){
                    e.printStackTrace();
                    Log.e("JSONERR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                    //CustomDialog.imeOutExceptionDialog(context);
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }


    public static void syncAllItemCategory(Context con,String url, final CategoryDelegate delegate) {
        context = con;
        categoryList = new ArrayList<>();
        categoryDelegate = delegate;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.e("33333333333"," erankirantaa "  );
                try {

                    JSONArray jsonArray = response.getJSONArray("categories");

                    if (categoryList.size() > 0) {
                        categoryList.clear();

                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (!object.isNull("category")) {
                            categoryList.add(object.getString("category"));
                        }
                    }

                    categoryDelegate.syncItemCategory(categoryList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("errrrrrrrrrrr "," " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RES_ERR",error.toString());

                if(error.toString().trim().equalsIgnoreCase("com.android.volley.TimeoutError") ||
                        error.toString().trim().equalsIgnoreCase("com.android.volley.NoConnectionError: java.net.SocketException: Network is unreachable")) {
                    // Log.e("respomce_order_err","come error");
                   // CustomDialog.imeOutExceptionDialog(context);
                }

            }

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return setHeaderData();
            }
        };

        // now volley retry policy is 20s
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(20000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getmInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    //set header
    private static Map<String, String> setHeaderData(){
        Map<String, String> headers = new HashMap<>();

        /*String clientId = AppSettings.getClientId(context);
        String companyId = AppSettings.getCompanyId(context);
        //DbHandler dbHandler = DBSingleton.getInstance(context);
        //User user = dbHandler.getUserAuth(AppSettings.getUserSession(context));
        String userId = AppSettings.getUniqueId(context);
        String authId = AppSettings.getAuthId(context);
*/
        headers.put("client_id","21OFhI7afsDa5XkS5dHED4FuGGXBY97Pcpm8rDGv");
        headers.put("company_id","bf21636d3f29957e");
        headers.put("user_id","cff19b96-04e8-4592-957d-dd1321b950a7");
        headers.put("authorization","aUC54mGLlJM5lm5KEdpwjiQdg3xjp3feNwHe8iIw2nQzMKFu64HG1MszxMH1sJGxJp561m1XxLO");

       /* headers.put("client_id",clientId);
        headers.put("company_id",companyId);
        headers.put("user_id",userId);
        headers.put("authorization",authId);
*/
        return headers;
    }



}
