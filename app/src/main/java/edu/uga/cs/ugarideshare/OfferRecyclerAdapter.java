package edu.uga.cs.ugarideshare;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfferRecyclerAdapter extends RecyclerView.Adapter<OfferRecyclerAdapter.OfferHolder>
{
    private final Context context;
    private final List<RideShareData.Offer> offerList;

    public OfferRecyclerAdapter(Context context, List<RideShareData.Offer> offerList) {
        this.context = context;
        this.offerList= offerList;
    }



    public static class OfferHolder extends RecyclerView.ViewHolder {
        //Requires enough variables for both card types
        public TextView value1;
        public TextView value2;
        public TextView value3;

        public OfferHolder(View itemView) {
            super(itemView);
            value1 = itemView.findViewById(R.id.offerValue1);
            value2 = itemView.findViewById(R.id.offerValue2);
            value3 = itemView.findViewById(R.id.offerValue3);
        }
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_card, parent, false);


        return new OfferHolder(view);
    }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull OfferHolder holder, int position) {
        //StateQuizData.Quiz quiz = quizList.get(position);
//        String quizDateTime = quiz.getQuizDate();
//        String datePart;
//        String timePart;
//        if (quizDateTime != null && quizDateTime.contains(" ")) {
//            String[] parts = quizDateTime.split(" ");
//            datePart = parts[0];
//            timePart = parts.length > 1 ? parts[1] : "";
//        } else {
//            datePart = quizDateTime;
//            timePart = "";
//        }
//        holder.quizValue1.setText("Date: " + datePart);
//        holder.quizValue2.setText("Time: " + timePart);
//        holder.quizValue3.setVisibility(View.VISIBLE);
//        holder.quizValue3.setText("Score: " + quiz.getCorrectAnswers() + " / 6");

        //TODO: Get values from the database

        holder.value1.setText("Offer test 1");
        holder.value2.setText("Offer test 2");
        holder.value3.setText("Offer test 3");

    }

    @Override
    public int getItemCount() {
        return offerList != null ? offerList.size() : 0;
    }
}