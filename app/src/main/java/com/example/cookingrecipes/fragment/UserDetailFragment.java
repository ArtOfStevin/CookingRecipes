package com.example.cookingrecipes.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingrecipes.R;
import com.example.cookingrecipes.activity.LoginActivity;
import com.example.cookingrecipes.logic.SessionManagementUtil;
import com.example.cookingrecipes.view_model.VMFoodBannerRepositoryBridge;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {

    private TextView tvUsernameCenter;
    private TextView tvUsername;
    private TextView tvEmail;

    private Button btnLogout;
    private ImageView ivPhotoProfile;
    private VMFoodBannerRepositoryBridge vmFoodBannerRepositoryBridge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.vmFoodBannerRepositoryBridge = new ViewModelProvider(requireActivity()).get(VMFoodBannerRepositoryBridge.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        this.tvUsernameCenter = fragmentView.findViewById(R.id.tvUserDetailUsernameCenter);
        this.tvUsername = fragmentView.findViewById(R.id.tvUserDetailUsername);
        this.tvEmail = fragmentView.findViewById(R.id.tvUserDetailEmail);
        this.btnLogout = fragmentView.findViewById(R.id.btnUserDetailLogOut);
        this.ivPhotoProfile = fragmentView.findViewById(R.id.ivUserDetailPhoto);

        setBtnLogout();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String loginUser = requireActivity().getIntent().getStringExtra("loginUser");
        this.tvUsernameCenter.setText(loginUser);
        this.tvUsername.setText(loginUser);

        inquiryByUsername(loginUser);
    }

    private void setBtnLogout(){
        this.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagementUtil.getInstance().clearStoredData(requireContext());
                vmFoodBannerRepositoryBridge.clearTable();
                vmFoodBannerRepositoryBridge.getLiveDataAllFoodBanner().observe(getViewLifecycleOwner(), foodBannerList -> {
                    for (int i = 0; i < foodBannerList.size(); i++) {
                        System.out.println("LogOut ROOM: Title: "+foodBannerList.get(i).getTitle());
                    }
                });
                toLoginScreen();
            }
        });
    }

    private void toLoginScreen(){
        Intent intent = new Intent(this.requireContext(), LoginActivity.class);
        intent.putExtra("loginUser", this.tvUsername.getText().toString());
        startActivity(intent);
    }

    private void inquiryByUsername(String username){
        this.ivPhotoProfile.setImageResource(R.drawable.ribbon);
    }
}