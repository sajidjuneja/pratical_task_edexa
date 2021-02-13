package com.example.practicaltaskedexa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    AllAdapter adapter;
    ArrayList<DataModel> dataModelArrayList;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getActivity() fragment
        View view=inflater.inflate(R.layout.fragment_all, container, false);
        recyclerView=view.findViewById(R.id.recyclerview);
        editText=view.findViewById(R.id.et_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dataModelArrayList=new ArrayList<>();
        btnLogin();
        return view;
    }

    private void btnLogin() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.npoint.io/81ada0361bbd877efb9e",
                response -> {
                    try {
                        dialog.dismiss();
                        Log.v("response",response);
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0;i<jsonArray.length();i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            DataModel dataModel=new DataModel();
                            dataModel.setFirst_name(obj.getString("first_name"));
                            dataModel.setLast_name(obj.getString("last_name"));
                            dataModel.setCity(obj.getString("city"));
                            dataModelArrayList.add(dataModel);
                        }
                        adapter=new AllAdapter(getActivity(),dataModelArrayList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                    }catch (JSONException e){
                        dialog.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }, error -> {
            dialog.dismiss();
            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}