package carpool.buddy.classes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import carpool.buddy.R;

public class PassengersViewHolder extends RecyclerView.ViewHolder {
    TextView passengerName, passengerRating;
    public PassengersViewHolder(@NonNull View itemView) {
        super(itemView);
        passengerName = itemView.findViewById(R.id.passengerName);
        passengerRating = itemView.findViewById(R.id.passengerRating);
    }
}