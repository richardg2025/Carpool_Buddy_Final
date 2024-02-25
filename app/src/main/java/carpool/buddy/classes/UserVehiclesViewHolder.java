package carpool.buddy.classes;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import carpool.buddy.R;

public class UserVehiclesViewHolder extends RecyclerView.ViewHolder {
    protected TextView isEV, vehicleModel;
    public UserVehiclesViewHolder(@NonNull View v) {
        super(v);
        isEV = itemView.findViewById(R.id.isEV);
        vehicleModel = itemView.findViewById(R.id.vehicleModel);
    }
}
