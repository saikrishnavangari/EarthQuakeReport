package adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.features;
import sample.com.earthquake.MainActivity;
import sample.com.earthquake.R;


/**
 * Created by krrish on 28/09/2016.
 */
public class EarthquakeAdapter extends ArrayAdapter<features> {
    private static final String LOCATION_SEPARATOR = " of ";
    String primaryLocation;
    String locationOffset;

    public EarthquakeAdapter(MainActivity context, ArrayList<features> earthquakes) {
        super(context,0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_view, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        features feature= getItem(position);
        features.properties earthquake=feature.getProperties();
        String originalLocation = earthquake.getLocation();
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView mag = (TextView) listItemView.findViewById(R.id.mag);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        GradientDrawable magnitudeCircle= (GradientDrawable) mag.getBackground();
        magnitudeCircle.setColor(getMagnitudeColor(earthquake.getMag()));
        mag.setText(String.valueOf(new DecimalFormat("0.0").format(earthquake.getMag())));

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView primary = (TextView) listItemView.findViewById(R.id.primary);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        primary.setText(locationOffset);
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView secondary = (TextView) listItemView.findViewById(R.id.secondary);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        secondary.setText(primaryLocation);


        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        long time=earthquake.getDate();
        try {
            date.setText(formateDate(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;

    }

    private String formateDate(long time) throws ParseException {

        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());

    }

    @Override
    public features getItem(int position) {

        return super.getItem(position);
    }

    int magnitudeColorResourceId;
    public int getMagnitudeColor(double magnitude){
        int mag=(int)(Math.floor(magnitude));
     switch (mag)  {
         case 0:
         case 1:
             magnitudeColorResourceId = R.color.magnitude1;
             break;
         case 2:
             magnitudeColorResourceId = R.color.magnitude2;
             break;
         case 3:
             magnitudeColorResourceId = R.color.magnitude3;
             break;
         case 4:
             magnitudeColorResourceId = R.color.magnitude4;
             break;
         case 5:
             magnitudeColorResourceId = R.color.magnitude5;
             break;
         case 6:
             magnitudeColorResourceId = R.color.magnitude6;
             break;
         case 7:
             magnitudeColorResourceId = R.color.magnitude7;
             break;
         case 8:
             magnitudeColorResourceId = R.color.magnitude8;
             break;
         case 9:
             magnitudeColorResourceId = R.color.magnitude9;
             break;
         default:
             magnitudeColorResourceId = R.color.magnitude10plus;
             break;
     }
         return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
