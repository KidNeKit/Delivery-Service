package bsuir.diplom.mercury.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import bsuir.diplom.mercury.R;

public class AddressSuggestAdapter extends ArrayAdapter<Address> {
    private final Context context;
    private final int resource;
    private final List<Address> addressList;

    public AddressSuggestAdapter(@NonNull Context context, int resource, List<Address> addresses) {
        super(context, resource, addresses);
        this.context = context;
        this.resource = resource;
        this.addressList = addresses;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> newAddressList = geocoder.getFromLocationName(constraint.toString() + " minsk", 3);
                    addressList.clear();
                    addressList.addAll(newAddressList);

                    filterResults.values = addressList;
                    filterResults.count = addressList.size();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Address address = (Address) resultValue;
            return address.getThoroughfare() + " " + address.getFeatureName() + ", " + address.getLocality();
        }
    };

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        Address address = getItem(position);

        TextView streetTextView = convertView.findViewById(R.id.street);
        TextView cityTextView = convertView.findViewById(R.id.city);

        streetTextView.setText(address.getThoroughfare() + " " + address.getFeatureName());
        cityTextView.setText(address.getLocality() + ", " + address.getCountryName());

        return convertView;
    }
}
