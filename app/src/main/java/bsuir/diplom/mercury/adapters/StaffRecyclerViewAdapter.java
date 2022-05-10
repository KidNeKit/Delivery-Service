package bsuir.diplom.mercury.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bsuir.diplom.mercury.R;
import bsuir.diplom.mercury.entities.Staff;

public class StaffRecyclerViewAdapter extends RecyclerView.Adapter<StaffRecyclerViewAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<Staff> staffList;

    public StaffRecyclerViewAdapter(Context context, List<Staff> staffList) {
        this.staffList = staffList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.single_staff_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.photoImageView.setImageResource(staff.getPhotoImageResource());
        holder.surnameTextView.setText(staff.getSurname());
        holder.nameTextView.setText(staff.getName());
        holder.professionTextView.setText(staff.getProfession().getText());
        holder.ratingTextView.setText(String.valueOf(staff.getRating()));
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView surnameTextView;
        private final TextView nameTextView;
        private final TextView professionTextView;
        private final ImageView photoImageView;
        private final TextView ratingTextView;

        public ViewHolder(@NonNull View view) {
            super(view);
            photoImageView = view.findViewById(R.id.staff_photo);
            surnameTextView = view.findViewById(R.id.staff_surname);
            nameTextView = view.findViewById(R.id.staff_name);
            professionTextView = view.findViewById(R.id.staff_profession);
            ratingTextView = view.findViewById(R.id.staff_rating);
        }
    }
}
