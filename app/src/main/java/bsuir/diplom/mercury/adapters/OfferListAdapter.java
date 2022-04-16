package bsuir.diplom.mercury.adapters;

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

public class OfferListAdapter extends ArrayAdapter<Offer> {

    private final Context context;
    private final int resource;

    public OfferListAdapter(@NonNull Context context, int resource, @NonNull List<Offer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        Double length = getItem(position).getLength();
        Double width = getItem(position).getWidth();
        Double height = getItem(position).getHeight();
        Double weight = getItem(position).getWeight();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameTextView = convertView.findViewById(R.id.item_name);
        TextView itemSizeInfo = convertView.findViewById(R.id.item_size_info);

        String sizeInfo = length + " x " + width + " x " + height;

        nameTextView.setText(name);
        itemSizeInfo.setText(sizeInfo);

        return convertView;
    }
}
