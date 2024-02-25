package carpool.buddy.classes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import carpool.buddy.R;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.PassengersViewHolder> {

    ArrayList<User> passengers;

    public PassengersAdapter(ArrayList<User> passengers) {
        this.passengers = passengers;
    }

    public static class PassengersViewHolder extends RecyclerView.ViewHolder {
        protected TextView passengerName, passengerRating;

        public PassengersViewHolder(@NonNull View v) {
            super(v);

            passengerName = itemView.findViewById(R.id.passengerName);
            passengerRating = itemView.findViewById(R.id.passengerRating);
        }
    }
    @NonNull
    @Override
    public PassengersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passengers_item_view, parent, false);
        return new PassengersViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PassengersViewHolder holder, int position) {
        holder.passengerName.setText(passengers.get(position).getName());
        holder.passengerRating.setText("Rating: " + passengers.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }
}
