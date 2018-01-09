package com.apps.noura.masajd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;


///Fragment
public class MosqueImage extends Fragment {

    private  ImageView img ;
    private String mosqCode;
    private List<MosquesLatLng> mosquesLatLngs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        view = inflater.inflate(R.layout.fragment_mosque_image, container, false);

        img = (ImageView) view.findViewById(R.id.img);

        /*Intent intent = getActivity().getIntent();
        mosqCode= intent.getStringExtra("MOSQUE_CODE");
        System.out.print(mosqCode + " THE CPDE KKKKKKKKKK:::: ::: PPPPPPP");
        Toast.makeText(getActivity(),"View Image Fragment",Toast.LENGTH_SHORT).show();
        */
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    org.jsoup.nodes.Document doc = Jsoup.connect("http://gis.moia.gov.sa/Mosques/Content/images/mosques/" + mosqCode + "/").get();

                    String title = doc.title();
                    Elements links = doc.getElementsByTag("a");


                    builder.append(title).append("\n");


                    for (Element link : links) {
                        builder.append("\n").append("Link: ")
                                .append(link.attr("href"))
                                .append("\n")
                                .append("Text: ")
                                .append("\n")
                                .append(link.text());

                        System.out.println(links + " :):):):):):):)");
                        System.out.println(link.attr("href") + " :):):):):):):)  ++++++");


                    }

                } catch (IOException e) {
                    builder.append("Error :( :(")
                            .append(e.getMessage())
                            .append("\n");
                    //e.printStackTrace();
                }
                System.out.println(builder +"\n BILDER 0000000000000000000000000000000000000000000000");
            }
        }).start();


        System.out.println("Test After Thred");
        Picasso.with(getContext())
                .load("http://gis.moia.gov.sa/Mosques/Content/images/mosques/1232/IMG_2066.JPG")
                .into(img);
*/
        return view;
    }


}
