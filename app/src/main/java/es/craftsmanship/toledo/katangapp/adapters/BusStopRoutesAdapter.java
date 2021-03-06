/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.adapters;

import es.craftsmanship.toledo.katangapp.R;
import es.craftsmanship.toledo.katangapp.interactors.RouteIdInteractor;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.graphics.Color;

import android.support.v7.widget.RecyclerView;

import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

/**
 * author Cristóbal Hermida
 * author Manuel de la Peña
 */
public class BusStopRoutesAdapter
    extends RecyclerView.Adapter<BusStopRoutesAdapter.BusStopRoutesHolder>
    implements ItemClickListener {

    private List<RouteResult> routes;

    public BusStopRoutesAdapter(List<RouteResult> routes) {
        this.routes = routes;
    }

    @Override
    public BusStopRoutesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BusStopRoutesHolder(parent,this);
    }

    @Override
    public void onBindViewHolder(BusStopRoutesHolder holder, int position) {
        holder.bind(routes.get(position));
    }

    @Override
    public void onItemClick(View view, int position) {
        RouteResult route = routes.get(position);

        String routeId = route.getIdl();

        RouteIdInteractor routeIdInteractor = new RouteIdInteractor(routeId);

        new Thread(routeIdInteractor).start();
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    static class BusStopRoutesHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private final TextView lineText;
        private final ViewGroup parent;
        private final TextView timeText;
        public ItemClickListener listener;

        public BusStopRoutesHolder(ViewGroup parent, ItemClickListener listener) {
            super(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bus_stop_route_row, parent, false));

            this.listener = listener;

            this.parent = parent;

            lineText = (TextView) itemView.findViewById(R.id.line);
            timeText = (TextView) itemView.findViewById(R.id.time);

            lineText.setOnClickListener(this);
        }

        public void bind(RouteResult route) {
            lineText.setText(route.getIdl());

            formatTimeTextStyles(timeText, route.getTime());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }

        private void formatTimeTextStyles(TextView textView, long time) {
            int color = Color.BLACK;
            float textSize = KatangaFont.DEFAULT_FONT_SIZE;

            if (time <= 5) {
                color = Color.parseColor("#FF4B45");
                textSize *= 1.2;
            }
            else if (time < 10) {
                color = Color.parseColor("#FFB300");
                textSize *= 1.1;
            }

            textView.setTextColor(color);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

            if (time == 0) {
                CharSequence text = parent.getContext().getText(R.string.proximo);

                textView.setText(String.valueOf(text).toUpperCase());

                return;
            }

            NumberFormat numberFormat;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ES"));
            }
            else {
                numberFormat = NumberFormat.getNumberInstance();
            }

            String formattedTime = numberFormat.format(time);

            CharSequence minutesText = parent.getContext().getText(R.string.minutes);

            textView.setText(formattedTime + " " + minutesText);
        }

    }

}