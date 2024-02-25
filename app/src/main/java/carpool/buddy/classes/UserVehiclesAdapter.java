package carpool.buddy.classes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import carpool.buddy.R;

public class UserVehiclesAdapter extends RecyclerView.Adapter<UserVehiclesAdapter.UserVehiclesViewHolder> {

    private final ArrayList<Vehicle> vehicles;
    private final RecyclerViewClickListener itemListener;

    public UserVehiclesAdapter(ArrayList<Vehicle> vehicles, RecyclerViewClickListener itemListener) {
        this.vehicles = vehicles;
        this.itemListener = itemListener;
    }

    public class UserVehiclesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView isEV, vehicleModel;

        public UserVehiclesViewHolder(@NonNull View v) {
            super(v);
            v.setOnClickListener(this);

            isEV = itemView.findViewById(R.id.isEV);
            vehicleModel = itemView.findViewById(R.id.vehicleModel);
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
    public UserVehiclesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicles_item_view, parent, false);
        return new UserVehiclesViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserVehiclesViewHolder holder, int position) {
        holder.vehicleModel.setText(vehicles.get(position).getModel());
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
