package info.androidhive.materialdesign.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.materialdesign.R;

public class RVAdapterKomentar extends RecyclerView.Adapter<RVAdapterKomentar.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.bankomat_name);
            personAge = (TextView) itemView.findViewById(R.id.distance);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pisition = getAdapterPosition();
                MainActivity.metod(pisition);

                }
            });
        }
    }

    //  List<Person> persons;
    List<Bankomat> bankomati;

    RVAdapterKomentar(List<Bankomat> bankomati) {
        this.bankomati = bankomati;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        int posicija = i;
        personViewHolder.personName.setText(bankomati.get(i).name);
        personViewHolder.personAge.setText("Distance: " + bankomati.get(i).distance+"m");
        //  personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
        personViewHolder.personPhoto.setImageResource(R.drawable.log);


    }

    @Override
    public int getItemCount() {
        return bankomati.size();
    }


}
