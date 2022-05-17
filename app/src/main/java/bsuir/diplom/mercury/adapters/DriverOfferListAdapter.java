package bsuir.diplom.mercury.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class DriverOfferListAdapter extends ArrayAdapter<Offer> {
    private final Context context;
    private final int resource;

    public DriverOfferListAdapter(@NonNull Context context, int resource, List<Offer> offerList) {
        super(context, resource, offerList);
        this.context = context;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Offer offer = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView itemQuantityTextView = convertView.findViewById(R.id.offer_short_quantity);
        TextView destinationFromTextView = convertView.findViewById(R.id.offer_short_from);
        TextView destinationToTextView = convertView.findViewById(R.id.offer_short_to);
        TextView expectedTimeTextView = convertView.findViewById(R.id.offer_short_time);

        //int itemQuantity = offer.getItemList().size();
        //itemQuantityTextView.setText("Количество перевозимых предметов: " + itemQuantity);
        itemQuantityTextView.setText("Количество предметов: 4");
        //destinationFromTextView.setText(offer.getAddressTo());
        //destinationToTextView.setText(offer.getAddressFrom());
        expectedTimeTextView.setText(" 15 минут");

        return convertView;
    }
}
