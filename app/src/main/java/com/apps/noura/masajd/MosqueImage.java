package com.apps.noura.masajd;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



///Fragment
public class MosqueImage extends Fragment {

    private String mosqCode;
    private Context context = getContext();

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MosqueImagAdapter adapter;


    ArrayList<String> ImageUrls = new ArrayList<String>();
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mosque_image, container, false);

        intent= getActivity().getIntent();
        mosqCode = intent.getStringExtra("MOSQUE_CODE");

        System.out.print(mosqCode + " Mosque Code");


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewImag);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        loodImage ParseH = new loodImage();

        ParseH.mos(mosqCode );
        ParseH.execute();

       // Toast.makeText(getActivity(),"View Image Fragment",Toast.LENGTH_SHORT).show();

        return view;
    }


    //--------------------------------------------------------
    class loodImage extends AsyncTask<String,Void,String> {

        org.jsoup.nodes.Document doc = null;
        String CodNumber;
        Elements links;
        String myUrl;
        //ImageView imageView;
        // TextView textView;
        //, ImageView img, TextView textView
        //Constructor
        private void  mos( String MosquCode ){
            this.CodNumber = MosquCode;
            System.out.println(CodNumber + " CodNumber Is");
            //this.imageView = img;
            //   this.textView = textView;

        }

        @Override
        protected String doInBackground(String... strings) {

            final StringBuilder builder = new StringBuilder();
            System.out.println(" :)0 ");

            try {

                System.out.println(" :)1 ");
                doc = Jsoup.connect("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+CodNumber)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .get();
                Log.d("EXAMPLE ", doc.toString());
                String title = doc.title();
                links = doc.getElementsByTag("a");
                builder.append(title).append(" title was\n");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Execute";
        }

        @Override
        protected void onPostExecute(String s) {

            if(doc != null  ){


                for (Element E : links) {
                    myUrl = E.attr("href");
                    System.out.println(" Link is : " + myUrl);

                    if(E.attr("href").endsWith(".JPG")){
                        ImageUrls.add(E.attr("href"));
                        Log.d("IMG :  ", "Download Image"+ E.attr("href").endsWith(".JPG"));


                    }//end if

                }//end for

                for (String imag: ImageUrls  ){
                    System.out.println(" size is  ()Mosque Image : " + ImageUrls.size());

                    System.out.println("Url Is" + imag);

                      /*
                      Picasso.with(context)
                                .load("http://gis.moia.gov.sa/"+imag)
                                .placeholder(R.drawable.mosqueicon)
                                .into(imageView);
                                */
                }

            }//end if
            adapter = new MosqueImagAdapter( getContext(),ImageUrls);
            recyclerView.setAdapter(adapter);
        }
    }//end Class


    //--------------------------------------------------------

}






