package com.freeman.codeoasistask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactList extends AppCompatActivity {
    private String TAG = ContactList.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ListView listView;
    private static String url = "http://api.androidhive.info/contacts/";

    DatabaseHandler databaseHandler;
    SwipeDetector swipeDetector = new SwipeDetector();


    ArrayList<HashMap<String, String>> contactList;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        contactList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.list_of_items);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ContactList.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();

            databaseHandler = new DatabaseHandler(ContactList.this);

            String jsonString = httpHandler.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonString);

            if (jsonString != null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);

                    JSONArray contacts = jsonObject.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++){
                        JSONObject cont = contacts.getJSONObject(i);

                        String id = cont.getString("id");
                        String name = cont.getString("name");
                        String email = cont.getString("email");
                        String address = cont.getString("address");
                        String gender = cont.getString("gender");

                        JSONObject phone = cont.getJSONObject("phone");

                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        databaseHandler.addContact(new Contact(id, name, email, address,
                                    gender, mobile, home, office));

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "JSON parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "JSON parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get JSON from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            List<Contact> contacts = databaseHandler.getAllContacts();
            for (Contact cn : contacts) {
                HashMap<String, String> contact = new HashMap<>();
                contact.put("name", cn.getName());
                contact.put("email", cn.getEmail());
                contact.put("mobile", cn.getmPhone());
                contactList.add(contact);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            final ListAdapter listAdapter = new SimpleAdapter(ContactList.this, contactList,
                    R.layout.list_item, new String[]{"name", "mobile"},
                    new int[]{R.id.name, R.id.mobile});
            listView.setAdapter(listAdapter);

            listView.setOnTouchListener(swipeDetector);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (swipeDetector.swipeDetected()){
                        if (swipeDetector.getAction() == SwipeDetector.Action.CL){
                            String phoneNumber = ((TextView) view.findViewById(R.id.mobile)).getText().toString();

                            Uri uri = Uri.parse("tel:" + phoneNumber);
                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                            Intent chooser = Intent.createChooser(intent, "Choice ...");
                            startActivity(chooser);
                            Log.e(LOG_TAG, "myLogs: no move");

                        } else if (swipeDetector.getAction() == SwipeDetector.Action.RL){

                        }
                    }

                }
            });

        }
    }
}
