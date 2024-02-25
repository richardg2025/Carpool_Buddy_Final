package carpool.buddy.classes;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import carpool.buddy.R;

public class RatingPassengersViewHolder extends RecyclerView.ViewHolder {
    TextView passengerName, passengerRating;
    RatingBar passengerRatingBar;
    public RatingPassengersViewHolder(@NonNull View itemView) {
        super(itemView);
        passengerName = itemView.findViewById(R.id.passengerName);
        passengerRating = itemView.findViewById(R.id.passengerRating);
        passengerRatingBar = itemView.findViewById(R.id.passengerRatingBar);
    }
}
