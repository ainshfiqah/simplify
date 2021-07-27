package com.example.simplify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ordersAdapter extends FirebaseRecyclerAdapter<orders, ordersAdapter.ordersAdapterViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ordersAdapter(@NonNull FirebaseRecyclerOptions<orders> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ordersAdapterViewHolder holder, int position, @NonNull orders model) {
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
    }

    @NonNull
    @Override
    public ordersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_ui, parent, false);
        return new ordersAdapterViewHolder(view);

    }

    class ordersAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, status, address;

        public ordersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.custName);
            address = itemView.findViewById(R.id.address);
            status = itemView.findViewById(R.id.status);
        }
    }
}