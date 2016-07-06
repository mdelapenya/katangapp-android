package es.craftsmanship.toledo.katangapp.activities;

import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.utils.KatangaFont;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

/**
 * author Cristóbal Hermida
 * author Manuel de la Peña
 */
public class LinesAdapter extends RecyclerView.Adapter<LinesAdapter.LinesHolder> {

    private List<RouteResult> lines;

    public LinesAdapter(List<RouteResult> lines) {
        this.lines = lines;
    }

    @Override
    public LinesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinesHolder(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(LinesHolder holder, int position) {
        holder.bind(lines.get(position));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    static class LinesHolder extends RecyclerView.ViewHolder {

        private final TextView lineText;
        private final TextView timeText;
        private final TextView routeMinutesText;

        public LinesHolder(View itemView) {
            super(itemView);

            lineText = (TextView) itemView.findViewById(R.id.line);
            timeText = (TextView) itemView.findViewById(R.id.time);
            routeMinutesText = (TextView) itemView.findViewById(R.id.routeMinutesLabel);
        }

        public void bind(RouteResult route) {
            lineText.setText(route.getIdl());

            formatTimeTextStyles(timeText, route.getTime());
            formatTimeTextStyles(routeMinutesText, route.getTime());

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ES"));

            String formattedTime = numberFormat.format(route.getTime());

            timeText.setText(formattedTime);
        }

        private void formatTimeTextStyles(TextView textView, long time) {
            int color = Color.BLACK;
            float textSize = KatangaFont.DEFAULT_FONT_SIZE;

            if (time <= 5) {
                color = Color.parseColor("#FF4B45");
                textSize *= 1.5;
            }
            else if (time < 10) {
                color = Color.parseColor("#FFB300");
                textSize *= 1.25;
            }

            textView.setTextColor(color);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

    }

}
