package com.apps.noura.masajd.Favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;

/**
 * Created by Monirah on 3/22/2018.
 */

public class FavoriteMosque extends Fragment {

    private final static String TAG = "FavoriteMosque";

    private View view;

    private TextView checkLogin;

    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         view = inflater.inflate(R.layout.mosque_favorite,container,false);

        checkLogin = (TextView) view.findViewById(R.id.Favorite);

        //Check If User Login
        sharedConfig = new SharedPreferencesConfig(getContext());
        if(sharedConfig.readLoginStatus())
        {
            checkLogin.setVisibility(View.GONE);

        }
         return view;
    }
}
