package carpool.buddy.classes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import carpool.buddy.R;

public class OpenVehiclesViewHolder extends RecyclerView.ViewHolder {
    TextView vehicleModel, userName, isEV;
    public OpenVehiclesViewHolder(@NonNull View itemView) {
        super(itemView);
        vehicleModel = itemView.findViewById(R.id.vehicleModel);
        userName = itemView.findViewById(R.id.userName);
        isEV = itemView.findViewById(R.id.isEV);
    }
}
