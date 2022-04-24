package com.example.idocs.ui.views;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.idocs.R;

public class StationaryShopsFragment extends Fragment {
    private ImageButton imgBtn_1;
    private ImageButton imgBtn_2;
    private ImageButton imgBtn_3;
    private ImageButton imgBtn_4;
    private ImageButton imgBtn_5;
    private PointF[] coordinates;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stationary_shops, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coordinates = new PointF[5];
        initializeCoordinates();
        imgBtn_1 = view.findViewById(R.id.bt_show_on_map_1);
        imgBtn_2 = view.findViewById(R.id.bt_show_on_map_2);
        imgBtn_3 = view.findViewById(R.id.bt_show_on_map_3);
        imgBtn_4 = view.findViewById(R.id.bt_show_on_map_4);
        imgBtn_5 = view.findViewById(R.id.bt_show_on_map_5);

        imgBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(StationaryShopsFragmentDirections.actionStationaryShopsFragmentToMapsFragment(coordinates[0].x, coordinates[0].y));
            }
        });

        imgBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(StationaryShopsFragmentDirections.actionStationaryShopsFragmentToMapsFragment(coordinates[1].x, coordinates[1].y));
            }
        });

        imgBtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(StationaryShopsFragmentDirections.actionStationaryShopsFragmentToMapsFragment(coordinates[2].x, coordinates[2].y));
            }
        });

        imgBtn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(StationaryShopsFragmentDirections.actionStationaryShopsFragmentToMapsFragment(coordinates[3].x, coordinates[3].y));
            }
        });

        imgBtn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(StationaryShopsFragmentDirections.actionStationaryShopsFragmentToMapsFragment(coordinates[4].x, coordinates[4].y));
            }
        });
    }

    private void initializeCoordinates() {
        coordinates[0] = new PointF((float) 24.859274, (float) 67.017220);
        coordinates[1] = new PointF((float) 24.846668, (float) 67.024498);
        coordinates[2] = new PointF((float) 24.847754, (float) 67.002357);
        coordinates[3] = new PointF((float) 24.774839, (float) 67.058151);
        coordinates[4] = new PointF((float) 24.829815, (float) 67.035011);
    }
}