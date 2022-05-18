package bsuir.diplom.mercury.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.dto.AddressDTO;
import bsuir.diplom.mercury.entities.enums.OfferStatus;

public class DriverOfferListAdapter extends ArrayAdapter<Offer> {
    private final Context context;
    private final int resource;

    public DriverOfferListAdapter(@NonNull Context context, int resource, List<Offer> offerList) {
        super(context, resource, offerList);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Offer offer = getItem(position);
        AddressDTO fromAddress = offer.getAddressFrom();
        AddressDTO toAddress = offer.getAddressTo();

        String fullFromAddress = fromAddress.getStreet() + " " + fromAddress.getHouse() + ", " + fromAddress.getCity();
        String fullToAddress = toAddress.getStreet() + " " + toAddress.getHouse() + ", " + toAddress.getCity();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView itemQuantityTextView = convertView.findViewById(R.id.offer_short_quantity);
        TextView destinationFromTextView = convertView.findViewById(R.id.offer_short_from);
        TextView destinationToTextView = convertView.findViewById(R.id.offer_short_to);
        TextView expectedTimeTextView = convertView.findViewById(R.id.offer_short_time);
        ImageView infoButton = convertView.findViewById(R.id.offer_short_info_button);

        itemQuantityTextView.setText("Количество предметов: " + offer.getItemList().size());
        destinationFromTextView.setText(fullFromAddress);
        destinationToTextView.setText(fullToAddress);
        expectedTimeTextView.setText("15 минут");

        infoButton.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_offer_info_layout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.show();

            TextView startPointTextView = dialog.findViewById(R.id.start_point);
            TextView endPointTextView = dialog.findViewById(R.id.end_point);
            Button closeButton = dialog.findViewById(R.id.close_button);
            Button applyButton = dialog.findViewById(R.id.apply_button);

            startPointTextView.setText(fullFromAddress);
            endPointTextView.setText(fullToAddress);

            closeButton.setOnClickListener(view1 -> dialog.dismiss());
            applyButton.setOnClickListener(view1 -> {
                DatabaseReference offersReference = FirebaseDatabase.getInstance().getReference("Offers");
                offersReference.child(offer.getOfferId()).child("offerStatus").setValue(OfferStatus.IN_PROGRESS);
                dialog.dismiss();
            });
        });

        return convertView;
    }
}
