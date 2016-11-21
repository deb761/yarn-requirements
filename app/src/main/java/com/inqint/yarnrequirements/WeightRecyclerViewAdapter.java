package com.inqint.yarnrequirements;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inqint.yarnrequirements.weights.KnittingNeedle;
import com.inqint.yarnrequirements.weights.WeightContent.WeightItem;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link WeightItem} and makes a call to the
 * specified {@link WeightFragment.OnListFragmentInteractionListener}.
 */
public class WeightRecyclerViewAdapter extends RecyclerView.Adapter<WeightRecyclerViewAdapter.ViewHolder> {

    private final List<WeightItem> mValues;
    private final WeightFragment.OnListFragmentInteractionListener mListener;

    public WeightRecyclerViewAdapter(List<WeightItem> items, WeightFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_weight, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        String sizes = "";

        Locale country = Locale.getDefault();
        KnittingNeedle[] needles = holder.mItem.needles;
        // If the user is in the US, show US needle sizes first
        if (country.equals(Locale.US)) {
            if (needles.length == 2) {
                sizes = String.format("%1$s-%2$s US, ", needles[0].getus(), needles[1].getus());
            } else {
                sizes = String.format("%s+ US, ", needles[0].getus());
            }
        }
        // Always show international needle sizes
        if (holder.mItem.needles.length == 2) {
            sizes += String.format("%1$.2f-%2$.2f mm", needles[0].getmm(), needles[1].getmm());
        } else {
            sizes += String.format("%.2f mm", needles[0].getmm());
        }
        Resources res = holder.mView.getResources();

        holder.mNeedleView.setText(res.getString(R.string.needle_size) + ": " + sizes);

        // Show the gauge
        double[] gauges = holder.mItem.gauge;
        String gauge = "";
        if (gauges.length == 2) {
            gauge = String.format("%1$.0f-%2$.0f ", gauges[0], gauges[1]);
        } else {
            gauge = String.format("%.0f ", gauges[0]);
        }
        holder.mGaugeView.setText(res.getString(R.string.gauge) + ": " + gauge + res.getString(R.string.gauge_length));

        // format windings string
        String wpi = "";
        double[] windings = holder.mItem.wpi;
        if (windings.length == 2) {
            wpi = String.format("%1$.0f-%2$.0f", windings[0], windings[1]);
        } else {
            wpi = String.format("%.0f", windings[0]);
        }

        double length = holder.mItem.length;
        double ball = holder.mItem.weight;

        holder.mWindingsView.setText(String.format("%1$s %2$s", wpi, res.getString(R.string.wpi)));
        holder.mDensityView.setText(String.format("%1$.0f%2$s %3$s %4$s%5$s %6$s", length,
                res.getString(R.string.meter_abbr), res.getString(R.string.per),
                holder.mItem.weight, res.getString(R.string.gram_abbr),
                res.getString(R.string.yarn_ball)));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mNeedleView;
        public final TextView mGaugeView;
        public final TextView mWindingsView;
        public final TextView mDensityView;
        public WeightItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mNeedleView = (TextView) view.findViewById(R.id.needles);
            mGaugeView = (TextView) view.findViewById(R.id.gauge);
            mWindingsView = (TextView) view.findViewById(R.id.wpi);
            mDensityView = (TextView) view.findViewById(R.id.density);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNeedleView.getText() + "'";
        }
    }
}
