package es.craftsmanship.toledo.katangapp.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

/**
 * @author Javier Gamarra
 */
public class StopsAdapter extends RecyclerView.Adapter<StopsAdapter.StopsViewHolder> {

    private List<BusStopResult> stops;
    private Context context;
    private RecyclerView lineas;
    private TextView address =null;
    private TextView distance =null;

    public StopsAdapter(List<BusStopResult> stops) {
        this.stops = stops;
    }

    @Override
    public StopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stop, parent, false);

        return new StopsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StopsViewHolder holder, int position) {
        BusStopResult stop = stops.get(position);

        holder.bind(stop);
    }

    @Override
    public int getItemCount() {
        return stops.size();
    }

    public class StopsViewHolder extends RecyclerView.ViewHolder {

        public StopsViewHolder(View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.stop_address);
            distance = (TextView) itemView.findViewById(R.id.stop_distance);
            lineas = (RecyclerView) itemView.findViewById(R.id.lineas);
            lineas.setLayoutManager(new LinearLayoutManager(context));

            Typeface tf = KatangaFont.getFont(context.getAssets(), KatangaFont.QUICKSAND_REGULAR);

            address.setTypeface(tf);
            distance.setTypeface(tf);
        }

        public void bind(BusStopResult stop) {
            List<RouteResult> results = stop.getResults();

            int size = results.size();

            lineas.setMinimumHeight(size * 100);
            lineas.setAdapter(new LinesAdapter(results));

            BusStop busStop = stop.getBusStop();

            address.setText(busStop.getAddress());

            // TODO we need to think about i18n for lang keys
            StringBuilder sb = new StringBuilder(3);

            sb.append("(");
            sb.append(String.format("%.2f", stop.getDistance()));
            sb.append(" metros)");

            distance.setText(sb.toString());
        }

    }

}