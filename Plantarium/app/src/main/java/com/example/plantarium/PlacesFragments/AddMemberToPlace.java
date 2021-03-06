package com.example.plantarium.PlacesFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.plantarium.Models.DBModels.PlaceMemberModel;
import com.example.plantarium.Models.Place;
import com.example.plantarium.Models.PlaceMember;
import com.example.plantarium.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddMemberToPlace extends Fragment {
    View view;
    TextView placeName;
    AppCompatEditText memberMail;
    Place place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_member_to_place, container, false);
        place = AddMemberToPlaceArgs.fromBundle(getArguments()).getPlace();
        CircleImageView placeImage = view.findViewById(R.id.place_image);
        placeName = view.findViewById(R.id.place_name);
        memberMail = view.findViewById(R.id.member_email);
        Button inviteMemberBtn = view.findViewById(R.id.sendInviteBtn);

        placeName.setText(place.getName());
        placeImage.setVisibility(View.INVISIBLE);
        if (place.getImageUrl() != null){
            Picasso.get().load(place.getImageUrl()).into(placeImage);
            placeImage.setVisibility(View.VISIBLE);
        }

        inviteMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlaceMember();
            }
        });

        return view;
    }

    public void addPlaceMember(){
        SendEmailInvitation();
        PlaceMember newPlaceMember = new PlaceMember(memberMail.getText().toString(), place.getId());
        PlaceMemberModel.instance.updatePlaceMember(newPlaceMember, new PlaceMemberModel.UpdatePlaceMemberListener() {
            @Override
            public void onComplete() {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    public void SendEmailInvitation(){
        String to= memberMail.getText().toString();
        String subject= "Plantarium הצטרף אלינו";
        String message= "אתה מוזמן להצטרף להשקות איתי יחד את העציצים שלנו ב" + place.getName();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        //need this to prompts email client only
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
}