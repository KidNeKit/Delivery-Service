package bsuir.diplom.mercury.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Offer;
import bsuir.diplom.mercury.entities.enums.OfferStatus;

public class OfferListAdapter extends ArrayAdapter<Offer> {
    private final Context context;
    private final int resource;
    private final OfferStatus offerStatusListValue;

    public OfferListAdapter(@NonNull Context context, int resource, @NonNull List<Offer> objects, OfferStatus offerStatusListValue) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.offerStatusListValue = offerStatusListValue;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Offer offer = getItem(position);
        String offerName = offer.getName();
        OfferStatus offerStatus = offer.getOfferStatus();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameTextView = convertView.findViewById(R.id.offer_name);
        TextView offerStatusTextView = convertView.findViewById(R.id.offer_status);

        nameTextView.setText(offerName);
        offerStatusTextView.setText(offerStatus.getStatus());

        switch (offerStatusListValue) {
            case COMPLETED:
                offerStatusTextView.setTextColor(Color.GREEN);
                break;
            case IN_PROGRESS:
                offerStatusTextView.setTextColor(Color.YELLOW);
                break;
            case IN_PROCESSING:
                offerStatusTextView.setTextColor(Color.GRAY);
                break;
            default:
                Log.e("Unexpected offer status: ", offerStatus.toString());
                break;
        }

        return convertView;
    }
}
