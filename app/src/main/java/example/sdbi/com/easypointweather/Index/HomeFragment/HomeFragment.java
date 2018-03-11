package example.sdbi.com.easypointweather.Index.HomeFragment;

import example.sdbi.com.easypointweather.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.home_fragment, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

	return view;
	}
}
