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

public class OpenVehiclesAdapter extends RecyclerView.Adapter<OpenVehiclesAdapter.OpenVehiclesViewHolder> {

    private final ArrayList<Vehicle> vehicles;
    private final OpenVehiclesAdapter.RecyclerViewClickListener itemListener;

    public OpenVehiclesAdapter(ArrayList<Vehicle> vehicles, RecyclerViewClickListener itemListener) {
        this.vehicles = vehicles;
        this.itemListener = itemListener;
    }

    public class OpenVehiclesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView isEV, vehicleModel, userName;

        public OpenVehiclesViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);

            vehicleModel = itemView.findViewById(R.id.vehicleModel);
            userName = itemView.findViewById(R.id.userName);
            isEV = itemView.findViewById(R.id.isEV);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View v, int position);
    }

    @NonNull
    @Override
    public OpenVehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_vehicles_item_view, parent, false);
        return new OpenVehiclesViewHolder(v);    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OpenVehiclesViewHolder holder, int position) {
        holder.vehicleModel.setText(vehicles.get(position).getModel());
        holder.userName.setText(vehicles.get(position).getOwner());
        if(vehicles.get(position).getElectric()) {
            holder.isEV.setText("EV");
        }
        else holder.isEV.setText("");
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }
}
